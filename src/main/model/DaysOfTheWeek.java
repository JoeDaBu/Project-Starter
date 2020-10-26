package model;

import model.exceptions.NotADay;

//Represents the days of the week with corresponding int values
public enum DaysOfTheWeek {
    Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6), Sunday(7);

    int dayNum;

    //Effects: matches each day with its corresponding number
    DaysOfTheWeek(int i) {
        dayNum = i;
    }


    //Effects: returns the day's number
    int showDayNum() {
        return dayNum;
    }

    //    //Effects: returns the day based on the day number given
//    DaysOfTheWeek showDay() {
//        for (DaysOfTheWeek m : DaysOfTheWeek.values()) {
//            if (dayNum == m.dayNum) {
//                return m;
//            }
//        }
//        throw new NotADay();
//    }
}
