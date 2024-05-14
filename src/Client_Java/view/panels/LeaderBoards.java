package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.Button;

import javax.swing.*;
import java.awt.*;

public class LeaderBoards extends JPanel {

    private JLabel top1 = new JLabel();
    private JLabel top2 = new JLabel();
    private JLabel top3 = new JLabel();
    private JLabel header = new JLabel("Leaderboards");

    public LeaderBoards(ClientController clientController) {

        header.setFont(FontFactory.newPoppinsBold(45));
        header.setBackground(ColorFactory.beige());

        setLayout(new GridBagLayout());

        JScrollPane playerList = createPlayerListPanel(clientController);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        add(header, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        add(top1, gbc);
        gbc.gridy++;
        add(top2, gbc);
        gbc.gridy++;
        add(top3, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(playerList, gbc);

        gbc.gridy++;
        add(createBackButton(clientController), gbc);
    }

    private JScrollPane createPlayerListPanel(ClientController clientController) {
        top1.setFont(FontFactory.newPoppinsBold(40));
        top2.setFont(FontFactory.newPoppinsDefault(35));
        top3.setFont(FontFactory.newPoppinsDefault(30));

        User[] users = clientController.getLeaderBoards();

        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));

        JScrollPane otherPlayers = new JScrollPane(playersPanel);
        otherPlayers.setPreferredSize(new Dimension(300, 100));

        for(int i = 0; i < users.length ; i++ ) {
            User currentUser = users[i];
            if(i == 0) {
                top1.setText("1. " + currentUser.userName +  ": " + currentUser.score);
            } else if (i == 1) {
                top2.setText("2. " + currentUser.userName +  ": " + currentUser.score);
            } else if (i == 2) {
                top3.setText("3. " + currentUser.userName +  ": " + currentUser.score);
            } else {
                playersPanel.add(new JLabel((i+1)+". " + currentUser.userName +  ": " + currentUser.score));
            }
        }


        return otherPlayers;
    }

    private Button createBackButton(ClientController clientController) {
        Button button = new Button("Back", new Dimension(100, 50), FontFactory.newPoppinsDefault(14));
        button.addActionListener(e -> clientController.changeFrame(ClientViews.HOME_PAGE));
        return button;
    }
}
