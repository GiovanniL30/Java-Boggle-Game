package Client_Java.view.components;

import Client_Java.utilities.ColorFactory;
import Client_Java.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LetterBlock extends JPanel {
    private String letter;
    private JLabel label;

    public LetterBlock(String letter) {
        this.letter = letter;
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setBorder(new LineBorder(Color.BLACK));

        label = new JLabel(letter.toUpperCase());
        label.setFont(FontFactory.newPoppinsBold(20));
        label.setHorizontalAlignment(SwingUtilities.CENTER);
        add(label, BorderLayout.CENTER);
    }

    public void setUsed(){
        setBackground(new Color(63, 26, 26));
        label.setForeground(Color.white);
    }

    public void setUnUsed(){
        setBackground(Color.white);
        label.setForeground(Color.black);
    }

    public String getLetter() {
        return letter;
    }
}
