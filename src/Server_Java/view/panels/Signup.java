package Server_Java.view.panels;

import Server_Java.controller.AdminController;
import Server_Java.utilities.AdminViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.utilities.UtilityMethods;
import Server_Java.view.AdminMainFrame;
import shared.viewComponents.FieldInput;
import shared.viewComponents.FilledButton;
import shared.viewComponents.Picture;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Signup extends JPanel {

    private FieldInput firstName;
    private FieldInput lastName;
    private FieldInput userName;
    private FieldInput password;

    public Signup(AdminController adminController) {

        setBackground(ColorFactory.beige());
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(AdminMainFrame.WIDTH, AdminMainFrame.HEIGHT));

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




        FilledButton createAccountButton = new FilledButton("CREATE ACCOUNT", new Dimension(550, 50), FontFactory.newPoppinsBold(11), ColorFactory.blue(), Color.WHITE);
        layoutConstraints.gridy = 4;
        add(createAccountButton, layoutConstraints);

        FilledButton cancelButton = new FilledButton("CANCEL", new Dimension(550, 50), FontFactory.newPoppinsBold(11), ColorFactory.red(), Color.WHITE);
        layoutConstraints.gridy = 5;
        add(cancelButton, layoutConstraints);

        cancelButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Cancel", JOptionPane.YES_NO_OPTION);
            if (response  == JOptionPane.YES_OPTION) {
                adminController.changeFrame(AdminViews.PLAYERS);
            }
        });


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


//            User newUser = new User(UtilityMethods.generateRandomID(),fName, lName, uName, p, 0);
//            try {
//                clientControllerObserver.createAccount(newUser);
//            } catch (CreateException e) {
//                throw new RuntimeException(e);
//            }
        });
    }

    private JPanel createFieldInputs() {
        JPanel fieldInputHolders = new JPanel(new GridBagLayout());
        fieldInputHolders.setBackground(ColorFactory.beige());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;


        firstName = new FieldInput("First Name", new Dimension(250, 35), 30, 1, false);
        lastName = new FieldInput("Last Name", new Dimension(250, 35), 30, 1, false);
        userName = new FieldInput("User Name", new Dimension(250, 35), 20, 1, false);
        password = new FieldInput("Password", new Dimension(250, 35), 20, 1, true);

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
