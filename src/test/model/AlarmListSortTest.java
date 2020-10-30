package model;

import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.DaysOfTheWeek.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AlarmListSortTest {
    public Alarm test;
    public Alarm test2;
    public Alarm test3;
    public Alarm test4;
    public AlarmList testList;

    @BeforeEach
    public void makeList() {
        DaysList dofWeek = new DaysList();
        try {
            dofWeek.addDay(Monday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail();
        }
        test = new Alarm("t1", 9, 30, dofWeek);
        test2 = new Alarm("e2", 9, 35, dofWeek);
        test3 = new Alarm("a3", 10, 30, dofWeek);
        test4 = new Alarm("v4", 1, 0, dofWeek);
        testList = new AlarmList("testList");
        testList.addAlarm(test);
        testList.addAlarm(test2);
        testList.addAlarm(test3);
        testList.addAlarm(test4);
    }

    @Test
    public void testAlphabeticalSort() {
        try {
            testList.alphabeticallySorter();
        } catch (EmptyList emptyList) {
            fail("expected a non empty list");
        }
        ArrayList<Alarm> testArray = testList.getAlarms();
        assertEquals(testArray.get(0), test3);
        assertEquals(testArray.get(1), test2);
        assertEquals(testArray.get(2), test);
        assertEquals(testArray.get(3), test4);
    }

    @Test
    public void testAlphabeticalSortEmpty() {
        AlarmList testList2 = new AlarmList("testList2");
        try {
            testList2.alphabeticallySorter();
            fail("Expected EmptyList Exception");
        } catch (EmptyList emptyList) {
            //expected
        }
    }

    @Test
    public void testSortTimeEmptyList() {
        AlarmList testList2 = new AlarmList("testList2");
        ArrayList<Alarm> t = null;
        try {
            testList2.sortAlarmsByTime();
            fail("expected an empty list exception");
        } catch (EmptyList emptyList) {
            //expected
        }
    }

    @Test
    public void testSorterTime() {
        ArrayList<Alarm> t = null;
        try {
            testList.sortAlarmsByTime();
        } catch (EmptyList emptyList) {
            fail("Caught an EmptyList expected a non empty list");
        }
        System.out.println(testList);
        ArrayList<Alarm> testArray = testList.getAlarms();
        assertEquals(testArray.get(0), test4);
        assertEquals(testArray.get(1), test);
        assertEquals(testArray.get(2), test2);
        assertEquals(testArray.get(3), test3);
    }

    @Test
    public void testSorter2Time() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        ArrayList<Alarm> t = null;
        try {
            testList2.sortAlarmsByTime();
        } catch (EmptyList emptyList) {
            fail("Expected a non empty list");
        }
        t = testList2.getAlarms();
        assertEquals(t.get(0), test);
    }

    @Test
    public void testViewAlarmsEmpty() {
        AlarmList testList2 = new AlarmList("testList2");
        ArrayList<Alarm> t = null;
        try {
            testList2.viewAlarm("test");
            fail("Expected an empty list exception");
        } catch (EmptyList emptyList) {
            //expected
        }

    }

    @Test
    public void testViewAlarms() {
        try {
            testList.viewAlarm("t4");
        } catch (EmptyList emptyList) {
            fail("Expected the alarm called t4");
        }
    }

    @Test
    public void testShowAlarms() {
        String a = "t1 9:30 [Monday], e2 9:35 [Monday], a3 10:30 [Monday], v4 1:0 [Monday]";
        assertEquals(a, testList.showAlarms());
    }

    @Test
    public void testShowNoAlarms() {
        AlarmList testList2 = new AlarmList("testList2");
        assertEquals(testList2.showAlarms(), "There Are No Alarms to Show");
    }

    @Test
    public void testShowOneAlarm() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        assertEquals(testList2.showAlarms(), test.alarmToString());
    }

    @Test
    public void testSize() {
        assertEquals(testList.numAlarms(), 4);
    }

    @Test
    public void testSetName() {
        testList.setName("Unsuccessful");
        assertEquals("Unsuccessful", testList.getName());
    }
}
