package persistence;

import model.Alarm;
import model.AlarmList;
import model.DaysList;
import model.DaysOfTheWeek;
import model.exceptions.ItemAlreadyExists;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//reads the Alarm List from the file storing JSON data
public class JsonReader {
    private final String source;

    //Effects: constructs a reader to read the source file
    public JsonReader(String source) {
        this.source = source;
    }

    //Effects: reads alarmlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AlarmList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlarmList(jsonObject);
    }

    //Effects: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //Effects: analyzes and breaks down alarmlist from JSON object and returns it
    private AlarmList parseAlarmList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AlarmList al = new AlarmList(name);
        addAlarms(al, jsonObject);
        return al;
    }

    //Modifies: al
    //Effects: analyzes and breaks down alarms from JSON object and adds them
    //to the alarmList
    private void addAlarms(AlarmList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("alarms");
        for (Object json : jsonArray) {
            JSONObject nextAlarm = (JSONObject) json;
            addAlarm(al, nextAlarm);
        }
    }

    //Modifies:al
    //Effects: analyzes and breaks down alarm from JSON and adds it to alarmList
    private void addAlarm(AlarmList al, JSONObject nextAlarm) {
        String name = nextAlarm.getString("name");
        Integer hour = nextAlarm.getInt("hour");
        Integer minutes = nextAlarm.getInt("minutes");
        DaysList dofWeek = new DaysList();
        JSONArray jsonArrayDofWeek = nextAlarm.getJSONArray("Days of the Week");
        for (Object json : jsonArrayDofWeek) {
            DaysOfTheWeek nextDay = DaysOfTheWeek.valueOf(json.toString());

            dofWeek.add(nextDay);
        }
        Alarm alarm = new Alarm(name, hour, minutes, dofWeek);
        al.addAlarm(alarm);
    }

}
