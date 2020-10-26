package model;

import model.exceptions.EmptyList;
import model.exceptions.ListObject;
import model.exceptions.ListObjectNonExistent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.DaysOfTheWeek.*;
import static org.junit.jupiter.api.Assertions.*;

public class DaysListTest {
    DaysList testList;
    DaysList testList2;

    @BeforeEach
    public void makeDaysAndLists() {
        testList = new DaysList();
        testList.addDay(Monday);
        assertEquals(testList.getDays().get(0), Monday);
        testList.addDay(Friday);
        assertEquals(testList.getDays().get(1), Friday);
        testList.addDay(Saturday);
        testList.addDay(Thursday);
        testList.addDay(Sunday);
        testList2 = new DaysList();
    }

    @Test
    public void testRemoveDayEmpty() {
        try {
            testList2.removeDay(Monday);
            fail("Expected empty list exception");
        } catch (EmptyList emptyList) {
            //expected
        } catch (ListObjectNonExistent listObjectNonExistent) {
            fail("Expected EmptyList exception");
        }
    }

    @Test
    public void testRemoveDayNotContained() {
        try {
            testList.removeDay(Sunday);
            fail("Sunday is not contained within testList");
        } catch (EmptyList emptyList) {
            fail("TestList is a non empty list");
        } catch (ListObjectNonExistent listObjectNonExistent) {
           //expected
        }
    }

    @Test
    public void testRemoveDay() {
        try {
            testList.removeDay(Sunday);
        } catch (EmptyList emptyList) {
            fail("expected not exception");
        } catch (ListObjectNonExistent listObjectNonExistent) {
            fail("expected not exception");
        }
        assertEquals(testList.dayListSize(), 4);
        assertFalse(testList.containDay(Sunday));
    }

    @Test
    public void testChangeDay() {
        try {
            testList.changeDay(Sunday, Tuesday);
        } catch (ListObject listObject) {
            fail("No exception expected");
        }
        assertEquals(testList.getDays().get(0), Monday);
        assertEquals(testList.getDays().get(1), Tuesday);
        assertEquals(testList.getDays().get(2), Thursday);
        assertEquals(testList.getDays().get(3), Friday);
        assertEquals(testList.getDays().get(4), Saturday);
        assertEquals(testList.dayListSize(), 5);
    }

    @Test
    public void testSortDaysEmpty() {
        try {
            testList2.sortDays();
            fail("expected empty list exception");
        } catch (EmptyList emptyList) {
            //expected
        }
    }

    @Test
    public void testShowDays() {
        String t = testList.showDays();
        String expectedT = "";
        assertEquals(t, expectedT);
    }
}
