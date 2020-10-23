package persistence;

import model.AlarmList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Writes the JSON representation of AlarmList to file
public class JsonWriter {
    public static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    //Effects: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //Modifies: This
    //Effects: opens writer; if destination file cannot be opened
    //for writing throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //Modifies: This
    //Effects: writes ALarmList to file as a JSON representation
    public void write(AlarmList a) {
        JSONObject json = a.toJson();
        saveToFile(json.toString(TAB));
    }

    //Modifies: This
    //Effects: closes the writer
    public void close() {
        writer.close();
    }

    //Modifies: this
    //Effects: saves string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
