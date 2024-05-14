package Server_Java.view.components;

import Server_Java.view.AdminMainFrame;
import shared.utilities.FontFactory;
import shared.viewComponents.FieldInput;
import shared.viewComponents.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchBar extends JPanel {
    private FieldInput searchField;
    public SearchBar(){
        setBackground(Color.white);
        setLayout(new BorderLayout(0, 50));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(AdminMainFrame.WIDTH, 50));
        centerPanel.setBackground(Color.WHITE);

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(Color.white);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;

        IconButton back = new IconButton("src/shared/images/back.png", 40, 40);
        buttonsPanel.add(back, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 40, 0, 0);

        searchField = new FieldInput("", new Dimension(600, 50), Integer.MAX_VALUE, 0, false);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(Color.WHITE);
        JLabel label = new JLabel();
        label.setFont(FontFactory.newPoppinsBold(14));
        labelPanel.add(label);

        centerPanel.add(buttonsPanel, BorderLayout.WEST);
        centerPanel.add(searchField, BorderLayout.EAST);

        add(labelPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        searchField.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

                if (!Character.isLetterOrDigit(e.getKeyChar())) {
                    if(!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) return;
                }

                if (e.isAltDown() || e.isShiftDown() || e.isControlDown()) {
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                    if (searchField.getInput() != null && searchField.getInput().isEmpty()) {
                        searchField.removeError();
                    }
                }


                if (searchField.getInput() != null) {
                    searchField.removeError();
                }

            }


            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
