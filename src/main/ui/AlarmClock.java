package ui;

import javax.swing.*;
import java.awt.*;

public class AlarmClock extends JFrame {
    private JPanel clock;

    public AlarmClock() {
        clock.setBorder(BorderFactory.createEmptyBorder(30, 30,10, 30));
        clock.setLayout(new FlowLayout(5, 0, 0));
        add(clock, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Alarm Clock");
        setSize(350, 210);
        setVisible(true);
        setResizable(true);
    }
}
