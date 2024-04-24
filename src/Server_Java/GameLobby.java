package Server_Java;

import App.ClientActions;
import App.Controller;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import shared.referenceClasses.Response;

import java.util.HashMap;


public class GameLobby  {

    private final HashMap<String, Controller> players = new HashMap<>();
    private final HashMap<String, HashMap<String, Integer>> playerScores = new HashMap<>();
    private final HashMap<String, Integer> enteredWords = new HashMap<>();
    private javax.swing.Timer waitingTimer;
    private int secondsLeft;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private final String lobbyId;

    public GameLobby(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void addPlayer(String userId, Controller clientController) {
        players.put(userId, clientController);

        if (waitingTimer == null || gameStarted || !waitingTimer.isRunning()) {
            secondsLeft = 60;

            waitingTimer = new javax.swing.Timer(1000, e -> {

                if (secondsLeft <= 0) {
                    waitingTimer.stop();
                    gameStarted = true;
                    for (Controller controller : players.values()) {
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


    }

    public void removePlayer(String userId) {
        players.remove(userId);
    }

    public synchronized App.Response addWord(String word, String userId) {

        Any any = ORB.init().create_any();
        if (enteredWords.containsKey(word.toLowerCase())) {
            any.insert_long(0);
            return new App.Response(any, false);
        }

        int score = computeScore(word);
        enteredWords.put(word, score);

        if (playerScores.containsKey(userId)) {
            playerScores.get(userId).put(word, score);
        } else {
            HashMap<String, Integer> scoreTablePlayer = new HashMap<>();
            scoreTablePlayer.put(word, score);
            playerScores.put(userId, scoreTablePlayer);
        }

        any.insert_long(score);
        return new App.Response(any, false);
    }

    private int computeScore(String word) {
        return 0;
    }

}
