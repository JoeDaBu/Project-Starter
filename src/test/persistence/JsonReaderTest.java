package persistence;

import model.Alarm;
import model.AlarmList;
import model.DaysList;
import model.exceptions.ItemAlreadyExists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static model.DaysOfTheWeek.Saturday;
import static model.DaysOfTheWeek.Sunday;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlarmList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlarmList.json");
        try {
            AlarmList alarmList = reader.read();
            assertEquals("My alarm list", alarmList.getName());
            assertEquals(0, alarmList.numAlarms());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlarmList() {
        DaysList weekend = new DaysList();
        try {
            weekend.addDay(Saturday);
            weekend.addDay(Sunday);
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }

        JsonReader reader = new JsonReader("./data/testReaderGeneralAlarmList.json");
        try {
            AlarmList alarmList = reader.read();
            assertEquals("My alarm list", alarmList.getName());
            ArrayList<Alarm> alarms = alarmList.getAlarms();
            assertEquals(2, alarms.size());
            checkAlarm("PM", 23, 30, weekend, alarms.get(0));
            checkAlarm("AM", 9, 20, new DaysList(), alarms.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}