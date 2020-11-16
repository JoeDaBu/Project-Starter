package ui;

import ui.gui.AlarmClock;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Update update = new Update();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlarmClock();
            }
        });
        //AlarmClock alarmClock = new AlarmClock();
        //new ConsoleApp();
        //TestFrame testFrame = new TestFrame();
    }
}
