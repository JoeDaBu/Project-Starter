package model;

import model.exceptions.EmptyList;
import model.exceptions.ItemAlreadyExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.DaysOfTheWeek.*;
import static org.junit.jupiter.api.Assertions.*;

public class AlarmAndAlarmListSortTest {
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
        DaysList dofWeek2 = new DaysList();
        DaysList dofWeek3 = new DaysList();
        dofWeek3.add(Friday);
        dofWeek3.add(Monday);
        DaysList dofWeek4 = new DaysList();
        dofWeek4.add(Sunday);
        DaysList dofWeek5 = new DaysList();
        dofWeek5.add(Thursday);
        DaysList dofWeek6 = new DaysList();
        dofWeek6.add(Wednesday);
        test = new Alarm("t1", 9, 30, dofWeek3);
        test2 = new Alarm("e2", 9, 35, dofWeek);
        test3 = new Alarm("a3", 10, 30, dofWeek3);
        test4 = new Alarm("v4", 1, 0, dofWeek4);
        test5 = new Alarm("jo", 23, 59, dofWeek5);
        test6 = new Alarm("g", 23, 59, dofWeek2);
        test7 = new Alarm("ga", 13, 59, dofWeek6);
        test8 = new Alarm("gan", 0, 0, dofWeek);
        test9 = new Alarm("gand", 0, 0, dofWeek4);
        test10 = new Alarm("gando", 0, 0, dofWeek3);
        test11 = new Alarm("gandol", 9, 31, dofWeek);
        test12 = new Alarm("gandolf", 9, 36, dofWeek2);
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
    public void testSortImminentEmpty() {
        AlarmList testList2 = new AlarmList("testList2");
        ArrayList<Alarm> t = null;
        try {
            testList2.sortByImminent();
            fail("expected an empty list exception");
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

    //Note: this test and the next 8 below it are time dependent tests.
    @Test
    public void testSorterImminentHard() {
        try {
            testList.addAlarm(test5);
            testList.addAlarm(test6);
            testList.addAlarm(test7);
            testList.addAlarm(test8);
            testList.addAlarm(test9);
            testList.addAlarm(test10);
            testList.addAlarm(test11);
            testList.addAlarm(test12);
            testList.sortByImminent();
        } catch (EmptyList emptyList) {
            fail("Caught an EmptyList expected a non empty list");
        }
        System.out.println(testList.getAlarms().get(0).alarmToString());
        ArrayList<Alarm> testArray = testList.getAlarms();
        assertEquals(testArray.get(0), test);
        assertEquals(testArray.get(1), test11);
        assertEquals(testArray.get(2), test2);
        assertEquals(testArray.get(3), test12);
        assertEquals(testArray.get(4), test3);
        assertEquals(testArray.get(5), test6);
        assertEquals(testArray.get(6), test7);
        assertEquals(testArray.get(7), test5);
        assertEquals(testArray.get(8), test10);
        assertEquals(testArray.get(9), test9);
        assertEquals(testArray.get(10), test4);
        assertEquals(testArray.get(11), test8);
    }

    @Test
    public void testSorterImminent() {
        try {
            testList.addAlarm(test5);
            testList.sortByImminent();
        } catch (EmptyList emptyList) {
            fail("Caught an EmptyList expected a non empty list");
        }
        ArrayList<Alarm> testArray = testList.getAlarms();
        assertEquals(testArray.get(0), test);
        assertEquals(testArray.get(1), test2);
        assertEquals(testArray.get(2), test3);
        assertEquals(testArray.get(3), test5);
    }

    @Test
    public void testGetNegative() {
        ArrayList<Integer> testList = test2.getNegative();
        assertEquals(testList.size(), 0);
        ArrayList<Integer> testList2 = test6.getNegative();
        assertEquals(testList2.size(), 0);
        ArrayList<Integer> testList3 = test.getNegative();
        assertEquals(testList3.size(), 0);
        ArrayList<Integer> testList4 = test10.getNegative();
        assertEquals(testList4.size(), -2);
    }

    @Test
    public void testGetNextOccurrence() {
        assertEquals(test2.getNextOccurrence(Monday), 16);
        assertEquals(test6.getNextOccurrence(Monday), 16);
        assertEquals(test8.getNextOccurrence(Monday), 23);
    }

    @Test
    public void testGetPositive() {
        ArrayList<Integer> testList = test2.getPositive();
        assertEquals(testList.size(), 0);
        ArrayList<Integer> testList2 = test6.getPositive();
        assertEquals(testList2.size(), 6);
        ArrayList<Integer> testList3 = test.getPositive();
        assertEquals(testList3.size(), 1);
        ArrayList<Integer> testList4 = test3.getPositive();
        assertEquals(testList4.size(), 1);
        ArrayList<Integer> testList5 = test4.getPositive();
        assertEquals(testList5.size(), 1);
        ArrayList<Integer> testList6 = test10.getPositive();
        assertEquals(testList6.size(), 1);

    }

    @Test
    public void testToday() {
        assertTrue(test2.today());
        assertTrue(test6.today());
        assertTrue(test.today());
    }

    @Test
    public void testCategorizeLists() {
        ArrayList<Integer> negative = new ArrayList<>();
        negative.add(-1);
        negative.add(-5);
        ArrayList<Integer> positive = new ArrayList<>();
        positive.add(6);
        positive.add(3);
        assertEquals(test6.categorizeLists(negative, positive), 3);
        ArrayList<Integer> negative2 = new ArrayList<>();
        negative.add(-4);
        negative.add(-6);
        assertEquals(test.categorizeLists(negative2, new ArrayList<>()), 3);
    }

    @Test
    public void testNextOccurrence() {
        assertEquals(test8.nextOccurrence(), test8.getToday());
        assertEquals(test10.nextOccurrence(), 5);
        assertEquals(test2.nextOccurrence(), 5);
    }

    @Test
    public void testGetToday() {
        assertEquals(test10.getToday(), 5);
        assertEquals(test6.getToday(), 5);
        assertEquals(test8.getToday(), 5);
        assertEquals(test9.today(), false);
        assertEquals(test.today(), false);
        assertEquals(test5.today(), true);
    }

    @Test
    public void testSorterTime() {
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
        try {
            testList.addAlarm(test5);
            testList.sortAlarmsByTime();
        } catch (EmptyList emptyList) {
            fail("Caught an EmptyList expected a non empty list");
        }
        ArrayList<Alarm> testArray = testList.getAlarms();
        assertEquals(testArray.get(0), test4);
        assertEquals(testArray.get(1), test);
        assertEquals(testArray.get(2), test2);
        assertEquals(testArray.get(3), test3);
    }

    @Test
    public void testSorter2Imminent() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        try {
            testList2.sortByImminent();
        } catch (EmptyList emptyList) {
            fail("Expected a non empty list");
        }
        assertEquals(testList2.getAlarms().get(0), test);
    }

    @Test
    public void testSorter2Time() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        try {
            testList2.sortAlarmsByTime();
        } catch (EmptyList emptyList) {
            fail("Expected a non empty list");
        }
        assertEquals(testList2.getAlarms().get(0), test);
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
        String a = "t1 9:30 [Monday, Friday], e2 9:35 [Monday], a3 10:30 [Monday, Friday], v4 1:0 [Sunday]";
        assertEquals(a, testList.showAlarms());
    }

    @Test
    public void testShowAlarmsGUI() {
        String a = "t1 9:30 [Monday, Friday], e2 9:35 [Monday]\n" +
                "a3 10:30 [Monday, Friday], v4 1:0 [Sunday]";
        assertEquals(a, testList.showAlarmsGui());
    }

    @Test
    public void testShowNoAlarms() {
        AlarmList testList2 = new AlarmList("testList2");
        assertEquals(testList2.showAlarms(), "There Are No Alarms to Show");
    }

    @Test
    public void testShowNoAlarmsGUI() {
        AlarmList testList2 = new AlarmList("testList2");
        assertEquals(testList2.showAlarmsGui(), "There Are No Alarms to Show");
    }

    @Test
    public void testShowOneAlarm() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        assertEquals(testList2.showAlarms(), test.alarmToString());
    }

    @Test
    public void testShowOneAlarmGUI() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        assertEquals(testList2.showAlarmsGui(), test.alarmToString());
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
