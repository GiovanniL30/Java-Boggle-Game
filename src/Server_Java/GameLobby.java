package Server_Java;

import App.ClientActions;
import App.Controller;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import javax.swing.Timer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


public class GameLobby  {

    private final HashMap<String, Controller> players = new HashMap<>(); // player id -> controller
    private final HashMap<String, Integer> playerScores = new HashMap<>(); //player id -> word, score
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

        secondsLeft = 5;
        waitingTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {
                waitingTimer.stop();
                gameStarted = true;
                startRound();
                for (Controller controller : players.values()) {
                    sendRandomLetters(controller);
                    controller.receiveUpdates(ClientActions.START_GAME);
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
        if(gameStarted) return;
        players.put(userId, clientController);
    }

    public void removePlayer(String userId) {
        players.remove(userId);
    }

    public void startRound() {

        secondsLeft = 30;

        gameTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {

                if(currentRound > 3) {
                    gameTimer.stop();
                    gameEnded = true;

                    String topPlayer = getTopPlayer();
                    for (Controller controller : players.values()) {
                        controller.endGameUpdate(topPlayer, playerScores.get(topPlayer));
                    }

                    new Thread(() -> Database.finishedGame(topPlayer, lobbyId)).start();
                }else {
                    secondsLeft = 30;
                    currentRound++;

                    for (Controller controller : players.values()) {
                        sendRandomLetters(controller);
                        controller.receiveUpdates(ClientActions.NEW_GAME_ROUND);
                    }

                }


            } else {
                secondsLeft--;
                for (Controller controller : players.values()) {
                    controller.setGameTime(secondsLeft);
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

        int score = computeScore(word);

        int newScore = playerScores.get(userId) + score;
        playerScores.put(userId, newScore);

        new Thread(() -> players.forEach( (playerID, playerController) -> playerController.updatePlayerScore(userId, newScore))).start(); //update score view

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

    private void sendRandomLetters(Controller client) {
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

        client.receiveLetter(letters.toArray(new String[0]));
    }

    private String getTopPlayer() {
        String topPlayer = null;
        int maxScore = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            String playerId = entry.getKey();
            int score = entry.getValue();

            if (score > maxScore) {
                maxScore = score;
                topPlayer = playerId;
            }
        }

        return topPlayer;
    }


}
