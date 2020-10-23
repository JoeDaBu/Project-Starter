package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlarmListSortTest {
    public Alarm test;
    public Alarm test2;
    public Alarm test3;
    public Alarm test4;
    public AlarmList testList;

    @BeforeEach
    public void makeList() {
        ArrayList<String> dofWeek = new ArrayList<>();
        dofWeek.add("Monday");
        test = new Alarm("t", 9, 30, dofWeek);
        test2 = new Alarm("t", 9, 35, dofWeek);
        test3 = new Alarm("t", 10, 30, dofWeek);
        test4 = new Alarm("t", 1, 0, dofWeek);
        testList = new AlarmList("testList");
        testList.addAlarm(test);
        testList.addAlarm(test2);
        testList.addAlarm(test3);
        testList.addAlarm(test4);
    }

    //Note sorter doesn't work for empty lists
    @Test
    public void testSorter() {
        ArrayList<Alarm> t = testList.timeSorter();
        System.out.println(testList);
        assertEquals(t.get(0), test4);
        assertEquals(t.get(1), test);
        assertEquals(t.get(2), test2);
        assertEquals(t.get(3), test3);
    }

    @Test
    public void testSorter2() {
        AlarmList testList2 = new AlarmList("testList2");
        testList2.addAlarm(test);
        ArrayList<Alarm> t = testList2.timeSorter();
        assertEquals(t.get(0), test);
    }

    @Test
    public void testShowAlarms() {
        String a = "t 9:30 [Monday], t 9:35 [Monday], t 10:30 [Monday], t 1:0 [Monday]";
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
}
