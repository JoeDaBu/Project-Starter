package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;

public class AlarmControllerPanel extends JPanel {
    AlarmController controller;

    AlarmControllerPanel() {
        controller = new AlarmController();
        setLayout(new GridLayout(2,1));
        setBackground(new Color(255,0,0));
        setBounds(0,170, 390,100);
        add(controller.addAlarm);
        add(controller.removeAlarm);
        add(controller.viewAlarm);
        add(controller.changeAlarm);
        add(controller.showAlarms);
        add(controller.sort);
        setVisible(true);
    }
}
