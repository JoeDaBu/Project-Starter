package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AlarmAndListTest {
    public Alarm test;
    public AlarmList listTest;

    @BeforeEach
    public void createAlarmsAndLists() {
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Friday");
        test = new Alarm(8, 30, testArray, "alarmTest");
        listTest = new AlarmList();
    }

    @Test
    public void testChangeTime() {
        test.changeTime(9, 0);
        ArrayList<Integer> nt = new ArrayList<>();
        nt.add(9);
        nt.add(0);
        assertEquals(nt, test.getTime());
    }

    @Test
    public void testChangeName() {
        String newName = "changeName";
        test.changeAlarmName(newName);
        assertEquals(newName, test.getAlarmName());
    }

    @Test
    public void testChangeDaysOfTheWeek() {
        ArrayList<String> newWeek = new ArrayList<>();
        newWeek.add("Monday");
        newWeek.add("Sunday");
        test.changeDaysOfTheWeek(newWeek);
        assertEquals(newWeek, test.getDaysOfTheWeek());
    }

    @Test
    public void testAddAlarm() {
        listTest.addAlarm(test);
        assertEquals(listTest.getAlarms().get(0), test);
        assertEquals(listTest.getAlarms().size(), 1);
    }

    @Test
    public void testAddAlarm2() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        listTest.addAlarm(a);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.size(), 2);
        assertEquals(alarms.get(1), a);
    }

    @Test
    public void testRemoveAlarm() {
        listTest.addAlarm(test);
        assertEquals(listTest.removeAlarm("alarmTest"), test);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.size(), 0);
    }

    @Test
    public void testRemoveAlarm2() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        listTest.addAlarm(a);
        assertEquals(listTest.removeAlarm("a"), a);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.size(), 1);
        assertEquals(alarms.get(0), test);
    }

    @Test
    public void testRemoveAlarm3() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        listTest.addAlarm(a);
        assertEquals(listTest.removeAlarm("alarmTest"), test);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.size(), 1);
        assertEquals(alarms.get(0), a);
    }

    @Test
    public void testRemoveAlarm4() {
        listTest.addAlarm(test);
        assertNull(listTest.removeAlarm("x"));
    }

    @Test
    public void testChangeAlarm() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        listTest.addAlarm(a);
        ArrayList<String> dofWeek = new ArrayList<>();
        dofWeek.add("Thursday");
        dofWeek.add("Tuesday");
        Alarm newAlarm = new Alarm(5, 5, dofWeek, "newAlarm");
        assertEquals(listTest.changeAlarm("alarmTest", newAlarm), test);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.get(0).getAlarmName(), newAlarm.getAlarmName());
    }

    @Test
    public void testChangeAlarm2() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        listTest.addAlarm(a);
        ArrayList<String> dofWeek = new ArrayList<>();
        dofWeek.add("Thursday");
        dofWeek.add("Friday");
        Alarm newAlarm2 = new Alarm(2, 9, dofWeek, "newAlarm2");
        assertEquals(listTest.changeAlarm("a", newAlarm2), a);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.get(1).getAlarmName(), newAlarm2.getAlarmName());
    }

    @Test
    public void testChangeAlarm3() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        assertNull(listTest.changeAlarm("x", a));
    }

    @Test
    public void testViewAlarm() {
        listTest.addAlarm(test);
        assertNull(listTest.viewer("x"));
    }

    @Test
    public void testViewAlarmListWithTheAlarm() {
        listTest.addAlarm(test);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("Wednesday");
        Alarm a = new Alarm(7, 50, testArray, "a");
        listTest.addAlarm(a);
        assertEquals(listTest.viewer("a"), a);
    }

    @Test
    public void testAlarmToString() {
        String a = "alarmTest 8:30 [Friday]";
        assertEquals(test.alarmToString(), a);
    }
}