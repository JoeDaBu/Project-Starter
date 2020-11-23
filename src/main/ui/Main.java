package ui;

import ui.gui.AlarmClock;

import javax.swing.*;

//The main class that runs the gui and ui
public class Main {

    //Effects: runs the ui or gui
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlarmClock();
            }
        });
        //AlarmClock alarmClock = new AlarmClock();
        //new ConsoleApp();
    }
}
