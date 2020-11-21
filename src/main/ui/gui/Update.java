package ui.gui;

import model.Alarm;

import java.util.ArrayList;

//The class responsible for telling the other classes to change
public class Update {

    private final AlarmController controller;
    private final MenuBar menuBar;
    private final ArrayList<Observer> updaters;

    //Effects: initializes and sets up all objects that need to be changed
    public Update(AlarmController controller,
                  MenuBar menuBar) {
        updaters = new ArrayList<>();
        this.menuBar = menuBar;
        this.menuBar.setUpdate(this);
        this.controller = controller;
        this.controller.setUpdate(this);
    }

    //Modifies: this
    //Effects: adds an observer to the list of updaters
    public void addObservers(Observer o) {
        updaters.add(o);
    }

    //Modifies This
    //Effects: removes the observer o frm the list updaters
    public void removeObserver(Observer o) {
        updaters.remove(o);
    }

    //Effects: notifies all observers to add an alarm
    public void updateAdd(String name, Alarm alarm) {
        for (Observer o : updaters) {
            o.updateAdd(name, alarm);
        }
    }

    //Effects: notifies all observers to remove an alarm
    public void updateRemove(String name, Alarm alarm) {
        for (Observer o : updaters) {
            o.updateRemove(name, alarm);
        }
    }

    //Effects: notifies all observers to change an alarm
    public void updateChange(String oldName, Alarm oldAlarm, String newName, Alarm newAlarm) {
        for (Observer o : updaters) {
            o.updateChange(oldName, oldAlarm, newName, newAlarm);
        }
    }

    //Effects: notifies all observers to toggle view an alarm
    public void updateView(String name) {
        for (Observer o : updaters) {
            o.updateView(name);
        }
    }

    //Effects: notifies all observers to toggle image
    public void updateImage() {
        AlarmClock toUpdate = null;
        for (Observer o : updaters) {
            if (!(o.getClass() == AlarmClock.class)) {
                o.updateImage();
            } else {
                toUpdate = (AlarmClock) o;
            }
        }
        toUpdate.updateImage();
    }

    //Effects: notifies all observers to toggle show all alarms
    public void updateShow() {
        for (Observer o : updaters) {
            o.updateShow();
        }
    }

    //Effects: notifies all observers to sort all alarms
    public void updateSort() {
        for (Observer o : updaters) {
            o.updateSort(controller.alarmListGUI);
        }
    }

    //Effects: notifies all observers to change name
    public void updateName(String name) {
        for (Observer o : updaters) {
            o.updateName(name);
        }
    }

    //Effects: notifies all observers to remove all
    public void updateRemoveAll() {
        for (Observer o : updaters) {
            o.updateRemoveAll();
        }
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == controller.sort) {
//            System.out.println("Working");
//
//        }
//    }

//    private String getLatestAlarmName() {
//        return controller
//                .alarmListGUI
//                .getAlarms()
//                .get(controller.alarmListGUI.numAlarms() - 1)
//                .getAlarmName();
//    }
}
