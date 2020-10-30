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
    public Alarm test5;
    public Alarm test6;
    public Alarm test7;
    public Alarm test8;
    public Alarm test9;
    public Alarm test10;
    public Alarm test11;
    public Alarm test12;
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
        test5 = new Alarm("jo", 23, 59, dofWeek);
        test6 = new Alarm("g", 23, 59, dofWeek);
        test7 = new Alarm("ga", 13, 59, dofWeek);
        test8 = new Alarm("gan", 0, 0, dofWeek);
        test9 = new Alarm("gand", 0, 0, dofWeek);
        test10 = new Alarm("gando", 0, 0, dofWeek);
        test11 = new Alarm("gandol", 9, 31, dofWeek);
        test12 = new Alarm("gandolf", 9, 36, dofWeek);
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
            testList.addAlarm(test5);
            testList.addAlarm(test6);
            testList.addAlarm(test7);
            testList.addAlarm(test8);
            testList.addAlarm(test9);
            testList.addAlarm(test10);
            testList.addAlarm(test11);
            testList.addAlarm(test12);
            testList.sortAlarmsByTime();
        } catch (EmptyList emptyList) {
            fail("Caught an EmptyList expected a non empty list");
        }
        System.out.println(testList);
        ArrayList<Alarm> testArray = testList.getAlarms();
        assertEquals(testArray.get(0), test10);
        assertEquals(testArray.get(1), test9);
        assertEquals(testArray.get(2), test8);
        assertEquals(testArray.get(3), test4);
        assertEquals(testArray.get(4), test);
        assertEquals(testArray.get(5), test11);
        assertEquals(testArray.get(6), test2);
        assertEquals(testArray.get(7), test12);
        assertEquals(testArray.get(8), test3);
        assertEquals(testArray.get(9), test7);
        assertEquals(testArray.get(10), test6);
        assertEquals(testArray.get(11), test5);
    }

    @Test
    public void testSorterTimeHard() {
        ArrayList<Alarm> t = null;
        try {
            testList.addAlarm(test5);
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
