package model.unused;

import java.util.ArrayList;
//a class saving settingLists

public class SavedSettings {
    private final ArrayList<SettingsList> settings;

    //Effects: Creates a new empty list of settings
    public SavedSettings() {
        settings = new ArrayList<>();
    }

    /*Requires: a to have a unique name
    Modifies: this
    Effects: add the settingsList s to the list settings*/
    public void addSettings(SettingsList s) {
        settings.add(s);
    }

    /*
    Modifies:this
    Effects: removes the settingsList called name from the list of settings,
    and if an alarm was removed, returns the removed settingsList, otherwise
    returns null*/
    public SettingsList removeSettings(String name) {
        for (SettingsList s : settings) {
            if (s.getSettingsName().equals(name)) {
                settings.remove(s);
                return s;
            }
        }
        return null;
    }

    //Effects: Returns the list of settings, settings
    public ArrayList<SettingsList> getSettings() {
        return settings;
    }

    //Requires: settingsList to be different from all other settingsLists in the
    //settings list
    //Modifies: this
    //Effects: replaces settingsList with the settingsList named, name, and
    //returns the removes settingsList, if an alarm was removed, , otherwise
    //returns null
    public SettingsList changeSettings(String name, SettingsList settingsList) {
        for (int i = 0; i < settings.size(); i++) {
            if (settings.get(i).getSettingsName().equals(name)) {
                return settings.set(i, settingsList);
            }
        }
        return null;
    }

    //Effects: Returns the settingList called name from the list of alarms
    public SettingsList viewSettings(String name) {
        for (SettingsList s : settings) {
            if (s.getSettingsName().equals(name)) {
                return s;
            }
        }
        return null;
    }
}
