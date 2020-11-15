package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Update implements ActionListener {

    private AlarmController controller;
    private AlarmControllerPanelLabels labels;
    private AlarmClock alarmClock;
    private ArrayList<Observer> updaters;

    public Update(AlarmClock alarmClock, AlarmControllerPanelLabels labels, AlarmController controller) {
        updaters = new ArrayList<>();
        this.alarmClock = alarmClock;
        this.labels = labels;
        this.controller = controller;
        this.controller.setUpdate(this);
        updaters.add(alarmClock);
        updaters.add(labels);
        controller.addAlarm.addActionListener(this);
        controller.removeAlarm.addActionListener(this);
        controller.showAlarms.addActionListener(this);
        controller.changeAlarm.addActionListener(this);
        controller.viewAlarm.addActionListener(this);
        controller.sort.addActionListener(this);
    }

    public void addObservers() {

    }


    public void updateAdd(String name, Alarm alarm) {
        for (Observer o: updaters) {
            o.updateAdd(name, alarm);
        }
    }

    public void updateRemove(String name, Alarm alarm) {
        for (Observer o: updaters) {
            o.updateRemove(name, alarm);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == controller.addAlarm) {
            System.out.println("Working");

        }
    }

    private String getLatestAlarmName() {
        return controller
                .alarmListGUI
                .getAlarms()
                .get(controller.alarmListGUI.numAlarms() - 1)
                .getAlarmName();
    }
}
