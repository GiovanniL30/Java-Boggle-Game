package Client_Java.view.panels;

import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.*;
import shared.viewComponents.Button;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private FieldInput lobbyId = new FieldInput("Enter Lobby ID: ", new Dimension(600, 50), 20, 5, false);
    private FilledButton joinLobby = new FilledButton("JOIN LOBBY", new Dimension(600, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.WHITE);
    private Button createNewLobby = new Button("CREATE NEW LOBBY", new Dimension(600, 50), FontFactory.newPoppinsDefault(14));

    private ClickableText leaderBoards = new ClickableText("Leaderboards", 200, 50, FontFactory.newPoppinsBold(15));

    private ClientController clientController;

    public HomePage(ClientController clientController) {
        this.clientController = clientController;
        setBackground(ColorFactory.beige());
        setLayout(new BorderLayout());

        Picture picture = new Picture("src/shared/images/boggled.png", 500, 150);
        picture.setBackground(ColorFactory.beige());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        centerPanel.setBackground(ColorFactory.beige());
        lobbyId.setBackground(ColorFactory.beige());
        centerPanel.add(picture, constraints);
        constraints.gridy = 1;
        centerPanel.add(lobbyId, constraints);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridy = 2;
        centerPanel.add(joinLobby, constraints);
        constraints.gridy = 3;
        centerPanel.add(createNewLobby, constraints);
        constraints.gridy = 4;
        centerPanel.add(leaderBoards, constraints);

        joinLobby.addActionListener( e -> {

            String id = lobbyId.getInput();

            if(id == null || id.isEmpty()) {
                return;
            }

            clientController.joinLobby(id);
        });

        leaderBoards.addActionListener(e -> clientController.changeFrame(ClientViews.LEADER_BOARDS));
        createNewLobby.addActionListener( e -> clientController.createNewLobby());
        add(centerPanel, BorderLayout.CENTER);
    }

}
