package model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.DaysOfTheWeek;

import static model.DaysOfTheWeek.*;

//list of days of the week
public class DaysList {
    LinkedHashMap daysList;
    LinkedHashMap  orderedDaysList;

    public DaysList() {
        daysList = new LinkedHashMap();
        createOrderedDaysList();
    }

    private void createOrderedDaysList() {
        orderedDaysList = new LinkedHashMap();
        orderedDaysList.put(1, Monday);
        orderedDaysList.put(2, Tuesday);
        orderedDaysList.put(3, Wednesday);
        orderedDaysList.put(4, Thursday);
        orderedDaysList.put(5, Friday);
        orderedDaysList.put(6, Saturday);
        orderedDaysList.put(7, Sunday);
    }

    public void addDay(DaysOfTheWeek day) {
        for (Object i:orderedDaysList.values()) {

        }
        daysList.put(day);
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
