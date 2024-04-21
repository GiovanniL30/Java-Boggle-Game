package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;

public class WaitingLobby extends JPanel {

    private ClientController clientController;
    private  JPanel playerListPanel = new JPanel();
    private String lobbyID;

    public WaitingLobby(ClientController clientController, String lobbyID) {
        this.clientController = clientController;
        this.lobbyID = lobbyID;
        playerList();

        add(playerListPanel);
    }

    private void playerList() {
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        User[] players = clientController.lobbyPlayer(lobbyID);

        for(User player: players) {
            playerListPanel.add(new PlayerNameBlock(player.userName, 0));
            System.out.println(player.userName);
        }

    }
}
