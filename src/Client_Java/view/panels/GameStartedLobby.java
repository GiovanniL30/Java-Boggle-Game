package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.view.components.LetterBlock;
import Client_Java.view.components.PlayerNameBlock;
import Client_Java.view.components.WordBlock;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FieldInput;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private final LinkedList<WordBlock> wordBlocks = new LinkedList<>();
    private final JPanel playerListPanel = new JPanel();
    private final JPanel randomLettersPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JPanel middlePanel = new JPanel();
    private final JPanel roundTimePanel = new JPanel();
    private final ClientController clientController;
    private final String gameLobby;
    private LinkedList<PlayerNameBlock> playerNameBlocks = new LinkedList<>();
    private LinkedList<LetterBlock> unUsedLetterBlocks = new LinkedList<>();
    private LinkedList<LetterBlock> usedLetterBlocks = new LinkedList<>();
    private final JLabel errorLabel = new JLabel();

    private String[] randomLetters;
    private int typeCode = 0;


    public GameStartedLobby(ClientController clientController, String gameLobby, String[] randomLetters) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        this.randomLetters = randomLetters;


        setLayout(new BorderLayout(20, 20));
        setBackground(ColorFactory.beige());

        initTopPanel();
        add(topPanel, BorderLayout.NORTH);

        initPlayerListPanel();
        add(playerListPanel, BorderLayout.WEST);

        initMiddlePanel();
        add(middlePanel, BorderLayout.CENTER);


        initRandomLettersPanel();
        add(randomLettersPanel, BorderLayout.EAST);

        fieldInput.setBackground(ColorFactory.beige());

        fieldInput.getTextField().addActionListener(e -> {
            clientController.submitWord(fieldInput.getTextField().getText());

            usedLetterBlocks.forEach(f -> {
                f.setUnUsed();
                unUsedLetterBlocks.addLast(f);
                fieldInput.removeError();
            });
        });
        fieldInput.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (typeCode == 10) return;

                if (e.isAltDown() || e.isControlDown() || e.isShiftDown()) {
                    return;
                }


                if (typeCode < 65 || typeCode > 90) {
                    if (typeCode != 8) e.consume();
                }

                if (typeCode == 8) {

                    String input = fieldInput.getTextField().getText();

                    if (input.isEmpty()) {

                        usedLetterBlocks.forEach(l -> {
                            l.setUnUsed();
                            fieldInput.removeError();
                            unUsedLetterBlocks.addLast(l);
                        });

                    } else {

                        Optional<LetterBlock> f = usedLetterBlocks.stream().filter(s -> s.getLetter().equalsIgnoreCase(input.length() == 1 ? input : input.substring(input.length() - 1))).findFirst();

                        if (f.isPresent()) {
                            fieldInput.removeError();
                            f.get().setUnUsed();
                            usedLetterBlocks.remove(f.get());
                            unUsedLetterBlocks.addLast(f.get());
                        }

                    }


                }

                if (isLetterFound((e.getKeyChar() + "").toUpperCase())) {
                    Optional<LetterBlock> f = unUsedLetterBlocks.stream().filter(s -> s.getLetter().equalsIgnoreCase(e.getKeyChar() + "")).findFirst();

                    if (f.isPresent()) {
                        f.get().setUsed();
                        usedLetterBlocks.addLast(f.get());
                        unUsedLetterBlocks.remove(f.get());
                    }

                } else {

                    if (typeCode != 8) {
                        e.consume();
                        fieldInput.enableError("Letter \"" + (e.getKeyChar() + "").toUpperCase() + "\" is not available");
                    }

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                typeCode = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {

                typeCode = e.getKeyCode();

            }
        });


    }

    private void initTopPanel() {
        scoreLabel.setFont(FontFactory.newPoppinsBold(20));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(ColorFactory.beige());

        errorLabel.setFont(FontFactory.newPoppinsDefault(14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        errorLabel.setBorder(new EmptyBorder(0, 30, 0, 0));

        initRoundTimePanel();
        roundTimePanel.setBackground(ColorFactory.beige());
        topPanel.add(roundTimePanel, BorderLayout.EAST);
        topPanel.add(errorLabel, BorderLayout.CENTER);
        topPanel.add(scoreLabel, BorderLayout.WEST);

    }

    private void initPlayerListPanel() {
        playerListPanel.setLayout(new GridBagLayout());
        playerListPanel.setBackground(ColorFactory.beige());
        playerListPanel.setPreferredSize(new Dimension(200, 50));
        updatePlayerList();
    }

    private void initRoundTimePanel() {
        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
        roundTimePanel.setLayout(layout);
        roundTimePanel.setSize(300, 20);
        roundTimePanel.setBackground(ColorFactory.beige());

        round.setFont(FontFactory.newPoppinsBold(17));
        gameTime.setFont(FontFactory.newPoppinsBold(17));

        roundTimePanel.add(round);
        roundTimePanel.add(gameTime);
    }

    private void initRandomLettersPanel() {

        randomLettersPanel.removeAll();
        unUsedLetterBlocks = new LinkedList<>();
        usedLetterBlocks = new LinkedList<>();
        GridLayout grid = new GridLayout(0, 4);
        randomLettersPanel.setLayout(grid);
        randomLettersPanel.setBackground(ColorFactory.beige());
        randomLettersPanel.setPreferredSize(new Dimension(400, 100));
        randomLettersPanel.setBackground(ColorFactory.beige());

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
        middlePanel.setBackground(ColorFactory.beige());

        fieldInput.setFont(FontFactory.newPoppinsBold(20));
        middlePanel.add(fieldInput);

        wordEnteredPanel.setLayout(new BoxLayout(wordEnteredPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(wordEnteredPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        middlePanel.add(scrollPane);
    }


    public void addNewWordBlock(String word, int score) {
        wordBlocks.addFirst(new WordBlock(word, score));
        repopulateWordBlock();
    }

    private void repopulateWordBlock() {

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {

                for (WordBlock wordBlock : wordBlocks) {
                    wordEnteredPanel.add(wordBlock);
                }

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
                    PlayerNameBlock playerNameBlock = new PlayerNameBlock(clientController.getLoggedInUser().userName.equals(player.userName) ? "YOU" : player.userName, 0, player.userID, 14, true);
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
        if (id.equals(clientController.getLoggedInUser().userID)) {
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
                fieldInput.getTextField().setText("");
                return null;
            }
        }.execute();

    }

    public void setError(String error) {
        fieldInput.enableError(error);
    }



    public void clearText() {
        fieldInput.clearText();
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

    public void enableError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.revalidate();
        errorLabel.repaint();

    }

    public void removeError(){
        errorLabel.setVisible(false);
    }

}

