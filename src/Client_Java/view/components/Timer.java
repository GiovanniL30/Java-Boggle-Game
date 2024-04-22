package Client_Java.view.components;



import Client_Java.utilities.FontFactory;

import javax.swing.*;

public class Timer extends JLabel {
    private int secondsLeft;
    private javax.swing.Timer timer;

    public Timer(int seconds, int fontSize) {
        super("", SwingConstants.CENTER);
        setFont(FontFactory.newPoppinsDefault(fontSize));
        this.secondsLeft = seconds;
        setText(secondsLeft +"");

        timer = new javax.swing.Timer(1000, e -> {
            secondsLeft--;
            setText(secondsLeft+"");
            if (secondsLeft <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                // Add any action to be performed when the timer reaches zero
            }
        });
    }

    public void startTime() {
        timer.start();
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }
}
