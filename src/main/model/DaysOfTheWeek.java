package model;

import model.exceptions.NotADay;

//Represents the days of the week with corresponding int values
public enum DaysOfTheWeek {
    Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6), Sunday(7);

    int dayNum;

    DaysOfTheWeek(int i) {
        dayNum = i;
    }

    DaysOfTheWeek showDay() {
        for (DaysOfTheWeek m : DaysOfTheWeek.values()) {
            if (dayNum == m.dayNum) {
                return m;
            }
        }
        throw new NotADay();
    }

    int showDayNum() {
        return dayNum;
    }
}
