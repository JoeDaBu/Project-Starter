package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;

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
        if ((dofWeek == null) || (dofWeek.size() == 0)) {
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
        return alarmName + " " + hours + ":" + minutes + " " + day;
    }

    //Modifies: This
    //Effects: Stops all Timers in this alarm
    public void cancelAlarmTask() {
        for (Timer t : timerList) {
            t.cancel();
        }
        timerList = new ArrayList<>();
    }

    //Modifies: This
    //Effects: Sets the timer at the specified time, for each day listed
    public void createAlarmTask() {
        for (int i = 0; i < daysOfTheWeek.size(); i++) { //for every day of the alarm creates a timer
            Calendar date = Calendar.getInstance();//gets current time
            date.set(Calendar.YEAR, Year.now().getValue());
            date.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
            date.set(Calendar.DAY_OF_MONTH, getNextOccurrence(daysOfTheWeek.get(i)));
            //sets the date to next occurrence of the input day of the week
            date.set(Calendar.HOUR_OF_DAY, hours);//sets date hours to hours
            date.set(Calendar.MINUTE, minutes);//sets date minutes to minutes
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);

            Timer timer = new Timer(alarmName);//initializes a new timer with name alarmName
            AlarmTask task = new AlarmTask();//initializes a new alarmTask
            timer.schedule(task, date.getTime(), 604800000);
            //sets a timer to ring at time input every week
            timerList.add(timer);
            //adds timer to the list of timers corresponding to this alarm
        }
    }

    //Effects: returns the integer value of when the specified day will occur next in the month, or if its today
    public int getNextOccurrence(DaysOfTheWeek days) {
        DayOfWeek day = (DayOfWeek.of((days.showDayNum())));
        LocalDate nextOrSame = LocalDate.now().with(TemporalAdjusters.nextOrSame(day));

        Calendar current = Calendar.getInstance();
        Boolean today = (nextOrSame.getDayOfMonth() == current.get(Calendar.DAY_OF_MONTH));
        Boolean hourPrior = (hours < current.get(Calendar.HOUR_OF_DAY));
        Boolean hourEqual = (hours == current.get(Calendar.HOUR_OF_DAY));
        Boolean timePrior = hourEqual && (current.get(Calendar.MINUTE) > minutes);

        if (today && (timePrior || hourPrior)) {
            LocalDate next = LocalDate.now().with(TemporalAdjusters.next(day));
            return next.getDayOfMonth();

        } else {
            return nextOrSame.getDayOfMonth();

        }
    }

    //Effects: get the next occurrence of the alarm
    public int nextOccurrence() {
        ArrayList<Integer> negative = getNegative();
        ArrayList<Integer> positive = getPositive();
        int timeTo = 1000000000;
        int lists = categorizeLists(negative, positive);
        int today = getToday();
        if ((lists < today) || (today == -1)) {
            timeTo = lists;
        } else {
            timeTo = today;
        }
        return timeTo;
    }

    //Effects: checks negative and positives list to find the next occurrence of the alarm
    public int categorizeLists(ArrayList<Integer> negative, ArrayList<Integer> positive) {
        int timeTo;
        if (positive.isEmpty()) {
            int smallestInt = 0;
            for (int i = 0; i < negative.size(); i++) {
                if (negative.get(i) < smallestInt) {
                    smallestInt = negative.get(i);
                }
            }
            timeTo = (((smallestInt + 7) * 86400000) + getTimeTo());
        } else {
            int smallestInt = 1000000000;
            for (int i = 0; i < positive.size(); i++) {
                if (positive.get(i) < smallestInt) {
                    smallestInt = positive.get(i);
                }
            }
            timeTo = (smallestInt * 86400000) + getTimeTo();
        }
        return timeTo;
    }

    //Effects: creates the positive list of occurrences
    public ArrayList<Integer> getPositive() {
        ArrayList<Integer> positive = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) {
            day = 7;
        }
        for (int i = 0; i < daysOfTheWeek.size(); i++) {
            int nextDay = (daysOfTheWeek.get(i).dayNum - day);
            if (nextDay > 0) {
                positive.add(nextDay);
            }
        }
        return positive;
    }

    //Effects: create the negative list of occurrences
    public ArrayList<Integer> getNegative() {
        ArrayList<Integer> negative = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) {
            day = 7;
        }
        for (int i = 0; i < daysOfTheWeek.size(); i++) {
            int nextDay = (daysOfTheWeek.get(i).dayNum - day);
            if (nextDay < 0) {
                negative.add(nextDay);
            }
        }
        return negative;
    }

    //Effects: if the alarm goes off today gets how long ago it went off or time till it goes off
    public Integer getToday() {
        ArrayList<Integer> today = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int timeTill = -1;
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) {
            day = 7;
        }
        for (int i = 0; i < daysOfTheWeek.size(); i++) {
            int nextDay = (daysOfTheWeek.get(i).dayNum - day);
            if (nextDay == 0) {
                today.add(nextDay);
            }
        }
        if (!(today.isEmpty())) {
            if (today()) {
                timeTill = getTimeTo();
            } else {
                timeTill = getTimeTo() + 604800000;
            }
        } else {
            timeTill = -1;
        }
        return timeTill;
    }

    //Effects: returns is the alarm will go off later in the day
    public Boolean today() {
        Calendar calendar = Calendar.getInstance();
        Boolean hourPrior = (hours > calendar.get(Calendar.HOUR_OF_DAY));
        Boolean hourEqual = (hours == calendar.get(Calendar.HOUR_OF_DAY));
        Boolean timePrior = hourEqual && (calendar.get(Calendar.MINUTE) < minutes);
        return (timePrior || hourPrior);
    }

    //Effects: gets the time till or past since the all goes/went off
    private int getTimeTo() {
        Calendar calendar = Calendar.getInstance();
        int hoursTill = hours - calendar.get(Calendar.HOUR_OF_DAY);
        int minutesTill = minutes - calendar.get(Calendar.MINUTE);
        return ((hoursTill * 3600000) + (minutesTill * 60000));
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

    public ArrayList<Timer> getTimerList() {
        return timerList;
    }
}
