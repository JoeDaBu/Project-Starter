package persistence;

import model.Alarm;
import model.DaysList;


import java.util.ArrayList;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAlarm(String name, Integer h, Integer m, DaysList dofWeek, Alarm alarm) {
        assertEquals(name, alarm.getAlarmName());
        assertEquals(h, alarm.getHours());
        assertEquals(m, alarm.getMinutes());
        for (int i = 0; i < dofWeek.size(); i++) {
            assertEquals(dofWeek.get(i), alarm.getDaysOfTheWeek().get(i));
        }
    }
}
