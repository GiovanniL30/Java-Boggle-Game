package Server_Java;

import App.ClientActions;
import App.Controller;
import App.GamePlayer;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import javax.swing.Timer;
import java.io.Serializable;
import java.util.*;


public class GameLobby implements Serializable {

    private final String lobbyId;
    private final HashMap<String, LinkedList<String>> playerEnteredWords = new HashMap<>(); //player id -> word list entered valid
    private final LinkedList<String> words;
    private HashMap<String, Controller> players = new HashMap<>(); // player id -> controller
    private HashMap<String, Integer> playerScores = new HashMap<>(); //player id -> word, score
    private Timer waitingTimer;
    private Timer gameTimer;
    private Timer idleTime;
    private int secondsLeft;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private int currentRound = 1;
    private int idleTimerSeconds = 6;
    private String[] currentLetters;


    public GameLobby(String lobbyId) {
        this.lobbyId = lobbyId;
        words = Database.getWords();

        secondsLeft = Database.getWaitingTime();
        waitingTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {
                waitingTimer.stop();

                if (players.size() == 1) {
                    Database.deleteLobby(lobbyId);
                    for (Controller controller : players.values()) {
                        controller.receiveUpdates(ClientActions.NO_PLAYER_LOBBY);
                    }

                    players = null;
                    playerScores = null;
                    waitingTimer = null;

                } else {

                    gameStarted = true;
                    startRound();
                    currentLetters = generateRandomLetters();
                    for (Controller controller : players.values()) {
                        controller.receiveLetter(currentLetters);
                        controller.receiveUpdates(ClientActions.START_GAME);
                    }
                }

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

        if (clientController == null) {

            System.out.println("Error letting this user join the lobby: " + lobbyId);
            return;
        }

        if (gameStarted) return;
        players.put(userId, clientController);
        playerScores.put(userId, 0);
        playerEnteredWords.put(userId, new LinkedList<>());
    }

    public void removePlayer(String userId) {
        players.remove(userId);
        playerScores.remove(userId);

        if(players.size() == 1) {
            endGame();
        }
    }

    public void startRound() {

        secondsLeft = (Database.getGameTime() / 3 ) + 1;

        gameTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {

                if (currentRound == 3) {
                    endGame();
                } else {

                    secondsLeft = (Database.getGameTime() / 3 ) + 1;
                    currentRound++;
                    gameTimer.stop();

                    startIdleTime(() -> {
                        currentLetters = generateRandomLetters();
                        for (Controller controller : players.values()) {
                            controller.stopIdleTime();
                            controller.receiveLetter(currentLetters);
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

        String topPlayer = getTopPlayer();
        LinkedList<GamePlayer> gamePlayers = new LinkedList<>();

        for (Map.Entry<String, Integer> player : playerScores.entrySet()) {
            gamePlayers.add(new GamePlayer(Database.getUser(player.getKey()), player.getValue()));
        }

        for (Controller controller : players.values()) {
            controller.startIdleTime(Database.getUser(topPlayer), playerScores.get(topPlayer), currentRound - 1, gamePlayers.toArray(new GamePlayer[0]));
        }

        new Thread(() -> players.forEach((playerID, playerController) -> {

            for (Map.Entry<String, Integer> playerScore : playerScores.entrySet()) {
                playerController.updatePlayerScore(playerScore.getKey(), playerScore.getValue());
            }

        })).start(); //update score view

        idleTime = new Timer(1000, e -> {

            idleTimerSeconds--;

            for (Controller controller : players.values()) {
                controller.setIdleTimeLeft("Starting round " + currentRound + " in: " + idleTimerSeconds + "s");
            }

            if (idleTimerSeconds <= 0) {
                idleTime.stop();
                idleTimerSeconds = 6;
                callback.run();
            }

        });

        idleTime.start();

    }

    public synchronized App.Response addWord(String word, String userId) {

        Any any = ORB.init().create_any();

        if (!isWordPresent(word)) {
            any.insert_long(0); // word is not present
            return new App.Response(any, false);
        }

        if (playerEnteredWords.get(userId).stream().anyMatch(s -> s.equalsIgnoreCase(word))) {
            any.insert_long(1); // player already entered the word
            return new App.Response(any, false);
        }

        String matchingWordPlayer = checkUniqueWord(word);

        if (matchingWordPlayer != null) {
            any.insert_long(2); //word is already entered by other user
            players.get(matchingWordPlayer).receiveUpdates(ClientActions.WORD_IS_REMOVED_SCORE);
            return new App.Response(any, false);
        }

        int score = computeScore(word);

        int newScore = playerScores.get(userId) + score;
        playerScores.put(userId, newScore);

        playerEnteredWords.get(userId).add(word);
        any.insert_long(score);
        return new App.Response(any, true);
    }

    public int playerCount() {
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

    private boolean haveTie() {
        int topPlayerScore =  playerScores.get(getTopPlayer());
      for(Map.Entry<String, Integer> player: playerScores.entrySet()) {
          if(topPlayerScore == player.getValue()) return true;
      }

      return false;
    };

    private String checkUniqueWord(String word) {


        for (Map.Entry<String, LinkedList<String>> player : playerEnteredWords.entrySet()) {

            Optional<String> notUniqueWord = player.getValue().stream().filter(w -> w.equalsIgnoreCase(word)).findFirst();

            if (notUniqueWord.isPresent()) {
                int newScore = playerScores.get(player.getKey()) - computeScore(word);
                playerScores.put(player.getKey(), newScore);
                return player.getKey();
            }

        }

        return null;
    }

    private void endGame() {

        LinkedList<GamePlayer> gamePlayers = new LinkedList<>();

        for (Map.Entry<String, Integer> player : playerScores.entrySet()) {
            gamePlayers.add(new GamePlayer(Database.getUser(player.getKey()), player.getValue()));
        }

        gameEnded = true;
        String topPlayer = getTopPlayer();

        for (Controller controller : players.values()) {
            controller.endGameUpdate(Database.getUser(topPlayer), playerScores.get(topPlayer), gamePlayers.toArray(new GamePlayer[0]));
        }

        new Thread(() -> {
            if (players.isEmpty()) {

            } else {

                if(haveTie()) {
                    Database.deleteLobby(lobbyId);
                    return;
                }

                Database.setTopPlayer(topPlayer, playerScores.get(topPlayer), lobbyId);
                Database.finishedGame(lobbyId);
            }
        }).start();

        new Thread(() -> Database.updatePlayerScores(playerScores)).start();
        new Thread(() -> {
            if(!haveTie()) {
                players.keySet().forEach(player -> Database.removePlayer(player, lobbyId));
            }
        } ).start();

        gameTimer.stop();
    }


}
