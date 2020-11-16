package model;

import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
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
        try {
            testList.addDay(Monday);
            assertEquals(testList.getDays().get(0), Monday);
            testList.addDay(Friday);
            assertEquals(testList.getDays().get(1), Friday);
            testList.addDay(Saturday);
            testList.addDay(Thursday);
            testList.addDay(Sunday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("No exception expected");
        }
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
            testList.removeDay(Tuesday);
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
        } catch (EmptyList | ListObjectNonExistent emptyList) {
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
    public void testSortDays() {
        try {
            testList2.addDay(Thursday);
            testList2.addDay(Wednesday);
            testList2.addDay(Friday);
            testList2.addDay(Monday);
            testList2.addDay(Sunday);
            testList2.sortDays();
        } catch (EmptyList emptyList) {
            fail("empty");
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        assertEquals(testList2.showDays(), "Monday, Wednesday, Thursday, Friday, Sunday");
    }


    @Test
    public void testShowDays() {
        String t = testList.showDays();
        String expectedT = "Monday, Thursday, Friday, Saturday, Sunday";
        assertEquals(t, expectedT);
    }

    @Test
    public void testShowDaysEmpty() {
        String empty = testList2.showDays();
        assertEquals(empty, "There Are No Days to Show");
    }

    @Test
    public void testShowDaysOne() {
        try {
            testList2.addDay(Monday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        String monday = testList2.showDays();
        assertEquals(monday, "Monday");
    }

    @Test
    public void testShowDaysMultiple() {
        try {
            testList2.addDay(Monday);
            testList2.addDay(Thursday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        String monday = testList2.showDays();
        assertEquals(monday, "Monday, Thursday");
    }

    @Test
    public void testChangeDayAlreadyExists() {
        try {
            testList.changeDay(Sunday, Monday);
            fail("Expected ItemAlreadyExist exception");
        } catch (ItemAlreadyExists itemAlreadyExists) {
            //expected
        } catch (ListObject listObject) {
            fail("test is not expected to reach this point");
        }
    }
}
