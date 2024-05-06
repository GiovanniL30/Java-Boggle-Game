package Client_Java.view.panels;

import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import Client_Java.utilities.ColorFactory;
import Client_Java.utilities.FontFactory;
import Client_Java.utilities.UtilityMethods;
import Client_Java.view.components.Button;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.FilledButton;
import Client_Java.view.components.Picture;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {


    private FilledButton loginButton;
    private ClientController controller;

    private Button signUpButton;

    public Login(ClientController controller) {
        this.controller = controller;


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;

        setBackground(Color.white);
        setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,0 ));
        signUpButton = new Button("Sign up", new Dimension(200, 50) , FontFactory.newPoppinsDefault(12));


        buttonPanel.add(signUpButton);
        loginButton = new FilledButton("Login", new Dimension(200, 50) ,FontFactory.newPoppinsDefault(12), ColorFactory.blue(), Color.white);

        buttonPanel.add(loginButton);

        Picture picture = new Picture("src/shared/images/welcome.png", 600, 100);

        JPanel fieldInputs = fieldInputs();


        constraints.gridy = 1;
        add(picture, constraints);

        constraints.gridy = 2;
        add(fieldInputs, constraints);

        constraints.gridy = 3;

        add(buttonPanel, constraints);
        signUpButton.addActionListener( e -> controller.changeFrame(ClientViews.SIGN_UP));
    }

    private JPanel fieldInputs(){

        JPanel fieldInputPanel = new JPanel();
        fieldInputPanel.setLayout(new BoxLayout(fieldInputPanel, BoxLayout.Y_AXIS));

        FieldInput userName = new FieldInput("User Name", new Dimension(420, 60), 20, 1, false);
        FieldInput password = new FieldInput("Password", new Dimension(420, 60), 20, 1, true);

        fieldInputPanel.add(userName);
        fieldInputPanel.add(password);

        loginButton.addActionListener( e -> {

            String name = userName.getInput();
            String pass = password.getInput();

            if(UtilityMethods.haveNullOrEmpty(name, pass)) {
                return;
            }


            controller.logIn(name, pass);
        });
        return fieldInputPanel;
    }

}
