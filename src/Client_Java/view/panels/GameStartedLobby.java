package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class GameStartedLobby extends JPanel {

    private JLabel gameTime = new JLabel();
    private JLabel round = new JLabel();
    private FieldInput fieldInput = new FieldInput("", new Dimension(200, 50), 20, 4, false);
    private JPanel wordEnteredPanel = new JPanel();
    private JPanel playerListPanel = new JPanel();
    private JPanel randomLettersPanel = new JPanel();
    private JPanel header = new JPanel();

    private ClientController clientController;
    private String gameLobby;
    private LinkedList<PlayerNameBlock> playerNameBlocks = new LinkedList<>();


    public GameStartedLobby(ClientController clientController, String gameLobby) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        initPlayerListPanel();
        setLayout(new BorderLayout());
        setBackground(Color.white);

        add(playerListPanel, BorderLayout.CENTER);
    }

    private void initPlayerListPanel() {
        playerListPanel.setLayout(new GridBagLayout());
        playerListPanel.setBackground(Color.white);
        playerListPanel.setSize(new Dimension(300, 50));
       updatePlayerList();
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



}

