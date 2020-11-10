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

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            AlarmList al = new AlarmList("My alarm list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAlarmList() {
        try {
            AlarmList al = new AlarmList("My alarm list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlarmList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlarmList.json");
            al = reader.read();
            assertEquals("My alarm list", al.getName());
            assertEquals(0, al.numAlarms());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAlarmList() {
        try {
            AlarmList al = new AlarmList("My alarm list");
            al.addAlarm(new Alarm("AM", 9, 30, new DaysList()));
            DaysList weekend = new DaysList();
            weekend.addDay(Saturday);
            weekend.addDay(Sunday);
            al.addAlarm(new Alarm("PM", 23, 30, weekend));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlarmList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAlarmList.json");
            al = reader.read();
            assertEquals("My alarm list", al.getName());
            ArrayList<Alarm> alarms = al.getAlarms();
            assertEquals(2, alarms.size());
            checkAlarm("AM", 9, 30, new DaysList(), alarms.get(0));
            checkAlarm("PM", 23, 30, weekend, alarms.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (ItemAlreadyExists itemAlreadyExists) {
            fail("iae");
        }
    }
}