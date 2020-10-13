package model;

import java.util.ArrayList;

/*The class alarmList stores alarms created and is the list
of active alarms that are to go off later.
This list can be edited to add and remove alarms
 */

public class AlarmList {
    private ArrayList<Alarm> alarms;

    //Effects: Creates a new empty list of alarms
    public AlarmList() {
        alarms = new ArrayList<>();
    }

    /*Requires: a to have a unique name
    Modifies: this
    Effects: add the alarm a to the list alarms*/
    public void addAlarm(Alarm a) {
        alarms.add(a);
    }

    /*
    Modifies:this
    Effects: removes the alarm called name from the list of alarms,
    alarms, and returns it if it was removed, otherwise
    returns null*/
    public Alarm removeAlarm(String name) {
        for (Alarm ala : alarms) {
            if (ala.getAlarmName().equals(name)) {
                alarms.remove(ala);
                return ala;
            }
        }
        return null;
    }

    //Effects: Returns the list of alarms, alarms
    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    //Requires: newAlarm to be different from all other alarms in the alarm
    //list
    //Modifies: this
    //Effects: replaces alarm with the alarm name, name, with Alarm new alarm
    //and returns the removed alarm if an alarm was removed, otherwise
    //returns null
    public Alarm changeAlarm(String name, Alarm newAlarm) {
        for (int i = 0; i < alarms.size(); i++) {
            if (alarms.get(i).getAlarmName().equals(name)) {
                return alarms.set(i, newAlarm);
            }
        }
        return null;
    }

    //Effects: Returns the alarm called name from the list of alarms
    public Alarm viewAlarm(String name) {
        for (Alarm a : alarms) {
            if (a.getAlarmName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    //Modifies: this
    //Effects: replaces alarms with the sorted verison of alarms by the time they go off
    //and throws an exception when trying to sort an empty list of alarms
    public void sortAlarms() throws Exception {
        if (alarms.size() == 0) {
            throw new Exception("There Are No Alarms To Sort");
        } else {
            ArrayList<Alarm> oldAlarms = alarms;
            alarms = sorter();
            assert alarms != oldAlarms;
        }
    }

    //Effects: sorts the list alarms by the time they go off, with the earliest first
    public ArrayList<Alarm> sorter() {
        ArrayList<Alarm> alarmsByEarliest = new ArrayList<>();
        alarmsByEarliest.add(alarms.get(0));
        int n = 1;
        for (int i = 1; i < alarms.size(); i++) {
            int alarmsHours = alarms.get(i).getHours();
            int alarmsMinutes = alarms.get(i).getMinutes();
            int earlyAlarmLat = alarmsByEarliest.get(alarmsByEarliest.size() - 1).getHours();
            int earlyAlarmLatestM = alarmsByEarliest.get(alarmsByEarliest.size() - 1).getMinutes();
            if (alarmsHours > earlyAlarmLat || (alarmsHours == earlyAlarmLat && alarmsMinutes > earlyAlarmLatestM)) {
                alarmsByEarliest.add(alarms.get(i));
            } else {
                for (int t = 0; t < alarmsByEarliest.size(); t++) {
                    int earlyAlarm = alarmsByEarliest.get(t).getHours();
                    int earlyAlarmM = alarmsByEarliest.get(t).getMinutes();
                    if ((alarmsHours < earlyAlarm) || ((alarmsHours == earlyAlarm) && (alarmsMinutes <= earlyAlarmM))) {
                        alarmsByEarliest.add(t, alarms.get(i));
                        break;
                    }
                }
            }
        }
        return alarmsByEarliest;
    }

    //Effects: converts the list alarms to a string of all the alarms
    public String showAlarms() {
        String show;
        if (alarms.size() == 0) {
            show = "There Are No Alarms to Show";
        } else {
            show = alarms.get(0).alarmToString();
            if (alarms.size() > 1) {
                for (int i = 1; i < alarms.size(); i++) {
                    show += ", " + alarms.get(i).alarmToString();
                }
            }
        }
        return show;
    }

    //Effects:returns the size of the list of alarms
    public int size() {
        return alarms.size();
    }

    public boolean alarmSounds() {
        boolean t = false;
        for (Alarm a : alarms) {
            if (a.alarmGoesOff()) {
                t = true;
            }
        }
        return t;
    }

}
