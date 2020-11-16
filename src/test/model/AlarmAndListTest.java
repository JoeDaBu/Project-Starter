package model;

import model.exceptions.ItemAlreadyExists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static model.DaysOfTheWeek.*;
import static org.junit.jupiter.api.Assertions.*;

class AlarmAndListTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    public Alarm test;
    public AlarmList listTest;
    AlarmTask task;

    @BeforeEach
    public void createAlarmsAndLists() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        task = new AlarmTask();
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Friday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("item already exists");
        }
        test = new Alarm("alarmTest", 8, 30, testArray);
        listTest = new AlarmList("listTest");
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
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
        DaysList newWeek = new DaysList();
        try {
            newWeek.addDay(Monday);
            newWeek.addDay(Sunday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("item already exist was thrown");
        }

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
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Wednesday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        Alarm a = new Alarm("a", 7, 50, testArray);
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
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Wednesday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        Alarm a = new Alarm("a", 7, 50, testArray);
        listTest.addAlarm(a);
        assertEquals(listTest.removeAlarm("a"), a);
        ArrayList<Alarm> alarms = listTest.getAlarms();
        assertEquals(alarms.size(), 1);
        assertEquals(alarms.get(0), test);
    }

    @Test
    public void testRemoveAlarm3() {
        listTest.addAlarm(test);
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Wednesday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        Alarm a = new Alarm("a", 7, 50, testArray);
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
        DaysList testArray = new DaysList();

        try {
            testArray.addDay(Wednesday);
            Alarm a = new Alarm("a", 7, 50, testArray);
            listTest.addAlarm(a);
            DaysList dofWeek = new DaysList();
            dofWeek.addDay(Tuesday);
            dofWeek.addDay(Thursday);
            Alarm newAlarm = new Alarm("newAlarm", 5, 5, dofWeek);
            assertEquals(listTest.changeAlarm("alarmTest", newAlarm), test);
            ArrayList<Alarm> alarms = listTest.getAlarms();
            assertEquals(alarms.get(0).getAlarmName(), newAlarm.getAlarmName());
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
    }

    @Test
    public void testChangeAlarm2() {
        listTest.addAlarm(test);
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Wednesday);
            Alarm a = new Alarm("a", 7, 50, testArray);
            listTest.addAlarm(a);
            DaysList dofWeek = new DaysList();
            dofWeek.addDay(Thursday);
            dofWeek.addDay(Friday);
            Alarm newAlarm2 = new Alarm("newAlarm2", 2, 9, dofWeek);
            assertEquals(listTest.changeAlarm("a", newAlarm2), a);
            ArrayList<Alarm> alarms = listTest.getAlarms();
            assertEquals(alarms.get(1).getAlarmName(), newAlarm2.getAlarmName());
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
    }

    @Test
    public void testChangeAlarm3() {
        listTest.addAlarm(test);
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Wednesday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        Alarm a = new Alarm("a", 7, 50, testArray);
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
        DaysList testArray = new DaysList();
        try {
            testArray.addDay(Wednesday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
        Alarm a = new Alarm("a", 7, 50, testArray);
        listTest.addAlarm(a);
        assertEquals(listTest.viewer("a"), a);
    }

    @Test
    public void testAlarmToString() {
        String a = "alarmTest 8:30 [Friday]";
        assertEquals(test.alarmToString(), a);
    }

    @Test
    public void testTimer() {
        Alarm a = new Alarm("Joe", 9, 30, null);
        assertEquals(a.getTimerList().size(), 7);
        a.cancelAlarmTask();
        assertEquals(a.getTimerList().size(), 0);
    }

    @Test
    public void testTimer2() {
        DaysList daysList = new DaysList();
        Alarm a = new Alarm("Joe", 9, 30, daysList);
        assertEquals(a.getTimerList().size(), 7);
        a.cancelAlarmTask();
        assertEquals(a.getTimerList().size(), 0);
    }

    @Test
    public void testTimer3() {
        DaysList daysList = new DaysList();
        daysList.add(Monday);
        daysList.add(Friday);
        daysList.add(Wednesday);
        Alarm a = new Alarm("Joe", 9, 30, daysList);
        assertEquals(a.getTimerList().size(), 3);
        a.cancelAlarmTask();
        assertEquals(a.getTimerList().size(), 0);
    }
}