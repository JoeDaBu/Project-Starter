package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/*The Alarm class store info about a specific alarm
like the time it should go off, on what days, and its name.
An alarm can be edited to change anyone of its parameters.
 */

public class Alarm implements Writable {
    //private Clock time;
    private int hours;
    private int minutes;
    //private LocalTime localTime;
    private DaysList daysOfTheWeek;
    private String alarmName;

    /*Requires: dofWeek to be a non-empty list with strings of
    only the days of the week, and h to be within 0 to 23. and m to be
    within 0 to 59, and name to be a non-empty string
    Effects: creates an Alarm with alarmName set to name, occurring on
    the days of the week set to dofWeek, at time set to t
     */
    public Alarm(String name, int h, int m, DaysList dofWeek) {
        hours = h;
        daysOfTheWeek = dofWeek;
        alarmName = name;
        minutes = m;
    }

    //Effects: returns the time at which the alarm will go off
    public ArrayList<Integer> getTime() {
        ArrayList<Integer> t = new ArrayList<>();
        t.add(getHours());
        t.add(getMinutes());
        return t;
    }

    //Effects: Returns the int hours
    public int getHours() {
        return hours;
    }

    //Effects:returns the int minutes
    public int getMinutes() {
        return minutes;
    }

    //Effects: returns the days of the week the alarm will go off
    public DaysList getDaysOfTheWeek() {
        return daysOfTheWeek;
    }

    //Effects: returns the name of the alarm
    public String getAlarmName() {
        return alarmName;
    }

    //Requires: double t is in within 0 to 24
    //Modifies: this
    //Effects: resets time to t
    public void changeTime(int h, int m) {
        hours = h;
        minutes = m;
    }

    //Requires: name to be an non-empty string
    //Modifies: this
    //Effects: resets alarmName to name
    public void changeAlarmName(String name) {
        alarmName = name;
    }

    //Requires: dofWeek to be a non-empty list
    //Modifies: This
    //Effects: resets daysOfTheWeek to dofWeek
    public void changeDaysOfTheWeek(DaysList dofWeek) {
        daysOfTheWeek = dofWeek;
    }

    //Effects: converts the alarm to a string beginning with the alarm name, then time
    //it goes off, and on what days
    public String alarmToString() {
        String day = Arrays.toString(daysOfTheWeek.toArray());
        String alarm = alarmName + " " + hours + ":" + minutes + " " + day;
        return alarm;
    }


    /*public boolean alarmGoesOff() {
        Date t = Calendar.getInstance().getTime();
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        String h = hourFormat.format(t);
        System.out.println("01");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        String m = minuteFormat.format(t);
        assert h.equals("01");
        return (h.equals(Integer.toString(hours)) && m.equals(Integer.toString(minutes)));
    }

     */

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", alarmName);
        json.put("hour", hours);
        json.put("minutes", minutes);
        json.put("Days of the Week", daysOfTheWeek);
        return json;
    }
}
