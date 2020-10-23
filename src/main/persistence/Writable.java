package persistence;

import org.json.JSONObject;

public interface Writable {
    //Effects: returns object called as a JSON object
    JSONObject toJson();
}
