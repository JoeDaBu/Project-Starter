package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import ui.gui.AlarmClock;

public class AlarmControllerPanelButtons extends JPanel {
    AlarmController controller;

    AlarmControllerPanelButtons(AlarmList alarmList) {
        String a = alarmList.getName();
        controller = new AlarmController();
        setBackground(new Color(255,0,0));
        setBounds(0,170, 390,100);
        setButtons();
        setVisible(true);
    }

    private void setButtons() {
        setLayout(new GridLayout(2,1));
        add(controller.addAlarm);
        add(controller.removeAlarm);
        add(controller.changeAlarm);
        add(controller.viewAlarm);
        add(controller.showAlarms);
        add(controller.sort);
    }
}
