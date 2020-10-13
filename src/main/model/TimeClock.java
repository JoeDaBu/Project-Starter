package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*The source of time(the class that reads the
current date and time), Aslo ticks every second to match
real time.
 */

public class TimeClock {
    public SimpleDateFormat time;
    public SimpleDateFormat day;
    public SimpleDateFormat date;
    public Date completeTime;

    public TimeClock() {
        time = new SimpleDateFormat("hh:mm:ss a");
        day = new SimpleDateFormat("EEEE");
        date = new SimpleDateFormat("MMMMM dd, yyyy");
        completeTime = Calendar.getInstance().getTime();
    }


    public String currentTime() {
        return time.format(completeTime);
    }


    public String currentDay() {
        return day.format(completeTime);
    }


    public String currentDate() {
        return date.format(completeTime);
    }
}
