package persistence;

import org.json.JSONObject;

//Converts objects to json
public interface Writable {
    //Effects: returns object called as a JSON object
    JSONObject toJson();
}
