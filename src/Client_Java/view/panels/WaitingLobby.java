package Client_Java.view.panels;

import App.LobbyException;
import App.User;
import Client_Java.controller.ClientController;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;
import Client_Java.view.components.PlayerNameBlock;

import javax.swing.*;
import java.awt.*;

public class WaitingLobby extends JPanel {

    private ClientController clientController;
    private  JPanel playerListPanel = new JPanel();
    private JPanel waitingList = new JPanel();
    private String gameLobby;
    private JLabel waitingTime = new JLabel("(GAME WILL START IN:...)");
    private FilledButton leaveLobby = new FilledButton("Leave Lobby", new Dimension(100, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.white);

    public WaitingLobby(ClientController clientController, String gameLobby) {
        this.clientController = clientController;
        this.gameLobby = gameLobby;
        playerList();
        waitingPanel();
        setLayout(new BorderLayout());
        setBackground(ColorFactory.beige());


        add(waitingList, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(playerListPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(leaveLobby, BorderLayout.SOUTH);

        leaveLobby.addActionListener(e -> {
            try {
                clientController.leaveLobby(gameLobby);
            } catch (LobbyException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void playerList() {
        playerListPanel.setLayout(new GridBagLayout());
        playerListPanel.setBackground(ColorFactory.beige());


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
                User[] players = clientController.lobbyPlayer(gameLobby);
                for(User player: players) {
                    gridBagConstraints.gridy++;
                    playerListPanel.add(new PlayerNameBlock(clientController.getLoggedInUser().userName.equals(player.userName) ? "YOU" : player.userName, 0, player.userID, 20, false), gridBagConstraints);
                    playerListPanel.revalidate();
                    playerListPanel.repaint();
                }
                return null;
            }
        }.execute();

    }

    private void waitingPanel() {
        waitingList.setLayout(new BoxLayout(waitingList, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Waiting for other players to join...");
        label.setFont(FontFactory.newPoppinsBold(30));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lobbyID = new JLabel("Lobby ID: " + gameLobby);
        lobbyID.setFont(FontFactory.newPoppinsBold(19));
        lobbyID.setAlignmentX(Component.CENTER_ALIGNMENT);

        waitingTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        waitingTime.setFont(FontFactory.newPoppinsDefault(17));

        waitingList.setBackground(ColorFactory.beige());

        waitingList.add(Box.createVerticalStrut(100));
        waitingList.add(label);
        waitingList.add(waitingTime);
        waitingList.add(lobbyID);
        waitingList.add(Box.createVerticalStrut(100));
    }


    public void setTime(int time) {
        waitingTime.setText("(GAME WILL START IN: " + time+"s)");
        waitingTime.revalidate();
        waitingTime.repaint();
    }
}
