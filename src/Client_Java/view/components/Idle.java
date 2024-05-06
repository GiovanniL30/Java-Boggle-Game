package Client_Java.view.components;

import javax.swing.*;
import java.awt.*;

public class Idle extends JDialog {

    private final JLabel label = new JLabel("Round will start in...");

    public Idle(Frame parent) {
        super(parent, "", true);

        getContentPane().add(label);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(parent);
        setSize(new Dimension(100, 50));
    }

    public void setText( int count) {
        System.out.println(count);
        label.setText("Round will start in " + count + "s");
        label.revalidate();
        label.repaint();
    }

}
