package Client_Java.view.components;

import javax.swing.*;
import java.awt.*;

public class IdleTimePopup extends JDialog {

    private JLabel label = new JLabel("");

    public IdleTimePopup(Frame owner) {
        super(owner, "",  true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        add(label);
        setSize(new Dimension(150, 70));
        setLocationRelativeTo(owner);
    }

    public void updateText(String message) {

        new Thread(() -> {
            label.setText(message);
            revalidate();
            repaint();
        }).start();


    }

}
