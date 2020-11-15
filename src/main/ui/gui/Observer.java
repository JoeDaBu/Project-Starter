package ui.gui;

import model.Alarm;

public interface Observer {
    void updateAdd(String name, Alarm alarm);
    void updateRemove(String name, Alarm alarm);
}
