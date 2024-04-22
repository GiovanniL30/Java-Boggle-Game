package Server_Java;

import App.ClientActions;
import App.Controller;
import App.GameLobbyData;
import App.GameLobbyPOA;
import shared.referenceClasses.Response;

import java.util.HashMap;


public class GameLobby extends GameLobbyPOA {

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
        if (waitingTimer == null) {
            secondsLeft = 60;
        }

        waitingTimer = new javax.swing.Timer(1000, e -> {
            secondsLeft--;
            clientController.setWaitingTime(secondsLeft);
            if (secondsLeft <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                clientController.receiveUpdates(ClientActions.NEW_MESSAGE);
            }
        });
        waitingTimer.start();
    }

    public void removePlayer(String userId) {
        players.remove(userId);
    }

    public Response<Integer> addWord(String word, String userId) {

        if (enteredWords.containsKey(word.toLowerCase())) {
            return new Response<>(0, false);
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

        return new Response<>(score, true);
    }

    private int computeScore(String word) {
        return 0;
    }


    @Override
    public GameLobbyData gameData() {
        return new GameLobbyData(60, 60);
    }

    @Override
    public void gameData(GameLobbyData newGameData) {

    }

    @Override
    public String getId() {
        return lobbyId;
    }
}
