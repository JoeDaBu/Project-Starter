package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import ui.gui.AlarmClock;

public class AlarmControllerPanelButtons extends JPanel {
    AlarmController controller;

    AlarmControllerPanelButtons(AlarmController controller) {
//        String a = alarmList.getName();
        this.controller = controller;
        setBackground(new Color(0, 0, 0));
        setBounds(5,150, (int) 387,100);
        //controller.addAlarm.addActionListener(this);
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

    public AlarmController getController() {
        return controller;
    }

}
