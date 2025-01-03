package Client_Java.view.components;

import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.Picture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Header extends JPanel {
    private JLabel label = new JLabel("");
    public Header() {

        Picture picture = new Picture("src/shared/images/logo.png", 75, 75);

        picture.setBackground(ColorFactory.beige());

        setLayout(new BorderLayout());
        setBackground(ColorFactory.beige());

        label.setFont(FontFactory.newPoppinsBold(26));
        label.setBorder(new EmptyBorder(0,0,10,0));

        add(picture, BorderLayout.WEST);
        add(label, BorderLayout.EAST);
    }

    public void setUserName(String name) {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                label.setText(name);
                label.revalidate();
                label.repaint();
                return null;
            }
        }.execute();
    }

}
