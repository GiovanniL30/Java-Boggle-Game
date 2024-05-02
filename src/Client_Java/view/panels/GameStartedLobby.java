package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.FontFactory;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.LetterBlock;
import Client_Java.view.components.PlayerNameBlock;
import Client_Java.view.components.WordBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Optional;

public class GameStartedLobby extends JPanel {

    private final JLabel gameTime = new JLabel("Time remaining: ");
    private final JLabel round = new JLabel("Round: ");
    private final JLabel scoreLabel = new JLabel("Your score: ");
    private final FieldInput fieldInput = new FieldInput("", new Dimension(400, 70), 20, 4, false);
    private final JPanel wordEnteredPanel = new JPanel();
    private final JPanel playerListPanel = new JPanel();
    private final JPanel randomLettersPanel = new JPanel();

    private final JPanel topPanel = new JPanel();

    private final JPanel inputPanel = new JPanel();

    private final JPanel middlePanel = new JPanel();
    private final JPanel roundTimePanel = new JPanel();


    private final ClientController clientController;
    private final String gameLobby;
    private LinkedList<PlayerNameBlock> playerNameBlocks = new LinkedList<>();
    private LinkedList<LetterBlock> unUsedLetterBlocks = new LinkedList<>();
    private LinkedList<LetterBlock> usedLetterBlocks = new LinkedList<>();
    private int currentScore = 0;

    private String[] randomLetters;




    public GameStartedLobby(ClientController clientController, String gameLobby, String[] randomLetters) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        this.randomLetters = randomLetters;

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.white);


        initTopPanel();
        add(topPanel, BorderLayout.NORTH);

        initPlayerListPanel();
        add(playerListPanel, BorderLayout.WEST);

        initMiddlePanel();
        add(middlePanel, BorderLayout.CENTER);


        initRandomLettersPanel();
        add(randomLettersPanel, BorderLayout.EAST);

        fieldInput.getTextField().addActionListener(e -> {
            clientController.submitWord(fieldInput.getTextField().getText());
        });
        fieldInput.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.isAltDown() || e.isControlDown() || e.isShiftDown()) {
                    return;
                }

                if(e.getExtendedKeyCode() < 65 || e.getExtendedKeyCode() > 90) {
                    if(e.getExtendedKeyCode() != 8) e.consume();
                }

                if(e.getExtendedKeyCode() == 8) {

                    String input = fieldInput.getTextField().getText();

                    if(input.isEmpty()) {

                        usedLetterBlocks.forEach(l ->  {
                            l.setUnUsed();
                            fieldInput.removeError();
                            unUsedLetterBlocks.add(l);
                        });

                    }else {

                        Optional<LetterBlock> f  = usedLetterBlocks.stream().filter(s -> s.getLetter().equalsIgnoreCase(input.length() == 1 ? input : input.substring(input.length() - 1))).findFirst();

                        if(f.isPresent()) {
                            fieldInput.removeError();
                            f.get().setUnUsed();
                            usedLetterBlocks.remove(f.get());
                            unUsedLetterBlocks.add(f.get());
                        }

                    }


                }

                if(isLetterFound((e.getKeyChar() + "").toUpperCase())) {
                    Optional<LetterBlock> f  = unUsedLetterBlocks.stream().filter(s -> s.getLetter().equalsIgnoreCase(e.getKeyChar()+"")).findFirst();

                    if(f.isPresent()) {
                        f.get().setUsed();
                        usedLetterBlocks.add(f.get());
                        unUsedLetterBlocks.remove(f.get());
                    }

                }else {

                    if(e.getExtendedKeyCode() != 8) {
                        e.consume();
                        fieldInput.enableError("Letter \"" + (e.getKeyChar() + "").toUpperCase() + "\" is not available");

                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {




            }

            @Override
            public void keyReleased(KeyEvent e) {



            }
        });


    }

    private void initTopPanel() {
        scoreLabel.setFont(FontFactory.newPoppinsBold(20));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.white);

        initRoundTimePanel();
        roundTimePanel.setBackground(Color.white);
        topPanel.add(roundTimePanel, BorderLayout.EAST);
        topPanel.add(scoreLabel, BorderLayout.WEST);

    }

    private void initPlayerListPanel() {
        playerListPanel.setLayout(new GridBagLayout());
        playerListPanel.setBackground(Color.white);
        playerListPanel.setPreferredSize(new Dimension(200, 50));
        updatePlayerList();
    }

    private void initRoundTimePanel() {
        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
        roundTimePanel.setLayout(layout);
        roundTimePanel.setSize(300, 20);
        roundTimePanel.setBackground(Color.white);

        round.setFont(FontFactory.newPoppinsBold(17));
        gameTime.setFont(FontFactory.newPoppinsBold(17));

        roundTimePanel.add(round);
        roundTimePanel.add(gameTime);
    }

    private void initRandomLettersPanel() {

        randomLettersPanel.removeAll();
        unUsedLetterBlocks = new LinkedList<>();
        GridLayout grid = new GridLayout(0, 4);
        randomLettersPanel.setLayout(grid);
        randomLettersPanel.setBackground(Color.black);
        randomLettersPanel.setPreferredSize(new Dimension(400, 100));
        randomLettersPanel.setBackground(Color.white);

        for (String string : randomLetters) {
            LetterBlock letter = new LetterBlock(string);
            unUsedLetterBlocks.add(letter);
            randomLettersPanel.add(letter);
        }
        randomLettersPanel.revalidate();
        randomLettersPanel.repaint();
    }


    private void initMiddlePanel() {
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setPreferredSize(new Dimension(600, 100));
        middlePanel.setBackground(Color.white);

        fieldInput.setFont(FontFactory.newPoppinsBold(20));
        middlePanel.add(fieldInput);

        wordEnteredPanel.setLayout(new BoxLayout(wordEnteredPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(wordEnteredPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        middlePanel.add(scrollPane);


    }


    public void addNewWordBlock(String word, int score) {

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                wordEnteredPanel.add(new WordBlock(word, score), 0);
                return null;
            }
        }.execute();

    }

    public void updatePlayerList() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                playerListPanel.removeAll();
                playerNameBlocks = new LinkedList<>();
                User[] players = clientController.lobbyPlayer(gameLobby);
                for (User player : players) {
                    gridBagConstraints.gridy++;
                    PlayerNameBlock playerNameBlock = new PlayerNameBlock(clientController.getLoggedInUser().userName.equals(player.userName) ? "YOU" : player.userName, 0, player.userID, 14);
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
        Optional<PlayerNameBlock> player = playerNameBlocks.stream().filter(playerNameBlock -> playerNameBlock.getPlayerId().equals(id)).findFirst();
        if(id.equals(clientController.getLoggedInUser().userID)) {
            scoreLabel.setText("Your score: " + score);
        }
        player.ifPresent(playerNameBlock -> playerNameBlock.updateScore(score));
    }

    public void setRandomLettersPanel(String[] letters) {
        this.randomLetters = letters;
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                initRandomLettersPanel();
                return null;
            }
        }.execute();

    }

    public FieldInput getFieldInput() {
        return fieldInput;
    }

    public void setTime(int time) {
        gameTime.setText("Time Remaining: (" + time + "s)");
        gameTime.revalidate();
        gameTime.repaint();
    }

    public void setRound(int r) {
        round.setText("Round: " + r);
        round.revalidate();
        round.repaint();
    }

    private boolean isLetterFound(String letter) {
        for (LetterBlock s : unUsedLetterBlocks) {
            if (s.getLetter().equalsIgnoreCase(letter)) {
                return true;
            }
        }
        return false;
    }

}

