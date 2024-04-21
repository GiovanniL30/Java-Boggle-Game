package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ColorFactory;
import Client_Java.utilities.FontFactory;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.Button;
import Client_Java.view.components.FilledButton;
import Client_Java.view.components.Timer;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private FieldInput lobbyId = new FieldInput("Enter Lobby ID: ", new Dimension(600, 50), 20, 5, false);
    private FilledButton joinLobby = new FilledButton("JOIN LOBBY", new Dimension(600, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.WHITE);
    private Button createNewLobby = new Button("CREATE NEW LOBBY", new Dimension(600, 50), FontFactory.newPoppinsDefault(14));

    private ClientController clientController;

    public HomePage(ClientController clientController) {
        this.clientController = clientController;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(lobbyId, constraints);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridy = 1;
        centerPanel.add(joinLobby, constraints);
        constraints.gridy = 2;
        centerPanel.add(createNewLobby, constraints);

        add(centerPanel, BorderLayout.CENTER);
    }

}
