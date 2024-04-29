package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.FontFactory;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.LetterBlock;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

public class GameStartedLobby extends JPanel {

    private JLabel gameTime = new JLabel("Time remaining: ");
    private JLabel round = new JLabel("Round: ");
    private FieldInput fieldInput = new FieldInput("", new Dimension(200, 50), 20, 4, false);
    private JPanel wordEnteredPanel = new JPanel();
    private JPanel playerListPanel = new JPanel();
    private JPanel randomLettersPanel = new JPanel();
    private JPanel header = new JPanel();
    private JPanel topPanel = new JPanel();

    private JPanel inputPanel = new JPanel();

    private JPanel middlePanel = new JPanel();
    private JPanel roundTimePanel = new JPanel();


    private ClientController clientController;
    private String gameLobby;
    private LinkedList<PlayerNameBlock> playerNameBlocks = new LinkedList<>();


    public GameStartedLobby(ClientController clientController, String gameLobby) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        initTopPanel();
        add(topPanel, BorderLayout.NORTH);

        initPlayerListPanel();
        add(playerListPanel, BorderLayout.WEST);

        initMiddlePanel();
        add(middlePanel, BorderLayout.CENTER);

        initRandomLettersPanel();
        add(randomLettersPanel, BorderLayout.EAST);
    }

    private void initTopPanel(){
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(header);

        initRoundTimePanel();
        roundTimePanel.setBackground(Color.white);
        topPanel.add(roundTimePanel);
    }

    private void initPlayerListPanel() {
        playerListPanel.setLayout(new GridBagLayout());
        playerListPanel.setBackground(Color.white);
        playerListPanel.setPreferredSize(new Dimension(300, 50));
        updatePlayerList();
    }

    private void initRoundTimePanel(){
        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
        roundTimePanel.setLayout(layout);
        roundTimePanel.setSize(300, 20);

        round.setFont(FontFactory.newPoppinsBold(17));
        gameTime.setFont(FontFactory.newPoppinsBold(17));

        roundTimePanel.add(round);
        roundTimePanel.add(gameTime);
    }
    private void initRandomLettersPanel(){
        GridLayout grid = new GridLayout(0, 4);
        randomLettersPanel.setLayout(grid);
        randomLettersPanel.setBackground(Color.black);
        randomLettersPanel.setPreferredSize(new Dimension(250, 100));

        String [] letters = generateRandomLetters();
        for (String string : letters){
            LetterBlock letter = new LetterBlock(string);

            letter.setSize(5, 5);
            randomLettersPanel.add(letter);
        }
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

    private void initMiddlePanel() {
        middlePanel.setLayout(new GridLayout(2,1));
        middlePanel.setPreferredSize(new Dimension(650, 100));
        middlePanel.setBackground(Color.white);

        initInputPanel();

        wordEnteredPanel.setLayout(new BoxLayout(wordEnteredPanel, BoxLayout.Y_AXIS));
        wordEnteredPanel.setBackground(Color.lightGray);

        middlePanel.add(inputPanel);
        middlePanel.add(wordEnteredPanel);
    }

    private void initInputPanel(){
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 10, 5);

        JLabel inputLabel = new JLabel("Your Score:             ");
        inputLabel.setFont(FontFactory.newPoppinsBold(25));
        inputLabel.setPreferredSize(new Dimension(300, 50));
        inputPanel.add(inputLabel, constraints);

        constraints.gridy++;

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(350, 50));
        inputField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        inputPanel.add(inputField, constraints);
    }

    public void addNewWordBlock(String word, int score) {
    }

    public void updatePlayerList() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(10, 10, 10 ,10);
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                playerListPanel.removeAll();
                playerNameBlocks = new LinkedList<>();
                User[] players = clientController.lobbyPlayer(gameLobby);
                for(User player: players) {
                    gridBagConstraints.gridy++;
                    PlayerNameBlock playerNameBlock =  new PlayerNameBlock(clientController.getLoggedInUser().userName.equals(player.userName) ? "YOU" : player.userName, 0, player.userID, 14);
                    playerNameBlocks.add(playerNameBlock);
                    playerListPanel.add(playerNameBlock, gridBagConstraints);
                    playerListPanel.revalidate();
                    playerListPanel.repaint();
                }
                return null;
            }
        }.execute();
    }

    public void updatePlayerScores(String id, int score) {
       Optional<PlayerNameBlock> player =  playerNameBlocks.stream().filter(playerNameBlock -> playerNameBlock.getPlayerId().equals(id)).findFirst();
       player.ifPresent(playerNameBlock -> playerNameBlock.updateScore(score));
    }

    public void getRandomLetters(String [] letters){
        for (String letter : letters){
            LetterBlock letterBlock = new LetterBlock(letter);
            randomLettersPanel.add(letterBlock);
        }
    }

    public FieldInput getFieldInput() {
        return fieldInput;
    }

    public void setTime(int time) {
        gameTime.setText("Time Remaining: (" + time+"s)");
        gameTime.revalidate();
        gameTime.repaint();
    }

    public void setRound(int r) {
        round.setText("Round: " + r);
        round.revalidate();
        round.repaint();
    }
}

