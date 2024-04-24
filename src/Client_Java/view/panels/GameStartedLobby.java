package Client_Java.view.panels;

import Client_Java.controller.ClientController;
import Client_Java.view.components.FieldInput;

import javax.swing.*;
import java.awt.*;

public class GameStartedLobby extends JPanel {

    private JLabel gameTime = new JLabel();
    private JLabel round = new JLabel();
    private FieldInput fieldInput = new FieldInput("", new Dimension(200, 50), 20, 4, false);
    private JPanel wordEnteredPanel = new JPanel();
    private JPanel playerListPanel = new JPanel();
    private JPanel randomLettersPanel = new JPanel();
    private JPanel header = new JPanel();

    public GameStartedLobby(ClientController clientController, String gameLobby) {

    }

}

