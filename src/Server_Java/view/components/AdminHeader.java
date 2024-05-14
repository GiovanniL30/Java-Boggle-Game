package Server_Java.view.components;

import Server_Java.controller.AdminController;
import Server_Java.utilities.AdminViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.ClickableText;
import shared.viewComponents.Picture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminHeader extends JPanel {
    private JLabel label = new JLabel();
    private shared.viewComponents.ClickableText createAccount = new ClickableText("Create account", 180, 15,FontFactory.newPoppinsBold(18));
    public AdminHeader(AdminController adminController) {

        Picture picture = new Picture("src/shared/images/logo.png", 75, 75);

        picture.setBackground(ColorFactory.beige());

        setLayout(new BorderLayout());
        setBackground(ColorFactory.beige());

        label.setFont(FontFactory.newPoppinsBold(26));
        label.setBorder(new EmptyBorder(0,0,10,0));
        createAccount.setForeground(Color.BLACK);
        createAccount.setBackground(ColorFactory.blue());
        createAccount.setVisible(false);

        add(picture, BorderLayout.WEST);
        add(label, BorderLayout.CENTER);
        add(createAccount, BorderLayout.EAST);

        createAccount.addActionListener(e -> adminController.changeFrame(AdminViews.SIGN_UP));
    }

    public void setText(String text) {
        label.setText(text);
    }

    public ClickableText getCreateAccount() {
        return createAccount;
    }

}
