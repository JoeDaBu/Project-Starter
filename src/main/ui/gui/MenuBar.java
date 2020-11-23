package ui.gui;

import model.Alarm;
import model.AlarmList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

//The menu-bar of the GUI
public class MenuBar extends JMenuBar implements ActionListener {
    private static String JSON_STORE;
    private final AlarmController controller;
    JMenu menu;
    JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Update update;
    private String loadedName;

    //Effects: initializes and sets up the menu bar
    public MenuBar(AlarmController controller) {
        this.controller = controller;
        menu = new JMenu("File");
        menu.setMnemonic('F');
        menu.setMnemonic(KeyEvent.VK_F);

        menuItem1 = new JMenuItem("Save");
        menuItem1.setMnemonic('S');
        menuItem1.addActionListener(this);

        menuItem2 = new JMenuItem("Load");
        menuItem2.setMnemonic('L');
        menuItem2.addActionListener(this);

        menuItem3 = new JMenuItem("Quit");
        menuItem3.setMnemonic('Q');

        addAdd();
        add(menu);
    }

    //Modifies: this
    //Effects: adds all items to a menu of the menu bar and gives them a key shortcut
    private void addAdd() {
        int h = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        menuItem1.setAccelerator(KeyStroke.getKeyStroke('S', h));
        menuItem2.setAccelerator(KeyStroke.getKeyStroke('L', h));
//        menuItem3.setAccelerator(KeyStroke.getKeyStroke('Q', h));

        menu.add(menuItem1);
        menu.add(menuItem2);
//        menu.add(menuItem3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItem1) {
            System.out.println("Save");
            doSave();
        } else if (e.getSource() == menuItem2) {
            System.out.println("Load");
            doLoad();
        }
//        } else if (e.getSource() == menuItem3) {
//            System.out.println("Quit");
//            doQuit();
//        }
    }

    //Modifies: this
    //Effects: saves the alarmList of the GUI
    private void doSave() {
        JSON_STORE = persistenceLocation("Save As:", "Save");
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(controller.alarmListGUI);
            jsonWriter.close();
            System.out.println("Saved " + controller.alarmListGUI.getName() + " to " + JSON_STORE);
            saved();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save file to:\n\t" + JSON_STORE);
            unsaved();
        }
    }

    //Effects:unsaved message popup
    private void unsaved() {
        JOptionPane.showMessageDialog(null,
                "Unable to save file to:\n\t" + JSON_STORE,
                "UnSave-Able",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //Effects: saved message popup
    private void saved() {
        JOptionPane.showMessageDialog(null,
                "Saved " + controller.alarmListGUI.getName() + " to " + JSON_STORE,
                "Saved",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //Modifies: this
    //Effects: loads the alarmList of the Gui
    public void doLoad() {
        JSON_STORE = persistenceLocation("Load From:", "Load");
        jsonReader = new JsonReader(JSON_STORE);
        update.updateRemoveAll();
        try {
            AlarmList loaded = jsonReader.read();
            controller.alarmListGUI = loaded;
            loadedName = loaded.getName();
            System.out.println("Loaded " + controller.alarmListGUI.getName() + " from " + JSON_STORE);
            loaded();
        } catch (IOException e) {
            System.out.println("Unable to load file from:\n\t" + JSON_STORE);
            unloaded();
        }
        update.updateName(loadedName);
        for (Alarm a : controller.alarmListGUI.getAlarms()) {
            controller.getUpdate().updateAdd(a.getAlarmName(), a);
        }
    }

    //Effects: unloaded popup
    private void unloaded() {
        JOptionPane.showMessageDialog(null,
                "Unable to load file from:\n\t" + JSON_STORE,
                "Unloaded",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //Effects: load file popup
    private void loaded() {
        JOptionPane.showMessageDialog(null,
                "Loaded " + controller.alarmListGUI.getName() + " from " + JSON_STORE,
                "Loaded",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //Modifies: this
    //Effects: sets update
    public void setUpdate(Update update) {
        this.update = update;
    }

    //Effects: gets the location of where persistence will be used
    public String persistenceLocation(String persistence, String type) {
        String name = JOptionPane.showInputDialog(null,
                persistence,
                type, JOptionPane.QUESTION_MESSAGE);
        String location = "./data/" + name + ".json";
        return location;
    }

//    public void doQuit() {
//        System.exit(2);
//    }

/*    @Override
    public void updateAdd(String name, Alarm alarm) {
        mirror.addAlarm(alarm);

    }

    @Override
    public void updateRemove(String name, Alarm alarm) {
        mirror.removeAlarm(name);
    }

    @Override
    public void updateView(String name) {
    }

    @Override
    public void updateChange(String oldName, Alarm oldAlarm, String newName, Alarm newAlarm) {
        mirror.changeAlarm(oldName, newAlarm);
    }

    @Override
    public void updateShow() {
    }

    @Override
    public void updateSort(AlarmList alarmList) {
        mirror = alarmList;
    }*/
}
