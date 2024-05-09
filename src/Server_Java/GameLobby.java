package Server_Java;

import App.ClientActions;
import App.Controller;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import javax.swing.Timer;

import java.util.*;


public class GameLobby  {

    private HashMap<String, Controller> players = new HashMap<>(); // player id -> controller
    private HashMap<String, Integer> playerScores = new HashMap<>(); //player id -> word, score
    private HashMap<String, LinkedList<String>> playerEnteredWords = new HashMap<>(); //player id -> word list entered valid
    private Timer waitingTimer;
    private Timer gameTimer;
    private Timer idleTime;
    private int secondsLeft;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private final String lobbyId;
    private int currentRound = 1;
    private LinkedList<String> words;
    private int idleTimerSeconds = 5;


    public GameLobby(String lobbyId) {
        this.lobbyId = lobbyId;
        words = Database.getWords();

        secondsLeft = Database.getWaitingTime();
        waitingTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {
                waitingTimer.stop();

//                if(players.size() == 1) {
//                    Database.deleteLobby(lobbyId);
//                    for(Controller controller : players.values()){
//                        controller.receiveUpdates(ClientActions.NO_PLAYER_LOBBY);
//                    }
//
//                    players = null;
//                    playerScores = null ;
//                    waitingTimer = null;

               // }else {

                    gameStarted = true;
                    startRound();
                    String[] letters = generateRandomLetters();
                    for (Controller controller : players.values()) {
                        controller.receiveLetter(letters);
                        controller.receiveUpdates(ClientActions.START_GAME);
                    }
               // }

            } else {
                secondsLeft--;
                for (Controller controller : players.values()) {
                    controller.setWaitingTime(secondsLeft);
                }
            }
        });
        waitingTimer.start();
    }

    public void addPlayer(String userId, Controller clientController) {
        if(gameStarted) return;
        players.put(userId, clientController);
        playerScores.put(userId, 0);
        playerEnteredWords.put(userId, new LinkedList<>());
    }

    public void removePlayer(String userId) {
        players.remove(userId);
        playerScores.remove(userId);
    }

    public void startRound() {

        secondsLeft = Database.getGameTime() / 3;

        gameTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {

                if(currentRound == 3) {

                    gameEnded = true;
                    String topPlayer = getTopPlayer();

                    for (Controller controller : players.values()) {
                        controller.endGameUpdate(Database.getUser(topPlayer), playerScores.get(topPlayer));
                    }

                    new Thread(() -> Database.finishedGame(topPlayer, lobbyId, playerScores.get(topPlayer))).start();

                    gameTimer.stop();
                }else {
                    secondsLeft = Database.getGameTime() / 3;
                    currentRound++;
                    gameTimer.stop();

                    startIdleTime(() -> {
                        String[] letters = generateRandomLetters();
                        for (Controller controller : players.values()) {
                            controller.stopIdleTime();
                            controller.receiveLetter(letters);
                            controller.setRound(currentRound);
                            controller.receiveUpdates(ClientActions.NEW_GAME_ROUND);
                        }
                        gameTimer.restart();
                    });


                }


            } else {
                secondsLeft--;
                for (Controller controller : players.values()) {
                    controller.setGameTime(secondsLeft);
                    controller.setRound(currentRound);
                }
            }


        });

        gameTimer.start();
    }

    private void startIdleTime(Runnable callback) {

        for(Controller controller : players.values()) {
           controller.startIdleTime();
        }

        idleTime = new Timer(1000, e -> {

            idleTimerSeconds--;

            for(Controller controller : players.values()) {
                controller.setIdleTimeLeft("Starting " + currentRound + " in: " + idleTimerSeconds+"s");
            }

            if(idleTimerSeconds <= 0) {
                idleTime.stop();
                idleTimerSeconds = 5;
                callback.run();
            }

        });

        idleTime.start();

    }

    public synchronized App.Response addWord(String word, String userId) {

        Any any = ORB.init().create_any();

        if(!isWordPresent(word)) {
            any.insert_long(0); //word is not present
            return new App.Response(any, false);
        }

        if(playerEnteredWords.get(userId).stream().anyMatch(s -> s.equalsIgnoreCase(word))) {
            any.insert_long(1); //player already entered the word
            return new App.Response(any, false);
        }

        String matchingWordPlayer = checkUniqueWord(word);

        if(matchingWordPlayer != null) {

            players.forEach((playerID, controller) -> {

                if(matchingWordPlayer.equalsIgnoreCase(playerID)) {
                    controller.removeWord(word);
                    controller.updatePlayerScore(playerID, playerScores.get(matchingWordPlayer));
                }

            });

            any.insert_long(2); //word is already entered by other user
            return new App.Response(any, false);
        }

        int score = computeScore(word);

        int newScore = playerScores.get(userId) + score;
        playerScores.put(userId, newScore);

        new Thread(() -> players.forEach( (playerID, playerController) -> playerController.updatePlayerScore(userId, newScore))).start(); //update score view

        playerEnteredWords.get(userId).add(word);
        any.insert_long(score);
        return new App.Response(any, true);
    }

    public int playerCount(){
        return players.size();
    }

    private int computeScore(String word) {
        return word.length();
    }

    private boolean isWordPresent(String word) {
        return words.stream().anyMatch(w -> w.equalsIgnoreCase(word));
    }

    private String[] generateRandomLetters() {
         Random random = new Random();

         String[] vowels = {"a", "e", "i", "o", "u"};
         String[] consonants = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"};

         LinkedList<String> letters = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            if (i < 7) {
                letters.add(vowels[random.nextInt(vowels.length)]);
            } else {
                letters.add(consonants[random.nextInt(consonants.length)]);
            }
        }

        return letters.toArray(new String[0]);
    }

    private String getTopPlayer() {
        String topPlayer = "";
        int maxScore = -1;

        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            String playerId = entry.getKey();
            int score = entry.getValue();

            if (score > maxScore) {
                maxScore = score;
                topPlayer = playerId;
            }
        }

        System.out.println(topPlayer);
        return topPlayer;
    }

    private String checkUniqueWord(String word) {


        for(Map.Entry<String, LinkedList<String>> player : playerEnteredWords.entrySet()) {

            Optional<String> notUniqueWord = player.getValue().stream().filter(w -> w.equalsIgnoreCase(word)).findFirst();

            if(notUniqueWord.isPresent()) {
                player.getValue().remove(word);
                int newScore = playerScores.get(player.getKey()) - computeScore(word);
                playerScores.put(player.getKey(), newScore);
                return player.getKey();
            }

        }

        return null;
    }


}
