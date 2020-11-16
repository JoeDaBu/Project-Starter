package ui.gui;

import model.Alarm;
import model.AlarmList;

public interface Observer {

    //Modifies: this
    //Effects: adds an alarm
    void updateAdd(String name, Alarm alarm);

    //Modifies: this
    //Effects: removes an alarm
    void updateRemove(String name, Alarm alarm);

    //Effects: toggles the view of an alarm
    void updateView(String name);

    //Modifies: this
    //Effects: changes an alarm
    void updateChange(String oldName, Alarm oldAlarm, String newName, Alarm newAlarm);

    //Effects: toggles the view of all alarms
    void updateShow();

    //Modifies: this
    //Effects: sorts the alarms
    void updateSort(AlarmList alarmList);

    //Modifies: this
    //Effects: updates the name
    void updateName(String name);

    //Modifies: this
    //Effects: removes all added alarms
    void updateRemoveAll();
}
