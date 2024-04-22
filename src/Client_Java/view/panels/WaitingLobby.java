package Client_Java.view.panels;

import App.GameLobby;
import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.FontFactory;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;
import java.awt.*;

public class WaitingLobby extends JPanel {

    private ClientController clientController;
    private  JPanel playerListPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private String gameLobby;
    private JLabel waitingTime = new JLabel();

    public WaitingLobby(ClientController clientController, String gameLobby) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        playerList();
        centerPanel();


        add(playerListPanel);
        add(centerPanel);
    }

    private void playerList() {
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        updatePlayerList();
    }

    public void updatePlayerList() {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                playerListPanel.removeAll();
                User[] players = clientController.lobbyPlayer(gameLobby);
                for(User player: players) {
                    playerListPanel.add(new PlayerNameBlock(player.userName, 0));
                    playerListPanel.revalidate();
                    playerListPanel.repaint();
                }
                return null;
            }
        }.execute();

    }

    private void centerPanel() {
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(600, 400));
        waitingTime.setFont(FontFactory.newPoppinsBold(20));
        centerPanel.add(waitingTime);
    }

    public void setTime(int time) {
        waitingTime.setText(time+"");
        waitingTime.revalidate();
        waitingTime.repaint();
    }
}
