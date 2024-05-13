package Server_Java.view;

import shared.utilities.ColorFactory;
import Client_Java.view.components.Header;
import Server_Java.controller.AdminController;
import Server_Java.view.panels.AdminHomePage;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;
    private JPanel mainLayout = new JPanel();
    private AdminHomePage adminHomePage;
    private Header header;

    public AdminMainFrame(AdminController adminController){
        init();
        adminHomePage = new AdminHomePage();
        header = new Header();
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(adminHomePage, BorderLayout.CENTER);
        header.setVisible(false);
    }

    private void init() {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("Button.arc", 10);
            UIManager.put("TextComponent.arc", 5);
            UIManager.put("ScrollBar.width", 10);
            UIManager.put("ScrollBar.thumbArc", 3);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Boggled Game Settings");
        BorderLayout borderLayout = new BorderLayout();

        mainLayout.setBorder(new EmptyBorder(25, 50, 25, 50));
        mainLayout.setBackground(ColorFactory.beige());
        setContentPane(mainLayout);

        getContentPane().setLayout(borderLayout);
        setVisible(true);
    }

    public Header getHeader() {
        return header;
    }

    public AdminHomePage getAdminHomePage(){
        return adminHomePage;
    }
}
