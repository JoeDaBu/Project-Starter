package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static model.DaysOfTheWeek.*;

/*The Alarm class store info about a specific alarm
like the time it should go off, on what days, and its name.
An alarm can be edited to change anyone of its parameters.
 */

public class Alarm implements Writable {
    private int hours;
    private int minutes;
    private DaysList daysOfTheWeek;
    private String alarmName;
    private ArrayList<Timer> timerList;

    /*Requires: dofWeek to be a non-empty list with strings of
    only the days of the week, and h to be within 0 to 23. and m to be
    within 0 to 59, and name to be a non-empty string
    Effects: creates an Alarm with alarmName set to name, occurring on
    the days of the week set to dofWeek, at time set to t
     */
    public Alarm(String name, int h, int m, DaysList dofWeek) {
        timerList = new ArrayList<>();
        hours = h;
        alarmName = name;
        minutes = m;
        if (dofWeek.size() == 0) {
            daysOfTheWeek = everyday();
        } else {
            daysOfTheWeek = dofWeek;
        }
        createAlarmTask();
    }

    //Effects:Creates the default day when no input is put in for dofWeek
    public DaysList everyday() {
        DaysList defaultList = new DaysList();
        defaultList.add(Monday);
        defaultList.add(Tuesday);
        defaultList.add(Wednesday);
        defaultList.add(Thursday);
        defaultList.add(Friday);
        defaultList.add(Saturday);
        defaultList.add(Sunday);
        return defaultList;
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
        cancelAlarmTask();
        createAlarmTask();
    }

    //Requires: name to be an non-empty string
    //Modifies: this
    //Effects: resets alarmName to name
    public void changeAlarmName(String name) {
        alarmName = name;
        cancelAlarmTask();
        createAlarmTask();
    }

    //Requires: dofWeek to be a non-empty list
    //Modifies: This
    //Effects: resets daysOfTheWeek to dofWeek
    public void changeDaysOfTheWeek(DaysList dofWeek) {
        daysOfTheWeek = dofWeek;
        cancelAlarmTask();
        createAlarmTask();
    }

    //Effects: converts the alarm to a string beginning with the alarm name, then time
    //it goes off, and on what days
    public String alarmToString() {
        String day = Arrays.toString(daysOfTheWeek.toArray());
        String alarm = alarmName + " " + hours + ":" + minutes + " " + day;
        return alarm;
    }

    //Modifies: This
    //Effects: Stops all Timers in this alarm
    public void cancelAlarmTask() {
        for (Timer t: timerList) {
            t.cancel();
        }
        timerList = new ArrayList<>();
    }

    //Modifies: This
    //Effects: Sets the timer at the specified time, for each day listed
    public void createAlarmTask() {
        for (int i = 0; i < daysOfTheWeek.size(); i++) {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.YEAR, Year.now().getValue());
            date.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
            date.set(Calendar.DAY_OF_MONTH, getNextOccurrence(daysOfTheWeek.get(i)));
            date.set(Calendar.HOUR_OF_DAY, hours);
            date.set(Calendar.MINUTE, minutes);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);

            Timer timer = new Timer(alarmName);
            AlarmTask task = new AlarmTask();
            timer.schedule(task, date.getTime(), 604800000);
            timerList.add(timer);
        }
    }

    //Effects: returns the integer value of when the specified day will occur next in the month, or if its today
    public int getNextOccurrence(DaysOfTheWeek days) {
        LocalDate nextOrSame = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.of((days.showDayNum()))));
        return nextOrSame.getDayOfMonth();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", alarmName);
        json.put("hour", hours);
        json.put("minutes", minutes);
        json.put("Days of the Week", daysOfTheWeek.daysList);
        return json;
    }
}
