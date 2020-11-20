package ui.gui;

import model.Alarm;
import model.AlarmList;
import model.DaysList;
import model.DaysOfTheWeek;
import model.exceptions.CancelException;
import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
import model.exceptions.NotADay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.DaysOfTheWeek.*;

//The Class for all the buttons and their actions in the GUI
public class AlarmController implements ActionListener {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 10;
    private static final int XBOUNDS = 100;
    private static final int YBOUNDS = 100;
    public AlarmList alarmListGUI;
    JButton addAlarm;
    JButton removeAlarm;
    JButton changeAlarm;
    JButton viewAlarm;
    JButton showAlarms;
    JButton sort;
    JButton changeName;
    JButton imageToggle;
    private Update update;
    //JButton save; show be in menu bar
    //JButton load; should be in menu bar

    //Effects: Creates all buttons and initializes the alarmList in gui
    AlarmController(String name) {
        alarmListGUI = new AlarmList(name);
        addButton();
        removeButton();
        changeButton();
        viewButton();
        showButton();
        sortButton();
        changeNameButton();
        imageToggleButton();
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
        } else if (e.getSource() == showAlarms) {
            doShowAlarms();
        } else if (e.getSource() == viewAlarm) {
            doViewAlarm();
        } else if (e.getSource() == sort) {
            doSortAlarms();
        } else if (e.getSource() == changeName) {
            doChangeName();
        } else if (e.getSource() == imageToggle) {
            doImageToggle();
        }
    }

    //Modifies: this
    //Effects: changes the name of the alarmList
    private void doChangeName() {
        String newName = JOptionPane.showInputDialog(null,
                "New Name For AlarmList",
                "A Diverging Path",
                JOptionPane.PLAIN_MESSAGE);
        try {
            if (newName == null) {
                throw new CancelException();
            } else {
                alarmListGUI.setName(newName);
                update.updateName(newName);
            }
        } catch (CancelException cancelException) {
            cancelActionMessage();
        }
    }

    //Modifies: this
    //Effects: reorganizes the alarmList
    private void doSortAlarms() {
        if (alarmListGUI.numAlarms() == 0) {
            noItemsExist();
        } else {
            try {
                choseSortAlarms();
            } catch (CancelException e) {
                cancelActionMessage();
            }
        }
    }

    //Effects: shows a popup of the alarm specified
    private void doViewAlarm() {
        if (alarmListGUI.numAlarms() == 0) {
            noItemsExist();
        } else {
            try {
                Boolean keepGoing = true;
                while (keepGoing) {
                    String name = findAlarmName();
                    Alarm a = alarmListGUI.viewer(name);
                    if (a == null) {
                        alarmDoesNotExist();
                    } else {
                        viewAlarm(a);
                        update.updateView(name);
                        keepGoing = false;
                    }
                }
            } catch (Exception e) {
                cancelActionMessage();
            }
        }
    }

    //Effects: toggles the image
    private void doImageToggle() {
        JOptionPane.showMessageDialog(null,
                "Image Toggled",
                "Toggle",
                JOptionPane.INFORMATION_MESSAGE);
        update.updateImage();
    }

    //Effects: shows all alarms in a popup
    private void doShowAlarms() {
        if (alarmListGUI.numAlarms() == 0) {
            noItemsExist();
        } else {
            String alarms = alarmListGUI.showAlarmsGui();
            JOptionPane.showMessageDialog(null,
                    "Alarm List:\n" + alarms,
                    "All Alarms",
                    JOptionPane.INFORMATION_MESSAGE);
            update.updateShow();
        }
    }

    //Modifies: this
    //Effects: removes an alarm from the list
    private void doRemoveAlarm() {
        if (alarmListGUI.numAlarms() == 0) {
            noItemsExist();
        } else {
            try {
                Boolean keepGoing = true;
                while (keepGoing) {
                    String name = findAlarmName();
                    Alarm a = alarmListGUI.removeAlarm(name);
                    if (a == null) {
                        alarmDoesNotExist();
                    } else {
                        successfulRemoval(a);
                        update.updateRemove(name, a);
                        keepGoing = false;
                    }
                }
            } catch (Exception e) {
                cancelActionMessage();
            }
        }
    }

    //Modifies: this
    //Effects: adds an alarm to the list
    private void doAddAlarm() {
        try {
            String name = addAlarmName();
            int hours = addAlarmHour();
            int minutes = addAlarmMinutes();
            DaysList days = addAlarmDays();
            Alarm a = new Alarm(name, hours, minutes, days);
            alarmListGUI.addAlarm(a);
            update.updateAdd(name, a);
        } catch (CancelException cancelException) {
            cancelActionMessage();
        }
    }

    //Modifies: this
    //Effects: changes an alarm in the list
    private void doChangeAlarm() {
        Boolean keepGoing = true;
        try {
            alarmListGUI.viewAlarm("test");
            while (keepGoing) {
                Alarm oldAlarm = alarmListGUI.viewAlarm(findAlarmName());
                if (oldAlarm == null) {
                    alarmDoesNotExist();
                } else {
                    String name = changeAlarmName(oldAlarm);
                    int hour = changeAlarmHour(oldAlarm);
                    int minutes = changeAlarmMinutes(oldAlarm);
                    DaysList daysList = changeAlarmDays(oldAlarm);
                    changeAlarmShortener(oldAlarm, name, hour, minutes, daysList);
                    keepGoing = false;
                }
            }
        } catch (CancelException e) {
            cancelActionMessage();
        } catch (EmptyList emptyList) {
            noItemsExist();
        }
    }

    //Modifies: this
    //Effects: changes an alarm in the list, used to shorten the doChange method
    private void changeAlarmShortener(Alarm oldAlarm, String name, int hour, int minutes, DaysList daysList) {
        Alarm newAlarm = new Alarm(name, hour, minutes, daysList);
        alarmListGUI.changeAlarm(oldAlarm.getAlarmName(), newAlarm);
        update.updateChange(oldAlarm.getAlarmName(), oldAlarm, name, newAlarm);
        viewAlarm(newAlarm);
    }

    //Effects: returns a list of days a user picks
    private DaysList changeAlarmDays(Alarm a) {
        DaysList daysList = new DaysList();
        int contin = changeAlarmChangeQuestion("Would You Like To Change The Days");
        if (contin == 2) {
            throw new CancelException();
        } else if (contin == 0) {
            daysList = addAlarmDays();
        } else {
            daysList = a.getDaysOfTheWeek();
        }
        return daysList;
    }

    //Effects: returns a valid time the user picks
    private int changeAlarmMinutes(Alarm a) {
        int minutes = -1;
        int contin = changeAlarmChangeQuestion("Would You Like To Change The Minutes");
        if (contin == 2) {
            throw new CancelException();
        } else if (contin == 0) {
            minutes = addAlarmMinutes();
        } else {
            minutes = a.getMinutes();
        }
        return minutes;
    }

    //Effects: returns a valid time a user picks
    private int changeAlarmHour(Alarm a) {
        int hour = -1;
        int contin = changeAlarmChangeQuestion("Would You Like To Change The Hour");
        if (contin == 2) {
            throw new CancelException();
        } else if (contin == 0) {
            hour = addAlarmHour();
        } else {
            hour = a.getHours();
        }
        return hour;
    }

    //Effects: returns a valid name a user picks
    private String changeAlarmName(Alarm a) {
        String name = null;
        int contin = changeAlarmChangeQuestion("Would You Like To Change The Name");
        if (contin == 2) {
            throw new CancelException();
        } else if (contin == 0) {
            name = addAlarmName();
        } else {
            name = a.getAlarmName();
        }
        return name;
    }

    //Modifies: this
    //Effects: determines how a user would like to sort the list, and sorts it
    private void choseSortAlarms() {
        String[] choices = {"Alphabetical", "Time", "Imminent", "Cancel"};
        int choice = getChoice(choices);
        System.out.println(choice);
        try {
            if (choice == 0) {
                alarmListGUI.alphabeticallySorter();
            } else if (choice == 1) {
                alarmListGUI.sortAlarmsByTime();
            } else if (choice == 2) {
                alarmListGUI.sortByImminent();
            } else if (choice == 3) {
                throw new CancelException();
            }
            update.updateSort();
        } catch (EmptyList emptyList) {
            System.out.println("Impossible Reached In Sorting");
        }
    }

    //Effects: a popup of a chosen alarm
    private void viewAlarm(Alarm a) {
        JOptionPane.showMessageDialog(null,
                "Alarm:\n" + a.alarmToString(),
                "View Alarm",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //Effects: returns the list of days a user picks
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

    //Effects: returns a valid time a user picks
    private int addAlarmMinutes() {

        int minutes = -1;
        Boolean keepGoing = true;
        while (keepGoing) {
            String number = JOptionPane.showInputDialog(null,
                    "Enter Alarm Minutes:",
                    "New Alarm Minutes",
                    JOptionPane.QUESTION_MESSAGE);
            if (number == null) {
                throw new CancelException();
            } else {

                try {
                    minutes = Integer.parseInt(number);
                    if (minutes < 60 && minutes >= 0) {
                        keepGoing = false;
                    } else {
                        addAlarmIntError("That is Not A Valid Minute!");
                    }
                } catch (NumberFormatException e) {
                    addAlarmIntError("That is Not An Integer!");
                }
            }
        }
        return minutes;
    }

    //Effects: returns a valid hour a user picks
    private int addAlarmHour() {
        int hours = -1;
        Boolean keepGoing = true;
        while (keepGoing) {
            String number = JOptionPane.showInputDialog(null,
                    "Enter Alarm Hour:",
                    "New Alarm Hour",
                    JOptionPane.QUESTION_MESSAGE);
            if (number == null) {
                throw new CancelException();
            } else {

                try {
                    hours = Integer.parseInt(number);
                    if (hours < 24 && hours >= 0) {
                        keepGoing = false;
                    } else {
                        addAlarmIntError("That is Not A Valid Hour!");
                    }
                } catch (NumberFormatException e) {
                    addAlarmIntError("That is Not An Integer!");
                }
            }
        }
        return hours;
    }

    //Effects: returns a valid name a user picks
    private String addAlarmName() throws CancelException {
        String name = "";
        Boolean keepGoing = true;
        while (keepGoing) {
            name = getInputAlarmName("New Alarm Name");
            if (name != null) {
                if (alarmListGUI.viewer(name) == null) {
                    if (name.equals("")) {
                        getWarningAddAlarmName();
                        int contin = getWarningOptionAddAlarmName();
                        if (contin == 0) {
                            keepGoing = false;
                        }
                    } else {
                        keepGoing = false;
                    }
                } else {
                    nameAlreadyExistError();
                }
            } else {
                throw new CancelException();
            }
        }
        return name;
    }

    //Effects: finds an a alarm a user picks, if un-found returns null
    private String findAlarmName() {
        String name = getInputAlarmName("Find Alarm");
        if (name == null) {
            throw new CancelException();
        }
        return name;
    }

    public Update getUpdate() {
        return update;
    }

    //Modifies: this
    //Effects: sets update
    public void setUpdate(Update update) {
        this.update = update;
    }

    //Effects: Gets a user input string
    private String getStringAddAlarmDay() {
        return JOptionPane.showInputDialog(null,
                "Note: Canceling Sets Day To Everyday(and/or ends the application)\nEnter Day:",
                "New Alarm Days",
                JOptionPane.QUESTION_MESSAGE);
    }

    //Effects: gets the user input choice
    private int getChoice(String[] choices) {
        return JOptionPane.showOptionDialog(null,
                "Chose How To Sort",
                "Sort",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                -1);
    }

    //Effects: gets a user input
    private int changeAlarmChangeQuestion(String changeFactor) {
        return JOptionPane.showConfirmDialog(null,
                changeFactor,
                "Change Alarm",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    //Effects: error popup
    private void nameAlreadyExistError() {
        JOptionPane.showMessageDialog(null,
                "That Alarm Already Exists!",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    //Effects: error popup
    private void addAlarmDaysError(String s) {
        JOptionPane.showMessageDialog(null,
                s, "Invalid",
                JOptionPane.ERROR_MESSAGE);
    }

    //Effects: error popup
    private void addAlarmIntError(String s) {
        JOptionPane.showMessageDialog(null,
                s, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //Effects: popup for cancelling an action
    private void cancelActionMessage() {
        JOptionPane.showMessageDialog(null,
                "Action Was Cancelled!",
                "Cancelled",
                JOptionPane.WARNING_MESSAGE);//replace later
    }

    //Effects: gets a user input
    private String getInputAlarmName(String title) {
        return JOptionPane.showInputDialog(null,
                "Enter Alarm Name:",
                title,
                JOptionPane.QUESTION_MESSAGE);
    }

    //Effects: a warning popup
    private void getWarningAddAlarmName() {
        JOptionPane.showMessageDialog(null,
                "The Alarm Name Is Blank!",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    //Effects: gets a user input
    private int getWarningOptionAddAlarmName() {
        return JOptionPane.showConfirmDialog(null,
                "Continue?",
                "Warning", JOptionPane.YES_NO_OPTION);
    }

    //Effects: an error popup
    private void noItemsExist() {
        JOptionPane.showMessageDialog(null,
                "No Alarms Exist!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //Effects: success popup
    private void successfulRemoval(Alarm a) {
        JOptionPane.showMessageDialog(null,
                "Success Removed Alarm:\n"
                        + "\t" + a.alarmToString()
                        + "\n\t :)");
    }

    //Effects: error popup
    private void alarmDoesNotExist() {
        JOptionPane.showMessageDialog(null,
                "No Such Alarm Exists!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //Effects: adds and initializes a button
    private void addButton() {
        addAlarm = new JButton();
        addAlarm.addActionListener(this);
        addAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        addAlarm.setText("Add Alarm");
        addAlarm.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void removeButton() {
        removeAlarm = new JButton();
        removeAlarm.addActionListener(this);
        removeAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        removeAlarm.setText("Remove Alarm");
        removeAlarm.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void changeButton() {
        changeAlarm = new JButton();
        changeAlarm.addActionListener(this);
        changeAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        changeAlarm.setText("Change Alarm");
        changeAlarm.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void viewButton() {
        viewAlarm = new JButton();
        viewAlarm.addActionListener(this);
        viewAlarm.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        viewAlarm.setText("View Alarm");
        viewAlarm.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void showButton() {
        showAlarms = new JButton();
        showAlarms.addActionListener(this);
        showAlarms.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        showAlarms.setText("Show Alarms");
        showAlarms.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void sortButton() {
        sort = new JButton();
        sort.addActionListener(this);
        sort.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        sort.setText("Sort Alarms");
        sort.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void changeNameButton() {
        changeName = new JButton();
        changeName.addActionListener(this);
        changeName.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        changeName.setText("Change Name");
        changeName.setFocusable(false);
    }

    //Effects: adds and initializes a button
    private void imageToggleButton() {
        imageToggle = new JButton();
        imageToggle.addActionListener(this);
        imageToggle.setBounds(XBOUNDS, YBOUNDS, WIDTH, HEIGHT);
        imageToggle.setText("Image Toggle");
        imageToggle.setFocusable(false);
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
