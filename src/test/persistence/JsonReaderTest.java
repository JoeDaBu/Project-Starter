package persistence;

import model.Alarm;
import model.AlarmList;
import model.AlarmList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AlarmList alarmList = reader.read();
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
        ArrayList<String> weekend = new ArrayList<>();
        weekend.add("Saturday");
        weekend.add("Sunday");
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlarmList.json");
        try {
            AlarmList alarmList = reader.read();
            assertEquals("My alarm list", alarmList.getName());
            ArrayList<Alarm> alarms = alarmList.getAlarms();
            assertEquals(2, alarms.size());
            checkAlarm("PM", 23, 30, weekend, alarms.get(0));
            checkAlarm("AM", 9, 20, new ArrayList<String>(), alarms.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}