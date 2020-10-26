package model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.DaysOfTheWeek;
import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
import model.exceptions.ListObject;
import model.exceptions.ListObjectNonExistent;

import static model.DaysOfTheWeek.*;

//list of days of the week
public class DaysList {
    ArrayList<DaysOfTheWeek> daysList;

    //Effects: creates a new empty list of days
    public DaysList() {
        daysList = new ArrayList<DaysOfTheWeek>();
    }

    //Effects: reestablishing the size method
    public int dayListSize() {
        return daysList.size();
    }

    //Effects: re-establishing the contains method
    public Boolean containDay(DaysOfTheWeek day) {
        return daysList.contains(day);
    }

    //Modifies: this
    //Effects: adds day to the list of days
    public void addDay(DaysOfTheWeek day) {
        daysList.add(day);
        try {
            sortDays();
        } catch (EmptyList emptyList) {
            System.out.println("Impossible Outcome Reached, EmptyList exception thrown for a non empty list");
        }
    }

    //Requires: day to be in the list of days
    //Modifies: this
    //Effects: removes day from the list of days
    public void removeDay(DaysOfTheWeek day) throws EmptyList, ListObjectNonExistent {
        if (daysList.size() == 0) {
            throw new EmptyList();
        } else if (!(daysList.contains(day))) {
            throw new ListObjectNonExistent();
        } else {
            daysList.remove(day);
        }
        sortDays();
    }

    //Requires: change day to be in the list of days
    //Modifies: this
    //Effects: changes change day to new in the list of days
    public void changeDay(DaysOfTheWeek changeDay, DaysOfTheWeek newDay) throws ListObject {
        if (daysList.contains(newDay)) {
            throw new ItemAlreadyExists();
        }
        removeDay(changeDay);
        addDay(newDay);
        sortDays();
    }

    //Effects: returns daysList
    public ArrayList<DaysOfTheWeek> getDays() {
        return daysList;
    }

    //Effects: converts the list alarms to a string of all the alarms and returns it
    public String showDays() {
        String show;
        if (daysList.size() == 0) {
            show = "There Are No Days to Show";
        } else {
            show = daysList.get(0).toString();
            if (daysList.size() > 1) {
                for (int i = 1; i < daysList.size(); i++) {
                    show += ", " + daysList.get(i).toString();
                }
            }
        }
        return show;
    }

    //Modifies: this
    //Effects: sorts days list based on when the days appear in a new week
    //throws an empty list exception if the list to sort is empty
    public void sortDays() throws EmptyList {
        if (daysList.size() == 0) {
            throw new EmptyList();
        } else {
            daysList = sortDaysSorter();
        }
    }

    //Requires: Duplicate days do not exist, and daysList to be a non empty list
    //Effects: returns a new list sorted days list that is days list but sorted
    //by the order the days appear in a new week
    public ArrayList<DaysOfTheWeek> sortDaysSorter() {
        ArrayList<DaysOfTheWeek> sortedDaysList = new ArrayList<>();
        for (DaysOfTheWeek d : daysList) {
            if (sortedDaysList.isEmpty()) {
                sortedDaysList.add(d);
            } else if (d.showDayNum() > sortedDaysList.get(sortedDaysList.size() - 1).showDayNum()) {
                sortedDaysList.add(d);
            } else {
                for (int i = 0; i < sortedDaysList.size(); i++) {
                    if (d.showDayNum() < sortedDaysList.get(i).showDayNum()) {
                        sortedDaysList.add(i, d);
                    }
                }
            }
        }
        return sortedDaysList;
    }
}
