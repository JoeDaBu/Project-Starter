/*
package ui.gui;

import model.Alarm;
import model.AlarmList;
import model.DaysList;
import model.exceptions.ItemAlreadyExists;

import javax.swing.*;
import java.awt.*;

import static model.DaysOfTheWeek.Monday;

public class TestFrame extends JFrame {
    private AlarmControllerPanelLabels alarmControllerPanelLabels;
    private AlarmList alarmList;
    public Alarm test;
    public Alarm test2;
    public Alarm test3;
    public Alarm test4;
    public Alarm test5;
    public Alarm test6;
    public Alarm test7;
    public Alarm test8;
    public Alarm test9;
    public Alarm test10;
    public Alarm test11;
    public Alarm test12;

    public TestFrame() {
        DaysList dofWeek = new DaysList();
        try {
            dofWeek.addDay(Monday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            System.out.println("fail");
        }
        alarmControllerPanelLabels = new AlarmControllerPanelLabels();
        alarmList = new AlarmList("List");
        makeAlarms(dofWeek);
        alarmList.addAlarm(test2);
        alarmControllerPanelLabels.update(alarmList);
        add(alarmControllerPanelLabels);
        setDefaultCloseOperation(EXIT_ON_CLOSE);//action of the x button/change later
        setResizable(false);//prevents the frame from changing in size
        setLayout(new GridLayout());//or null
        setSize(500, 700);//sets the dimension of the frame
        setVisible(true);
        try {
            Thread.sleep(1000);

            addAlarms();
            alarmControllerPanelLabels.update(alarmList);
            add(alarmControllerPanelLabels);
            repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addAlarms() {
        alarmList.addAlarm(test);
        alarmList.addAlarm(test3);
        alarmList.addAlarm(test4);
        alarmList.addAlarm(test5);
        alarmList.addAlarm(test6);
        alarmList.addAlarm(test7);
        alarmList.addAlarm(test8);
        alarmList.addAlarm(test9);
        alarmList.addAlarm(test10);
    }

    private void makeAlarms(DaysList dofWeek) {
        test = new Alarm("t1", 9, 30, dofWeek);
        test2 = new Alarm("e2", 9, 35, dofWeek);
        test3 = new Alarm("a3", 10, 30, dofWeek);
        test4 = new Alarm("v4", 1, 0, dofWeek);
        test5 = new Alarm("jo", 23, 59, dofWeek);
        test6 = new Alarm("g", 23, 59, dofWeek);
        test7 = new Alarm("ga", 13, 59, dofWeek);
        test8 = new Alarm("gan", 0, 0, dofWeek);
        test9 = new Alarm("gand", 0, 0, dofWeek);
        test10 = new Alarm("gando", 0, 0, dofWeek);
        test11 = new Alarm("gandol", 9, 31, dofWeek);
        test12 = new Alarm("gandolf", 9, 36, dofWeek);
    }

}
*/
//@Override
//public void updateShow() {
//        Boolean show = controllerLabels.getShow();
//        if (show) {
//        remove(controllerLabels);
//        pack();
////        setSize(new Dimension(WIDTH,HEIGHT));
//        } else {
//        add(controllerLabels);
//        pack();
//        }
//        }
