package Server_Java;

import App.ApplicationServer;
import App.ApplicationServerHelper;
import App.ClientActions;
import App.Controller;
import Server_Java.dataBase.Database;
import com.sun.media.sound.SF2Region;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.Timer;

import java.util.*;


public class GameLobby  {

    private HashMap<String, Controller> players = new HashMap<>(); // player id -> controller
    private HashMap<String, Integer> playerScores = new HashMap<>(); //player id -> word, score
    private HashMap<String, LinkedList<String>> playerEnteredWords = new HashMap<>(); //player id -> word list entered valid
    private Timer waitingTimer;
    private Timer gameTimer;
    private int secondsLeft;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private final String lobbyId;
    private int currentRound = 1;
    private LinkedList<String> words;


    public GameLobby(String lobbyId) {
        this.lobbyId = lobbyId;
        words = Database.getWords();

        secondsLeft = 2;
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

        secondsLeft = 5;

        gameTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {

                if(currentRound == 3) {

                    gameEnded = true;
                    String topPlayer = getTopPlayer();

                    for (Controller controller : players.values()) {
                        controller.endGameUpdate(Database.getUser(topPlayer), playerScores.get(topPlayer));
                    }

                    new Thread(() -> Database.finishedGame(topPlayer, lobbyId)).start();

                    gameTimer.stop();
                }else {
                    secondsLeft = 5;
                    currentRound++;

                    String[] letters = generateRandomLetters();
                    for (Controller controller : players.values()) {
                        controller.receiveLetter(letters);
                        controller.setRound(currentRound);
                        controller.receiveUpdates(ClientActions.NEW_GAME_ROUND);
                    }

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

    public synchronized App.Response addWord(String word, String userId) {

        Any any = ORB.init().create_any();

        if(!isWordPresent(word)) {
            any.insert_long(0);
            return new App.Response(any, false);
        }

        if(playerEnteredWords.get(userId).stream().anyMatch(s -> s.equalsIgnoreCase(word))) {
            any.insert_long(1);
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


}
