package ui.gui;

import model.Alarm;
import model.AlarmList;
import model.DaysList;
import model.DaysOfTheWeek;
import model.exceptions.CancelException;
import model.exceptions.ItemAlreadyExists;
import model.exceptions.NotADay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.DaysOfTheWeek.*;

public class AlarmController implements ActionListener {

    public AlarmList alarmListGUI;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 10;
    private static final int XBOUNDS = 100;
    private static final int YBOUNDS = 100;
    JButton addAlarm;
    JButton removeAlarm;
    JButton changeAlarm;
    JButton viewAlarm;
    JButton showAlarms;
    JButton sort;
    // JButton save; show be in menu bar
    //JButton load; should be in menu bar

    AlarmController() {
        alarmListGUI = new AlarmList("My Alarm List");
        addButton();
        removeButton();
        changeButton();
        viewButton();
        showButton();
        sortButton();
    }

    //JOptionPane.showMessageDialog - shows a simple dialog
    //JOptionPane.showConfirmDialog - shows a choice dialog depending on option type
    //JOptionPane.showInputDialog - shows a input dialog which has a user input
    //JOptionPane.showOptionDialog - shows a combination of all prior shows dialog

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAlarm) {
            doAddAlarm();
        } else if (e.getSource() == removeAlarm) {
            doRemoveAlarm();
        } else if (e.getSource() == changeAlarm) {
            doChangeAlarm();
        }  else if (e.getSource() == showAlarms) {
            doShowAlarms();
        } else if (e.getSource() == viewAlarm) {
            doViewAlarm();
        }  else if (e.getSource() == sort) {
            doSortAlarms();
        }
    }


    private void doAddAlarm() {
        try {
            String name = addAlarmName();
            int hours = addAlarmHour();
            int minutes = addAlarmMinutes();
            DaysList days = addAlarmDays();
            Alarm a = new Alarm(name, hours, minutes, days);
            alarmListGUI.addAlarm(a);
        } catch (CancelException cancelException) {
            JOptionPane.showMessageDialog(null,
                    "Action Was Cancelled!",
                    "Cancelled",
                    JOptionPane.WARNING_MESSAGE);//replace later
        }
    }

    private DaysList addAlarmDays() {
        DaysList days = new DaysList();
        Boolean keepGoing = true;
        while (keepGoing) {
            String day = getStringAddAlarmDay();
            if (day == null) {
                keepGoing = false;
            } else {
                try {
                    days.addDay(processCommandDays(day.toLowerCase()));
                } catch (NotADay exception) {
                    if (day.equals("stop")) {
                        keepGoing = false;
                    } else {
                        addAlarmDaysError("Oops That Is Not A Known Day!");
                    }
                } catch (ItemAlreadyExists itemAlreadyExists) {
                    addAlarmDaysError("Oops That Day Has Already Been Added!");
                }
            }
        }
        return days;
    }

    private String getStringAddAlarmDay() {
        return JOptionPane.showInputDialog(null,
                "Note: Canceling Sets Day To Everyday \nEnter Day:",
                "Add Alarm",
                JOptionPane.QUESTION_MESSAGE);
    }

    private void addAlarmDaysError(String s) {
        JOptionPane.showMessageDialog(null,
                s, "Invalid",
                JOptionPane.ERROR_MESSAGE);
    }

    private int addAlarmMinutes() {
        int minutes = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter Alarm Minutes:",
                "Add Alarm",
                JOptionPane.QUESTION_MESSAGE));
        return minutes;
    }

    private int addAlarmHour() {
        int hours = -1;
        Boolean keepGoing = true;
        while (keepGoing) {
            String number = JOptionPane.showInputDialog(null,
                    "Enter Alarm Hour:",
                    "Add Alarm",
                    JOptionPane.QUESTION_MESSAGE);
            if (number == null) {
                throw new CancelException();
            } else {

                try {
                    hours = Integer.parseInt(number);
                    if (hours < 24 && hours >= 0) {
                        keepGoing = false;
                    } else {
                        addAlarmHourError("That is Not A Valid Hour!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    addAlarmHourError("That is Not An Integer!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return hours;
    }

    private void addAlarmHourError(String s, String error, int errorMessage) {
        JOptionPane.showMessageDialog(null,
                s,
                error,
                errorMessage);
    }

    private String addAlarmName() throws CancelException {
        String name = "";
        Boolean keepGoing = true;
        while (keepGoing) {
            name = getAddAlarmName();
            if (name.equals("")) {
                getWarningAddAlarmName();
                int contin = getWarningOptionAddAlarmName();
                if (contin == 0) {
                    keepGoing = false;
                }
            } else if (name == null) {
                throw new CancelException();
            } else {
                keepGoing = false;
            }
        }
        return name;
    }

    private void getWarningAddAlarmName() {
        JOptionPane.showMessageDialog(null,
                "The Alarm Name Is Blank!",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    private int getWarningOptionAddAlarmName() {
        return JOptionPane.showConfirmDialog(null,
                "Continue?",
                "Warning", JOptionPane.YES_NO_OPTION);
    }

    private String getAddAlarmName() {
        return JOptionPane.showInputDialog(null,
                "Enter Alarm Name:",
                "Add Alarm",
                JOptionPane.QUESTION_MESSAGE);
    }


    private void addButton() {
        addAlarm = new JButton();
        addAlarm.addActionListener(this);
        addAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        addAlarm.setText("Add Alarm");
        addAlarm.setFocusable(false);
    }

    private void removeButton() {
        removeAlarm = new JButton();
        removeAlarm.addActionListener(this);
        removeAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        removeAlarm.setText("Remove Alarm");
        removeAlarm.setFocusable(false);
    }

    private void changeButton() {
        changeAlarm = new JButton();
        changeAlarm.addActionListener(this);
        changeAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        changeAlarm.setText("Change Alarm");
        changeAlarm.setFocusable(false);
    }

    private void viewButton() {
        viewAlarm = new JButton();
        viewAlarm.addActionListener(this);
        viewAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        viewAlarm.setText("View Alarm");
        viewAlarm.setFocusable(false);
    }

    private void showButton() {
        showAlarms = new JButton();
        showAlarms.addActionListener(this);
        showAlarms.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        showAlarms.setText("Show Alarms");
        showAlarms.setFocusable(false);
    }

    private void sortButton() {
        sort = new JButton();
        sort.addActionListener(this);
        sort.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        sort.setText("Sort Alarms");
        sort.setFocusable(false);
    }

    //Requires: days of the week inputs to be lower case
    //Effects:processes a users command regarding a day of the week
    private DaysOfTheWeek processCommandDays(String command) {
        if (command.equals("friday")) {
            return Friday;
        } else if (command.equals("monday")) {
            return Monday;
        } else if (command.equals("tuesday")) {
            return Tuesday;
        } else if (command.equals("wednesday")) {
            return Wednesday;
        } else if (command.equals("thursday")) {
            return Thursday;
        } else if (command.equals("saturday")) {
            return Saturday;
        } else if (command.equals("sunday")) {
            return Sunday;
        } else {
            throw new NotADay();
        }
    }

}
