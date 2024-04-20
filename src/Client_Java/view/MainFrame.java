package Client_Java.view;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private JPanel mainLayout = new JPanel();

    public MainFrame() {
        init();
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
        setTitle("Boggled");
        BorderLayout borderLayout = new BorderLayout();

        mainLayout.setBorder(new EmptyBorder(25, 50 ,25, 50));
        mainLayout.setBackground(Color.white);
        setContentPane(mainLayout);

        getContentPane().setLayout(borderLayout);
        setVisible(true);
    }

}
