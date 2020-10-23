package persistence;

import model.Alarm;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAlarm(String name, Integer h, Integer m, ArrayList<String> dofWeek, Alarm alarm) {
        assertEquals(name, alarm.getAlarmName());
        assertEquals(h, alarm.getHours());
        assertEquals(m, alarm.getMinutes());
        for (int i = 0; i < dofWeek.size(); i++) {
            assertEquals(dofWeek.get(i), alarm.getDaysOfTheWeek().get(i));
        }
    }
}
