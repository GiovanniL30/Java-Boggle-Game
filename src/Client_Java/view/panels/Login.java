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
    private JPanel leftSide = new JPanel();
    private JPanel rightSide = new JPanel();

    private Button signUpButton;

    public Login(ClientController controller) {
        this.controller = controller;

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 1;

        setBackground(ColorFactory.beige());
        setLayout(new GridLayout());

        Picture picture = new Picture("src/shared/images/welcome.png", 500, 100);
        picture.setBackground(ColorFactory.beige());
        Picture logo = new Picture("src/shared/images/logo.png", 450, 450);

        leftSide.setLayout(new GridBagLayout());
        leftSide.setBackground(ColorFactory.beige());
        GridBagConstraints leftConstraints = new GridBagConstraints();


        leftConstraints.gridy = 1;
        leftSide.add(logo, leftConstraints);

        rightSide.setLayout(new GridBagLayout());
        rightSide.setBackground(ColorFactory.beige());
        rightSide.setPreferredSize(new Dimension(500, 500));

        GridBagConstraints rightConstraints = new GridBagConstraints();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ColorFactory.beige());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,0 ));

        signUpButton = new Button("Sign up", new Dimension(200, 50) , FontFactory.newPoppinsDefault(12));
        buttonPanel.add(signUpButton);
        loginButton = new FilledButton("Login", new Dimension(200, 50) ,FontFactory.newPoppinsDefault(12), ColorFactory.blue(), Color.white);
        buttonPanel.add(loginButton);

        JPanel fieldInputs = fieldInputs();

        rightConstraints.gridy = 0;
        rightSide.add(picture,rightConstraints);

        rightConstraints.gridy = 1;
        rightSide.add(fieldInputs, rightConstraints);

        rightConstraints.gridy = 2;
        rightSide.add(buttonPanel, rightConstraints);

        add(leftSide, constraints);
        constraints.gridx = 0;
        add(rightSide, constraints);


        signUpButton.addActionListener( e -> controller.changeFrame(ClientViews.SIGN_UP));
    }

    private JPanel fieldInputs(){

        JPanel fieldInputPanel = new JPanel();
        fieldInputPanel.setBackground(ColorFactory.blue());
        fieldInputPanel.setLayout(new BoxLayout(fieldInputPanel, BoxLayout.Y_AXIS));


        FieldInput userName = new FieldInput("User Name", new Dimension(420, 60), 20, 1, false);
        FieldInput password = new FieldInput("Password", new Dimension(420, 60), 20, 1, true);

        fieldInputPanel.add(userName);
        fieldInputPanel.add(password);
        userName.setBackground(ColorFactory.beige());
        password.setBackground(ColorFactory.beige());

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
