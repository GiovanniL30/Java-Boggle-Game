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
    private String imagePath = "";
    private int height = 100;
    private int width = 100;
    private Picture banner;
    private shared.viewComponents.ClickableText createAccount = new ClickableText("Create account", 180, 15,FontFactory.newPoppinsBold(18));
    public AdminHeader(AdminController adminController) {

        Picture picture = new Picture("src/shared/images/logo.png", 75, 75);

        picture.setBackground(ColorFactory.beige());

        banner = new Picture(imagePath, width, height);

        banner.setBackground(ColorFactory.beige());
        setLayout(new BorderLayout());
        setBackground(ColorFactory.beige());

        createAccount.setForeground(ColorFactory.mahogany());
        createAccount.setVisible(false);

        add(picture, BorderLayout.WEST);
        add(banner, BorderLayout.CENTER);
        add(createAccount, BorderLayout.EAST);

        createAccount.addActionListener(e -> adminController.changeFrame(AdminViews.SIGN_UP));
    }

    public void setText(String imagePath, int height, int width) {
        this.imagePath = imagePath;
        this.height = height;
        this.width = width;

        remove(banner);
        banner = new Picture(imagePath, width, height);
        banner.setBackground(ColorFactory.beige());
        add(banner, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public ClickableText getCreateAccount() {
        return createAccount;
    }

}
