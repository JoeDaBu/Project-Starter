package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.DaysOfTheWeek.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaysOfTheWeekTest {
    DaysOfTheWeek m;
    DaysOfTheWeek f;

    @BeforeEach
    public void makeDays() {
        m = Monday;
        f = Friday;
    }

    @Test
    public void testShowDayNum() {
        assertEquals(m.showDayNum(), 1);
        assertEquals(f.showDayNum(), 5);
    }
}
