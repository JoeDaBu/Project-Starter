package ui;

import model.Alarm;
import model.AlarmList;
import model.DaysList;
import model.DaysOfTheWeek;
import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
import model.exceptions.NotADay;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static model.DaysOfTheWeek.*;

//a class running and setting alarms into motion

public class ConsoleApp {
    private static String JSON_STORE;
    private Scanner input;
    private DaysList defaultList;
    private AlarmList mine;
    private AlarmList user;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //Effects; runs the console app
    public ConsoleApp() {
        System.out.println("Please Know This Is A 24 Hour Clock System!");
        runConsoleApp();
    }

    //Modifies: this
    //Effects: initializes alarmLists and user input
    private void init() {
        mine = new AlarmList("My Alarm List");
        user = new AlarmList("User Alarm List");
        input = new Scanner(System.in);
        setupDefaultList();
    }

    //Modifies: this
    //Effects: process the users inputs
    public void runConsoleApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q") || command.equals("quit")) {
                doQuit();
                keepGoing = false;
            } else {
                processCommand(command);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
        }
        System.out.println("Goodnight, See you Tomorrow!");

    }

    //Effects: prints out the list of option the user has to modify alarms and
    //alarmLists in the console
    private void displayMenu() {
        System.out.println("My Alarms:");
        System.out.println("\tadd alarm--a");
        System.out.println("\tremove alarm--r");
        System.out.println("\tchange alarm--c");
        System.out.println("\tview alarm--v");
        System.out.println("\tshow alarms--show");
        System.out.println("\tsort alarms by time--st");
        System.out.println("\tsort alarms by name--sn");
        System.out.println("\tsort alarms by imminent--si");
        System.out.println("\tsave alarms--save");
        System.out.println("\tload alarms--load");
        System.out.println("\tquit--q");
    }

    //Modifies: this
    //Effects: processes a users command
    private void processCommand(String command) {
        if (validModifyAlarms(command)) {
            modifyAlarms(command);
        } else if (validPersistenceAlarms(command)) {
            persistenceAlarms(command);
        } else {
            System.out.println("Alarming! Invalid Selection...");
        }
    }

    //Effects: converts the next input into the location of where to save or load an alarmList
    private String persistenceLocation() {
        System.out.println("Write The Name Of The Alarm List");
        String name = input.nextLine();
        String location = "./data/" + name + ".json";
        return location;
    }

    //Modifies: This
    //Effects: Stops all timer Tasks
    private void doQuit() {
        for (int i = 0; i < mine.getAlarms().size(); i++) {
            mine.getAlarms().get(i).cancelAlarmTask();
        }
        for (int m = 0; m < user.getAlarms().size(); m++) {
            user.getAlarms().get(m).cancelAlarmTask();
        }
    }

    //Modifies: this
    //Effects: saves alarmList to to file
    private void doSaveAlarms() {
        AlarmList selected = selectAlarmList();
        JSON_STORE = persistenceLocation();
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(selected);
            jsonWriter.close();
            System.out.println("Saved " + selected.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save file to:\n\t" + JSON_STORE);
        }
    }

    //Modifies: this
    //Effects: loads alarmList from file
    private void doLoadAlarms() {
        AlarmList selected = selectAlarmList();
        JSON_STORE = persistenceLocation();
        jsonReader = new JsonReader(JSON_STORE);
        try {
            if (selected.getName().equals("My Alarm List")) {
                mine = jsonReader.read();
            } else {
                user = jsonReader.read();
            }
            System.out.println("Loaded " + selected.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load file from:\n\t" + JSON_STORE);
        }
    }

    //Modifies: this
    //Effects: Checks if there any alarms to change, if there are calls do change alarm.
    private void doChangeAlarmShortener() {
        AlarmList selected = selectAlarmList();
        if (selected.numAlarms() == 0) {
            System.out.println("There Are No Alarms To Change");
        } else {
            doChangeAlarm(selected);
        }
    }

    //Modifies: this
    //Effects: changes an alarm in the list of alarms.
    private void doChangeAlarm(AlarmList selected) {
        Alarm changeAlarm = checkAlarm(selected);

        String newName = validNameChange(selected);
        if (newName == null) {
            newName = changeAlarm.getAlarmName();
        }

        Integer newH = validHourChange();
        if (newH == -1) {
            newH = changeAlarm.getHours();
        }

        Integer newM = validMinuteChange();
        if (newM == -1) {
            newM = changeAlarm.getMinutes();
        }

        DaysList dofWeek = daysOfTheWeekChange();
        if (dofWeek == null) {
            dofWeek = changeAlarm.getDaysOfTheWeek();
        }
        Alarm newAlarm = new Alarm(newName, newH, newM, dofWeek);
        Alarm oldAlarm = selected.changeAlarm(changeAlarm.getAlarmName(), newAlarm);
        System.out.println("Successfully Changed " + changeAlarm.getAlarmName() + " To " + newName + ".");
        System.out.println("Alarm List:");
        printAlarms(selected);
    }

    //Modifies: This
    //Effects: calls checks to see if inputs are valid, if so calls
    //doAddAlarShortener, otherwise keeps looping
    private void doAddAlarm() {
        AlarmList selected = selectAlarmList();
        Boolean keepGoing = true;
        while (keepGoing) {
            String t = validNameAdd(selected);
            if (t == null) {
                continue;
            }
            while (keepGoing) {
                int h = validHourAdd();
                if (h == -1) {
                    continue;
                }

                while (keepGoing) {
                    int m = validMinutesAdd();
                    if (m == -1) {
                        continue;
                    }

                    doAddShortener(selected, h, m, daysOfTheWeekAdd(), t);
                    keepGoing = false;
                }
            }
        }
    }

    //Modifies: this
    //Effects: Removes an alarm from an alarmList
    private void doRemoveAlarm() {
        AlarmList selected = selectAlarmList();
        if (selected.numAlarms() == 0) {
            System.out.println("There Are No Alarms To Remove");
        } else {
            System.out.println("Enter Name of Alarm:");
            String name = input.nextLine();

            Alarm remove = selected.removeAlarm(name);
            if (remove == null) {
                System.out.println("Invalid Alarm Name...\nPlease Try Again!");
            } else {
                System.out.println("Successfully Removed " + remove.alarmToString() + "from Alarms");
            }
            System.out.println("Alarms That Remain:");
            printAlarms(selected);
        }
    }

    //Effects: calls doViewer
    private void doViewAlarm() {
        AlarmList selected = selectAlarmList();
        doViewer(selected);

    }

    //Effects:shows the alarm with name, name, in the list of alarms
    private void doViewer(AlarmList selected) {
        if (selected.numAlarms() == 0) {
            System.out.println("There Are No Alarms To View");
        } else {
            System.out.println("Please Write The Name Of The Alarm you Would Like to View");
            String name = input.nextLine();

            Alarm view = null;
            try {
                view = selected.viewAlarm(name);
            } catch (EmptyList emptyList) {
                System.out.println("There Are No Alarms To View");
            }
            if (view == null) {
                System.out.println("Invalid Alarm Name...\nPlease Try Again!");
            } else {
                System.out.println("Success!\nShowing Alarm " + name + "\n" + view.alarmToString());
            }
        }
    }

    //Modifies: this
    //Effects: sorts the selected alarm list based on name in alphabetical order,
    //other wise if there are no alarms says so
    private void doSortAlarmsName() {
        AlarmList selected = selectAlarmList();
        try {
            selected.alphabeticallySorter();
            System.out.println("Successfully Sorted Alarms");
            printAlarms(selected);
        } catch (EmptyList emptyList) {
            assert selected.numAlarms() == 0;
            System.out.println("There Are No Alarms To Sort");
        }
    }

    //Modifies: this
    //Effects: sorts the selected alarm list based on most imminent,
    //other wise if there are no alarms says so
    private void doSortAlarmsImminent() {
        AlarmList selected = selectAlarmList();
        try {
            selected.sortByImminent();
            System.out.println("Successfully Sorted Alarms");
            printAlarms(selected);
        } catch (EmptyList emptyList) {
            System.out.println("There Are No Alarms To Sort");
        }
    }

    //Modifies:this
    //Effects: sorts an alarmList if it is not empty, otherwise an exception is caught
    private void doSortAlarmsTime() {
        AlarmList selected = selectAlarmList();
        try {
            selected.sortAlarmsByTime();
            System.out.println("Successfully Sorted Alarms:");
            printAlarms(selected);
        } catch (EmptyList e) {
            assert selected.numAlarms() == 0;
            System.out.println("There Are No Alarms To Sort");
        }
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

        while (!(selectAlarmListLoop(selection))) {
            if (selection != "") {
                System.out.println("AlarmList Does Not Exist");
            }
            System.out.println("\nSelect User");
            System.out.println("\tMy Alarms(m)");
            System.out.println("\tUser Alarms(u)");
            selection = input.nextLine();
            selection = selection.toLowerCase();
        }
        if (selectAlarmListMine(selection)) {
            System.out.println("You Chose My Alarms");
            return mine;
        } else {
            System.out.println("You Chose User Alarms");
            return user;
        }
    }

    //Effects: processes user input regarding adding an alarm with days of the week
    //if no days are added it is assumed the alarms goes off everyday
    private DaysList daysOfTheWeekAdd() {
        boolean keepGoing = true;
        String command = null;
        DaysList dofWeek = new DaysList();

        while (keepGoing) {
            initForDaysOfTheWeek();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("stop")) {
                dofWeek = defaultDayOfWeek(dofWeek);
                keepGoing = false;
            } else {
                try {
                    DaysOfTheWeek day = processCommandDays(command);
                    dofWeek.addDay(day);
                } catch (NotADay notADay) {
                    System.out.println("\nOops That Is Not A Known Day!\n");
                } catch (ItemAlreadyExists itemAlreadyExists) {
                    System.out.println("\nOops That Day Has Already Been Added!\n");
                }
            }
        }
        return dofWeek;
    }

    //Effects: returns default days list if no days in input, otherwise returns days list
    private DaysList defaultDayOfWeek(DaysList daysList) {
        if (daysList.size() == 0) {
            return defaultList;
        } else {
            return daysList;
        }
    }

    //Effects: checks if the user wants to modify day list, if yes calls daysOfTheWeekAdd.
    private DaysList daysOfTheWeekChange() {
        System.out.println("Would You Like To Change The Days Of The Alarm?\n\tyes or no");
        Boolean bool = ifChange();
        if (bool == false) {
            return null;
        } else {
            return daysOfTheWeekAdd();
        }
    }

    //Effects: checks if an input name is valid and can be added to a list
    //if so returns the name, otherwise returns null
    private String validName(AlarmList selected) {
        String name = input.nextLine();
        Alarm a = null;
        String b = "";
        try {
            a = selected.viewAlarm(name);
        } catch (EmptyList emptyList) {
            b = "No items in List";
        }
        if (b.equals("No items in List") || (a == null)) {
            return name;
        } else {
            System.out.println("That Alarm Already Exists");
            return null;
        }
    }

    //Effects: Writes the correct s-o-u-t statement for do add alarm then sends the input to validName
    private String validNameAdd(AlarmList selected) {
        System.out.println("Enter the Name of Your New Alarm:");
        return validName(selected);
    }

    //Effects: Checks if the user wants to change the alarm name and if they are giving the correct input
    private String validNameChange(AlarmList selected) {
        System.out.println("Would You Like To Change The Name Of The Alarm?\n\tyes or no");
        Boolean bool = ifChange();
        if (bool == false) {
            System.out.println("Change Name Skipped");
            return null;
        } else {
            System.out.println("Enter the Name of The Alarm:");
            String name = null;
            Boolean keepGoing = true;
            while (keepGoing) {
                if (name == null) {
                    name = validName(selected);
                    continue;
                }
                keepGoing = false;
            }
            return name;
        }
    }

    //Effects: determines if the user wants to change an object of an alarm
    private Boolean ifChange() {
        Boolean keepGoing = true;

        boolean answer = false;
        while (keepGoing) {
            String bool = input.nextLine();
            if (bool.equals("yes")) {
                answer = true;
            } else if (bool.equals("no")) {
                answer = false;
            } else {
                continue;
            }
            keepGoing = false;
        }
        return answer;
    }

    //Effects: checks if the user wishes to chang ethe alarm hour and if the user is giving the correct input
    private int validHourChange() {
        System.out.println("Would You Like To Change The Hour Of The Alarm?\n\tyes or no");
        Boolean keepGoing = true;
        int h = 0;
        Boolean bool = ifChange();
        if (bool == false) {
            System.out.println("Change Hour Skipped");
            return -1;
        } else {
            while (keepGoing) {
                h = validHourAdd();
                if (h == -1) {
                    continue;
                }
                System.out.println("Changed Hours to " + h);
                keepGoing = false;
            }
            return h;
        }
    }

    //Effects: checks if the user wants to change the alarm minutes and if the user is giving the correct input
    private int validMinuteChange() {
        System.out.println("Would You Like To Change The Minutes Of The Alarm?\n\tyes or no");
        Boolean bool = ifChange();
        if (bool == true) {
            Boolean keepGoing = true;
            int m = 0;
            while (keepGoing) {
                m = validMinutesAdd();
                if (m == -1) {
                    continue;
                }
                System.out.println("Change Minutes To " + m);
                keepGoing = false;
            }
            return m;
        } else {
            System.out.println("Change Minute Skipped");
            return -1;
        }
    }

    //Effects: checks if an input hour is a valid time, if so
    //returns the hour, otherwise returns -1
    private int validHourAdd() {
        System.out.println("Enter Hours:");
        Boolean keepGoing = true;
        int hours = -1;
        while (keepGoing) {
            String number = (input.nextLine());
            try {
                hours = Integer.parseInt(number);
                if (hours >= 24 || hours < 0) {
                    System.out.println("That Is Not A Valid Hour");
                    hours = -1;
                }
                keepGoing = false;
            } catch (NumberFormatException e) {
                System.out.println("That Is Not An Integer!");
            }
        }
        return hours;
    }


    //Effects: checks if an input minutes is a valid time, if so
    //returns the hour, otherwise returns -1
    private int validMinutesAdd() {
        System.out.println("Enter Minutes:");
        Boolean keepGoing = true;
        int minutes = -1;
        while (keepGoing) {
            String number = (input.nextLine());
            try {
                minutes = Integer.parseInt(number);
                if (minutes > 59 || minutes < 0) {
                    System.out.println("That Is Not A Valid Minute");
                    minutes = -1;
                }
                keepGoing = false;
            } catch (NumberFormatException e) {
                System.out.println("That Is Not An Integer!");
            }
        }
        return minutes;
    }

    //Effects: checks if the alarm is valid for doChangeAlarm
    private Alarm checkAlarm(AlarmList selected) {
        Boolean keepGoing = true;
        Alarm changeAlarm = null;
        while (keepGoing) {
            System.out.println("Enter The Name Of The Alarm you Would Like To Change:");
            String name = input.nextLine();
            changeAlarm = selected.viewer(name);
            if (changeAlarm == null) {
                System.out.println("No Such Alarm Exists");
                continue;
            }
            keepGoing = false;
        }
        return changeAlarm;
    }

    //Effects: prints out the alarms in the alarmList
    public void printAlarms(AlarmList a) {
        System.out.println(a.showAlarms());
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

    //Modifies: this
    //Effects: adds an alarm to the selected alarmList
    private void doAddShortener(AlarmList selected, int h, int m, DaysList dofWeek, String t) {
        Alarm a = new Alarm(t, h, m, dofWeek);
        selected.addAlarm(a);
        System.out.println("Success alarm " + t + " has been added the list of Alarms");
        System.out.println(a.alarmToString());
    }

    //Effects: prints out a guide as to how to add days
    private void initForDaysOfTheWeek() {
        System.out.println("Add Day");
        System.out.println("By Default Everyday is added!");
        System.out.println("Type Stop to stop adding days");
    }

    //Modifies: this
    //Effects: creates the default list of days with everyday
    private void setupDefaultList() {
        defaultList = new DaysList();
        try {
            defaultList.addDay(Monday);
            defaultList.addDay(Tuesday);
            defaultList.addDay(Wednesday);
            defaultList.addDay(Thursday);
            defaultList.addDay(Friday);
            defaultList.addDay(Saturday);
            defaultList.addDay(Sunday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            System.out.println("Added Impossible In Setup Days");
        }
    }

    //Effects: returns true if the user selected my alarm list, and returns false otherwise.
    public boolean selectAlarmListMine(String selection) {
        Boolean keepRunningMine = selection.equals("m") || selection.equals("mine") || selection.equals("my alarms");
        return keepRunningMine;
    }

    //Effects: returns true if the user selected user or my alarm list, otherwise returns false
    public Boolean selectAlarmListLoop(String selection) {
        Boolean keepRunningMine = selectAlarmListMine(selection);
        Boolean keepRunningUser = selection.equals("u") || selection.equals("user") || selection.equals("user alarms");
        Boolean inputValid = keepRunningMine || keepRunningUser;
        return inputValid;
    }


    //Effects: returns true if the user input a command to load or save alarms, otherwise return false
    private Boolean validPersistenceAlarms(String command) {
        Boolean saveAlarmsValid = (command.equals("save") || command.equals("save alarms"));
        Boolean loadAlarmsValid = (command.equals("load") || command.equals("load alarms"));
        Boolean validPersistenceAlarms = loadAlarmsValid || saveAlarmsValid;
        return validPersistenceAlarms;
    }

    //Modifies: this
    //Effects: saves and loads alarmLists to and from file
    private void persistenceAlarms(String command) {
        if (command.equals("save") || command.equals("save alarms")) {
            System.out.println("You Chose To Save Alarms");
            doSaveAlarms();
        } else if (command.equals("load") || command.equals("load alarms")) {
            System.out.println("You Chose To Load Alarms");
            doLoadAlarms();
        }
    }

    //Effects: returns true if the user input a command to modify alarms or show alarms, otherwise returns false
    private Boolean validModifyAlarms(String command) {
        Boolean addAlarmValid = (command.equals("a") || command.equals("add alarm"));
        Boolean removeAlarmValid = (command.equals("r") || command.equals("remove alarm"));
        Boolean changeAlarmValid = (command.equals("c") || command.equals("change alarm"));
        Boolean viewAlarmValid = (command.equals("v") || command.equals("view alarm"));
        Boolean sortAlarmTimeValid = (command.equals("st") || command.equals("sort by time"));
        Boolean sortAlarmNameValid = (command.equals("sn") || command.equals("sort by name"));
        Boolean sortAlarmImminentValid = (command.equals("si") || command.equals("sort by imminent"));
        Boolean showAlarmsValid = (command.equals("show") || command.equals("show alarms"));
        Boolean validSort = sortAlarmNameValid || sortAlarmTimeValid || sortAlarmImminentValid;
        Boolean validPartModifyAlarms = addAlarmValid || removeAlarmValid || changeAlarmValid || validSort;
        Boolean validModifyAlarms = validPartModifyAlarms || showAlarmsValid || viewAlarmValid;
        return validModifyAlarms;
    }

    //Requires:Command to be a valid command
    //Modifies: this
    //Effects: modifies the alarmList or show it according to a given command
    private void modifyAlarms(String command) {
        if (command.equals("a") || command.equals("add alarm")) {
            System.out.println("You Chose To Add an Alarm");
            doAddAlarm();
        } else if (command.equals("r") || command.equals("remove alarm")) {
            System.out.println("You Chose To Remove an Alarm");
            doRemoveAlarm();
        } else if (command.equals("c") || command.equals("change alarm")) {
            System.out.println("You Chose To Change an Alarm");
            doChangeAlarmShortener();
        } else if (command.equals("v") || command.equals("view alarm")) {
            System.out.println("You Chose To View an Alarm");
            doViewAlarm();
        } else if (command.equals("show") || command.equals("show alarms")) {
            System.out.println("You Chose To Show the Alarms");
            doShowAlarms();
        } else {
            modifyAlarmsSort(command);
        }
    }

    //Requires:Command to be a valid command
    //Modifies: this
    //Effects: modifies the sorts the alarmList according to a given command
    private void modifyAlarmsSort(String command) {
        if (command.equals("st") || command.equals("sort by time")) {
            System.out.println("You Chose To Sort the Alarms By Time");
            doSortAlarmsTime();
        } else if (command.equals("sn") || command.equals("sort by name")) {
            System.out.println("You Chose To Sort the Alarms By Name");
            doSortAlarmsName();
        } else if (command.equals("si") || command.equals("sort by imminent")) {
            System.out.println("You Chose To Sort the Alarms By Imminent");
            doSortAlarmsImminent();
        }
    }

}
