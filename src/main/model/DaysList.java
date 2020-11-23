package model;

import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
import model.exceptions.ListObject;
import model.exceptions.ListObjectNonExistent;

import java.util.ArrayList;

import static model.DaysOfTheWeek.*;

//list of days of the week
public class DaysList {
    public static final ArrayList<DaysOfTheWeek> defaultList = new ArrayList<>();
    ArrayList<DaysOfTheWeek> daysList;

    //Effects: creates a new empty list of days
    public DaysList() {
        daysList = new ArrayList<>();
        defaultList.add(Monday);
        defaultList.add(Tuesday);
        defaultList.add(Wednesday);
        defaultList.add(Thursday);
        defaultList.add(Friday);
        defaultList.add(Saturday);
        defaultList.add(Sunday);
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
    //Effects: adds day to the list of days, it the day already exist throws
    // the already exists exception
    public void addDay(DaysOfTheWeek day) throws ItemAlreadyExists {
        if (daysList.contains(day)) {
            throw new ItemAlreadyExists();
        }
        add(day);
    }

    //Requires: No duplicate days are added
    //Modifies: this
    //Effects: adds day to days list
    private void add(DaysOfTheWeek day) {
        daysList.add(day);
        try {
            sortDays();
        } catch (EmptyList emptyList) {
            System.out.println("Impossible Reached In Add Day");
        }
    }

    //Modifies: this
    //Effects: removes day from the list of days, otherwise throws empty list if the list
    //was empty or list object non existent if the object to remove is not in the list
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

    //Modifies: this
    //Effects: changes change day to new in the list of days, throws list object is newDay
    // already exist, or change day doesn't
    public void changeDay(DaysOfTheWeek changeDay, DaysOfTheWeek newDay) throws ListObject {
        removeDay(changeDay);
        addDay(newDay);
        sortDays();
    }

    //Effects: converts daysList to an array
    public Object[] toArray() {
        return daysList.toArray();
    }

    //Effects: returns the size of daysList
    public int size() {
        return daysList.size();
    }

    //Effects: gets the object at index i in days list and returns it
    public DaysOfTheWeek get(int i) {
        return daysList.get(i);
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
    private ArrayList<DaysOfTheWeek> sortDaysSorter() {
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
                        break;
                    }
                }
            }
        }
        return sortedDaysList;
    }
}
