package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class GameStartedLobby extends JPanel {

    private JLabel gameTime = new JLabel("Time remaining: ");
    private JLabel round = new JLabel("Round: ");
    private FieldInput fieldInput = new FieldInput("", new Dimension(200, 50), 20, 4, false);
    private JPanel wordEnteredPanel = new JPanel();
    private JPanel playerListPanel = new JPanel();
    private JPanel randomLettersPanel = new JPanel();
    private JPanel header = new JPanel();

    private JPanel inputPanelAndEnteredWordsPanel = new JPanel();

    private ClientController clientController;
    private String gameLobby;
    private LinkedList<PlayerNameBlock> playerNameBlocks = new LinkedList<>();


    public GameStartedLobby(ClientController clientController, String gameLobby) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        initPlayerListPanel();
        initInputPanelAndEnteredWordsPanel();
        initRandomLettersPanel();
        setLayout(new BorderLayout());
        setBackground(Color.white);

        add(header, BorderLayout.NORTH);
        add(randomLettersPanel, BorderLayout.EAST);
        add(playerListPanel, BorderLayout.WEST);
        add(inputPanelAndEnteredWordsPanel, BorderLayout.CENTER);
    }

    private void initPlayerListPanel() {
        playerListPanel.setLayout(new GridBagLayout());
        playerListPanel.setBackground(Color.white);
        playerListPanel.setSize(new Dimension(100, 50));
        updatePlayerList();
    }

    private void initRandomLettersPanel(){
        GridBagConstraints constraints = new GridBagConstraints();

        randomLettersPanel.setLayout(new GridBagLayout());
        randomLettersPanel.setBackground(Color.white);
        randomLettersPanel.setSize(new Dimension(100, 80));

        randomLettersPanel.add(round, constraints);
        randomLettersPanel.add(gameTime, constraints);

    }
    private void initInputPanelAndEnteredWordsPanel() {

        inputPanelAndEnteredWordsPanel.setLayout(new GridBagLayout());
        inputPanelAndEnteredWordsPanel.setSize(new Dimension(100, 80));
        inputPanelAndEnteredWordsPanel.setBackground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 10, 5);

        JLabel inputLabel = new JLabel("Your Score: ");
        inputLabel.setPreferredSize(new Dimension(350, 50));
        inputPanelAndEnteredWordsPanel.add(inputLabel, constraints);

        constraints.gridy++;

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(350, 50));
        inputField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        inputPanelAndEnteredWordsPanel.add(inputField, constraints);

        constraints.gridy++;

        wordEnteredPanel.setLayout(new BoxLayout(wordEnteredPanel, BoxLayout.Y_AXIS));
        wordEnteredPanel.setBackground(Color.lightGray);

        inputPanelAndEnteredWordsPanel.add(wordEnteredPanel, constraints);
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

