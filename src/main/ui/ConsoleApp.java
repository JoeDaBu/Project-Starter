package ui;

import model.Alarm;
import model.AlarmList;

import java.util.ArrayList;
import java.util.Scanner;
//a class running and setting alars into motion

public class ConsoleApp {
    private Scanner input;
    private AlarmList mine;
    private AlarmList user;

    //Effects; runs the console app
    public ConsoleApp() {
        runConsoleApp();
    }

    //Modifies: this
    //Effects: process the users inputs
    public void runConsoleApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q") || command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodnight, See you Tomorrow!");

    }

    //Effects: prints out the list of option the user has to modify alarms and
    //alarmLists in the console
    private void displayMenu() {
        System.out.println("\nMy Alarms:");
        System.out.println("\tadd alarm--a");
        System.out.println("\tremove alarm--r");
        System.out.println("\tview alarm--v");
        System.out.println("\tshow alarms--show");
        System.out.println("\tsort alarms--s");
        System.out.println("\tquit--q");
    }


    //Modifies: this
    //Effects: processes a users command
    private void processCommand(String command) {
        if (command.equals("a") || command.equals("add alarm")) {
            System.out.println("You Chose To Add an Alarm");
            doAddAlarm();
        } else if (command.equals("r") || command.equals("remove alarm")) {
            System.out.println("You Chose To Remove an Alarm");
            doRemoveAlarm();
        } else if (command.equals("v") || command.equals("view alarm")) {
            System.out.println("You Chose To View an Alarm");
            doViewAlarm();
        } else if (command.equals("s") || command.equals("sort alarms")) {
            System.out.println("You Chose To Sort the Alarms");
            doSortAlarms();
        } else if (command.equals("show") || command.equals("show alarms")) {
            System.out.println("You Chose To Show the Alarms");
            doShowAlarms();
        } else {
            System.out.println("Alarming! Invalid Selection...");
        }
    }

    //Modifies: this
    //Effects: initializes alarmLists and user input
    private void init() {
        mine = new AlarmList();
        user = new AlarmList();
        input = new Scanner(System.in);
    }

    //Modifies: This
    //Effects: adds an alarm to an alarmList
    private void doAddAlarm() {
        AlarmList selected = selectAlarmList();
        ArrayList<String> dofWeek = new ArrayList<>();

        System.out.println("Enter the Name of Your New Alarm:");
        String name = input.next();

        System.out.println("Enter Hours:");
        int hours = input.nextInt();

        System.out.println("Enter Minutes:");
        int minutes = input.nextInt();

        dofWeek = daysOfTheWeek();
        Alarm a = new Alarm(hours, minutes, dofWeek, name);
        selected.addAlarm(a);

        System.out.println("Success alarm " + name + " has been added the list of Alarms");
        System.out.println(a.alarmToString());
    }

    //Modifies: ArrayList<String>
    //Effects: processes user input regarding adding an alarm with days of the week
    private ArrayList<String> daysOfTheWeek() {
        boolean keepGoing = true;
        String command = null;
        ArrayList<String> dofWeek = new ArrayList<>();

        while (keepGoing) {
            initForDaysOfTheWeek();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("stop")) {
                keepGoing = false;
            } else {
                String day = processCommandDays(command);
                if ((day.equals("Invalid"))) {
                    System.out.println("\nOops That Is Not A Known Day!\n");
                } else if (dofWeek.contains(day)) {
                    System.out.println("\nOops That Day Is Already In The Alarm!\n");
                } else {
                    dofWeek.add(day);
                }
            }
        }
        return dofWeek;
    }

    //Effects: prints out a guide as to how to add days
    private void initForDaysOfTheWeek() {
        System.out.println("Add Day");
        System.out.println("Remember To Write The Day In Lower Case!");
        System.out.println("Type Stop to stop adding days");
    }

    //Requires: days of the week inputs to be lower case
    //Effects:processes a users command regarding a day of the week
    private String processCommandDays(String command) {
        if (command.equals("friday")) {
            return "Friday";
        } else if (command.equals("monday")) {
            return "Monday";
        } else if (command.equals("tuesday")) {
            return "Tuesday";
        } else if (command.equals("wednesday")) {
            return "Wednesday";
        } else if (command.equals("thursday")) {
            return "Thursday";
        } else if (command.equals("saturday")) {
            return "Saturday";
        } else if (command.equals("sunday")) {
            return "Sunday";
        } else {
            return "Invalid";
        }
    }

    //Modifies: this
    //Effects: Removes an alarm from an alarmList
    private void doRemoveAlarm() {
        AlarmList selected = selectAlarmList();
        if (selected.size() == 0) {
            System.out.println("There Are No Alarms To Remove");
        } else {
            System.out.println("Enter Name of Alarm:");
            String name = input.next();

            Alarm remove = selected.removeAlarm("name");
            if (remove == null) {
                System.out.println("Invalid Alarm Name...\nPlease Try Again!");
            } else {
                System.out.println("Successfully Removed " + remove.alarmToString() + "from Alarms");
            }
            printAlarms(selected);
        }
    }

    //Effects:shows the alarm with nae anem in the list of alarms
    private void doViewAlarm() {
        AlarmList selected = selectAlarmList();
        if (selected.size() == 0) {
            System.out.println("There Are No Alarms To View");
        } else {
            System.out.println("Enter Name of Alarm:");
            String name = input.next();

            Alarm view = selected.viewAlarm("name");
            if (view == null) {
                System.out.println("Invalid Alarm Name...\nPlease Try Again!");
            } else {
                System.out.println("Success!\n Showing Alarm " + name + "\n" + view.alarmToString());
            }
        }
    }

    //Modifies:this
    //Effects: sorts an alarmList if it is not empty, otherwise an exception is caught
    private void doSortAlarms() {
        AlarmList selected = selectAlarmList();
        try {
            selected.sortAlarms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Successfully Sorted Alarms:");
        printAlarms(selected);
    }

    //Effects: shows all the alarms in the alarmList in the console
    private void doShowAlarms() {
        AlarmList selected = selectAlarmList();
        System.out.println("Alarm List:");
        printAlarms(selected);
    }

    //Effects: prompts user to select my or user alarms and returns the designated alarmList
    private AlarmList selectAlarmList() {
        String selection = "";
        Boolean keepRunningUser = selection.equals("u") || selection.equals("user") || selection.equals("user alarms");
        Boolean keepRunningMine = selection.equals("m") || selection.equals("mine") || selection.equals("my alarms");

        while (!(selection.equals("m") || selection.equals("u"))) {
            if (selection != "") {
                System.out.println("Alarms Do Not Exist");
            }
            System.out.println("\nSelect User");
            System.out.println("\tMy Alarms(m)");
            System.out.println("\tUser Alarms(u)");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("m")) {
            System.out.println("You Chose My Alarms");
            return mine;
        } else {
            System.out.println("You Chose User Alarms");
            return user;
        }
    }

    //Effects: prints out the alarms in the alarmList
    public void printAlarms(AlarmList a) {
        System.out.println(a.showAlarms());
    }
}
