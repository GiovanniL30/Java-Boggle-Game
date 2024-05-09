package Client_Java.view.panels;

import App.CreateException;
import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import Client_Java.utilities.ColorFactory;
import Client_Java.utilities.FontFactory;
import Client_Java.utilities.UtilityMethods;
import Client_Java.view.MainFrame;
import Client_Java.view.components.ClickableText;
import Client_Java.view.components.FieldInput;
import Client_Java.view.components.FilledButton;
import Client_Java.view.components.Picture;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Signup extends JPanel {

    private FieldInput firstName;
    private FieldInput lastName;
    private FieldInput userName;
    private FieldInput password;

    public Signup(ClientController clientControllerObserver) {

        setBackground(ColorFactory.beige());
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT));

        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridy = 0;
        layoutConstraints.gridx = 0;

        Picture boggled = new Picture("src/shared/images/boggled.png", 800, 175);
        boggled.setBackground(ColorFactory.beige());
        boggled.setBorder(new EmptyBorder(0,0,20,0));
        add(boggled, layoutConstraints);

        layoutConstraints.gridy = 1;

        Picture heroPicture = new Picture("resources/images/sign-up-header.png", 400, 150);
        heroPicture.setBackground(ColorFactory.beige());
        add(heroPicture, layoutConstraints);


        JPanel fieldInputHolders = createFieldInputs();
        layoutConstraints.gridy = 2;
        add(fieldInputHolders, layoutConstraints);

        ClickableText loginButton = new ClickableText("Already have an account? Login", 400, 50, FontFactory.newPoppinsDefault(11));
        loginButton.setForeground(ColorFactory.lightGrey());
        loginButton.setBackground(ColorFactory.beige());
        layoutConstraints.gridy = 3;
        add(loginButton, layoutConstraints);


        FilledButton createAccountButton = new FilledButton("CREATE ACCOUNT", new Dimension(950, 50), FontFactory.newPoppinsBold(11), ColorFactory.blue(), Color.WHITE);
        layoutConstraints.gridy = 4;
        add(createAccountButton, layoutConstraints);

        loginButton.addActionListener(e -> clientControllerObserver.changeFrame(ClientViews.LOGIN));

        createAccountButton.addActionListener(action -> {

            String fName = firstName.getInput();
            String lName = lastName.getInput();
            String uName = userName.getInput();
            String p = password.getInput();


            if(UtilityMethods.haveNullOrEmpty(fName, lName, uName, p)){
                return;
            }


            if(uName.matches(".*\\s+.*") || p.matches(".*\\s+.*")) {

                if(uName.matches(".*\\s+.*")) {
                    userName.enableError("Spaces are not allowed here");
                }

                if(p.matches(".*\\s+.*")) {
                    password.enableError("Spaces are not allowed here");
                }

                return;
            }


            User newUser = new User(UtilityMethods.generateRandomID(),fName, lName, uName, p);
            try {
                clientControllerObserver.createAccount(newUser);
            } catch (CreateException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private JPanel createFieldInputs() {
        JPanel fieldInputHolders = new JPanel(new GridBagLayout());
        fieldInputHolders.setBackground(ColorFactory.beige());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;


        firstName = new FieldInput("First Name", new Dimension(450, 50), 30, 1, false);
        lastName = new FieldInput("Last Name", new Dimension(450, 50), 30, 1, false);
        userName = new FieldInput("User Name", new Dimension(450, 50), 20, 1, false);
        password = new FieldInput("Password", new Dimension(450, 50), 20, 1, true);

        firstName.setBackground(ColorFactory.beige());
        lastName.setBackground(ColorFactory.beige());
        userName.setBackground(ColorFactory.beige());
        password.setBackground(ColorFactory.beige());

        constraints.gridy = 0;
        constraints.gridx = 0;
        fieldInputHolders.add(firstName, constraints);

        constraints.gridx = 1;
        fieldInputHolders.add(lastName, constraints);


        constraints.gridy = 1;
        constraints.gridx = 0;
        fieldInputHolders.add(userName, constraints);


        constraints.gridy = 1;
        constraints.fill = 3;
        constraints.gridx = 1;
        fieldInputHolders.add(password, constraints);
        return fieldInputHolders;
    }

    public FieldInput getUserName() {
        return userName;
    }
}
