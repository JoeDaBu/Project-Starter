package model;

import java.util.ArrayList;
import java.util.EnumSet;

//list of days of the week
public class DaysList {
    ArrayList<DaysOfTheWeek> daysList;
    ArrayList<DaysOfTheWeek> orderedDaysList;

    public DaysList() {
        daysList = new ArrayList<>();
        createOrderedDaysList();
    }

    private void createOrderedDaysList() {
        orderedDaysList = new ArrayList<>(EnumSet.allOf(DaysOfTheWeek.class));
    }

    public void addDay(DaysOfTheWeek day) {
        daysList.add(day);
        sortDays();
    }

    public void removeDay(DaysOfTheWeek day) {
        daysList.remove(day);
    }

    public void changeDay(DaysOfTheWeek changeDay, DaysOfTheWeek newDay) {
        removeDay(changeDay);
        addDay(newDay);
    }

    public ArrayList<DaysOfTheWeek> showDays() {
        sortDays();
        return daysList;
    }

    public void sortDays() {
        ArrayList<DaysOfTheWeek> sortedDaysList = new ArrayList<>();
        for (DaysOfTheWeek o : orderedDaysList) {
            for (DaysOfTheWeek d : daysList) {
                if (o == d && !sortedDaysList.contains(d)) {
                    sortedDaysList.add(d);
                }
            }
        }
        daysList = sortedDaysList;
    }
}
