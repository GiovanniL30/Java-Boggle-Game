package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ColorFactory;
import Client_Java.utilities.FontFactory;
import Client_Java.view.components.FilledButton;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;
import java.awt.*;

public class WaitingLobby extends JPanel {

    private ClientController clientController;
    private  JPanel playerListPanel = new JPanel();
    private JPanel waitingList = new JPanel();
    private String gameLobby;
    private JLabel waitingTime = new JLabel("GAME WILL START IN: ");
    private FilledButton leaveLobby = new FilledButton("Leave Lobby", new Dimension(100, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.white);

    public WaitingLobby(ClientController clientController, String gameLobby) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        playerList();
        waitingPanel();
        setLayout(new BorderLayout());


        add(waitingList, BorderLayout.NORTH);
        add(playerListPanel, BorderLayout.CENTER);
        add(leaveLobby, BorderLayout.SOUTH);

        leaveLobby.addActionListener(e -> clientController.leaveLobby(gameLobby));
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
                    playerListPanel.add(new PlayerNameBlock(clientController.getLoggedInUser().userName.equals(player.userName) ? "YOU" : player.userName, 0));
                    playerListPanel.revalidate();
                    playerListPanel.repaint();
                }
                return null;
            }
        }.execute();

    }

    private void waitingPanel() {
        waitingList.setLayout(new BorderLayout());
        waitingList.setPreferredSize(new Dimension(600, 400));
        waitingTime.setFont(FontFactory.newPoppinsBold(20));
        waitingList.add(waitingTime);
    }

    public void setTime(int time) {
        waitingTime.setText("GAME WILL START IN: " + time);
        waitingTime.revalidate();
        waitingTime.repaint();
    }
}
