package Server_Java;

import App.ClientActions;
import App.Controller;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import javax.swing.Timer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;


public class GameLobby  {

    private final HashMap<String, Controller> players = new HashMap<>(); // player id -> controller
    private final HashMap<String, HashMap<String, Integer>> playerScores = new HashMap<>(); //player id -> word, score
    private Timer waitingTimer;
    private Timer gameTimer;
    private int secondsLeft;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private final String lobbyId;
    private int curentRound = 1;
    private LinkedList<String> words;


    public GameLobby(String lobbyId) {
        this.lobbyId = lobbyId;
        words = Database.getWords();

        secondsLeft = 5;
        waitingTimer = new Timer(1000, e -> {

            if (secondsLeft <= 0) {
                waitingTimer.stop();
                secondsLeft = 30;
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

    public void addPlayer(String userId, Controller clientController) {
        if(gameStarted) return;
        players.put(userId, clientController);
    }

    public void removePlayer(String userId) {
        players.remove(userId);
    }

    public synchronized App.Response addWord(String word, String userId) {

        Any any = ORB.init().create_any();

        if(!isWordPresent(word)) {
            any.insert_long(0);
            return new App.Response(any, false);
        }

        int score = computeScore(word);

        if (playerScores.containsKey(userId)) {
            playerScores.get(userId).put(word, score);
        } else {
            HashMap<String, Integer> scoreTablePlayer = new HashMap<>();
            scoreTablePlayer.put(word, score);
            playerScores.put(userId, scoreTablePlayer);
        }

        any.insert_long(score);
        return new App.Response(any, true);
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

}
