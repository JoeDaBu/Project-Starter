package ui.gui;

import model.AlarmList;

public class Update {

    private AlarmController controller;
    private AlarmControllerPanelLabels controllerPanel;
    private AlarmClock alarmClock;

//    public Update() {
//        alarmClock = new AlarmClock();
//    }

    public void update(AlarmList alarmList) {
        controllerPanel = new AlarmControllerPanelLabels();
        controllerPanel.update(alarmList);
        alarmClock.update();
    }
}
