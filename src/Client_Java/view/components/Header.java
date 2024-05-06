package Client_Java.view.components;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private JLabel label = new JLabel("");
    public Header() {

        Picture picture = new Picture("src/shared/images/logo.png", 50, 50);
        JLabel label = new JLabel("");

        setLayout(new BorderLayout());
        setBackground(Color.white);

        add(picture, BorderLayout.WEST);
        add(label, BorderLayout.EAST);
    }

    public void setUserName(String name) {
        label.setText(name);
       revalidate();
       repaint();
    }

}
