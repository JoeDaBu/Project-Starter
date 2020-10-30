package model;

import model.exceptions.EmptyList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*The class alarmList stores alarms created and is the list
of active alarms that are to go off later.
This list can be edited to add and remove alarms
 */

public class AlarmList implements Writable {
    private String name;
    private ArrayList<Alarm> alarms;

    //Effects: Creates a new empty list of alarms
    public AlarmList(String name) {
        this.name = name;
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

    //Requires: newAlarm to have a different name from all other alarms in the alarm
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

    //Effects: throws an exception if the alarms list is empty otherwise
    //calls viewer
    public Alarm viewAlarm(String name) throws EmptyList {
        Alarm a;
        if (alarms.size() == 0) {
            throw new EmptyList();
        } else {
            a = viewer(name);
            return a;
        }
    }

    //Requires: A non empty list
    //Effects: Returns the alarm called name from the list of alarms
    public Alarm viewer(String name) {
        for (Alarm a : alarms) {
            if (a.getAlarmName().equals(name)) {
                return a;
            }
        }
        return null;
    }


    //Modifies: this
    //Effects: replaces alarms with the sorted version of alarms by the time they go off
    //and throws an exception when trying to sort an empty list of alarms
    public void sortAlarmsByTime() throws EmptyList {
        if (alarms.size() == 0) {
            throw new EmptyList();
        } else {
            ArrayList<Alarm> oldAlarms = alarms;
            alarms = timeSorter();
        }
    }

    //Requires: A non empty list
    //Effects: sorts the list alarms by the time they go off, with the earliest first
    public ArrayList<Alarm> timeSorter() {
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

    //Modifies: this
    //Effects: sorts alarms list by name
    public ArrayList<Alarm> alphabeticallySorter() throws EmptyList {
        int size = alarms.size();
        if (size == 0) {
            throw new EmptyList();
        } else {
            Collections.sort(alarms, Comparator.comparing(Alarm::getAlarmName));
            return alarms;
        }
    }

    //Effects: converts the list alarms to a string of all the alarms and returns it
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
    public int numAlarms() {
        return alarms.size();
    }

    /*public boolean alarmSounds() {
        boolean t = false;
        for (Alarm a : alarms) {
            if (a.alarmGoesOff()) {
                t = true;
            }
        }
        return t;
    }

     */

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("alarms", alarmsToJson());
        return json;
    }

    //returns alarms in the alarmList as a json array
    public JSONArray alarmsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Alarm a : alarms) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

    //Effects: returns the name of the alarm list
    public String getName() {
        return name;
    }

    //Modifies: This
    //Effects: changes the alarmList name to name and returns it
    public String setName(String name) {
        this.name = name;
        return name;
    }
}
