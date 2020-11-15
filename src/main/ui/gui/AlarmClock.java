package ui.gui;

import model.AlarmList;

import javax.swing.*;
import java.awt.*;

public class AlarmClock extends JFrame {
    private ClockPanel clockPanel;
    private AlarmControllerPanelButtons controllerButtons;
    private AlarmControllerPanelLabels controllerLabels;
    private Update update;
    public AlarmList alarmList;

    private static final int HEIGHT = 309;
    private static final int WIDTH = 405;

    public AlarmClock() {
        alarmList = new AlarmList("My Alarm List");
        controllerButtons = new AlarmControllerPanelButtons(alarmList);
        update = new Update();
        controllerLabels = new AlarmControllerPanelLabels();
        setTitle("Alarm Clock");//Sets time of gui
        setDefaultCloseOperation(EXIT_ON_CLOSE);//action of the x button/change later
        setResizable(true);//prevents the frame from changing in size
        setSize(WIDTH, HEIGHT);//sets the dimension of the frame
        setLayout(null);
        setVisible(true);//sets frame to visible
        clockPanel = new ClockPanel();
        add(clockPanel);
//        ImageIcon image = new ImageIcon("geometric-cool-elephant-wall-clocks.png");//Creates an image icon
//        setIconImage(image.getImage());//Change icon of frame
//        JLabel label = new JLabel();
//        label.setIcon(image);
//        add(label);
        add(controllerButtons);
        getContentPane().setBackground(new Color(255, 255, 255));//changes color of background
        System.out.println("Working");
        //update();
//        pack();
        //new panel
        //pack()
    }

    //new static method

    public void update() {
        try {
            Thread.sleep(1000);
            setSize(WIDTH, HEIGHT + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*public void update(AlarmControllerPanelLabels alarmControllerPanelLabels) {
        add(alarmControllerPanelLabels);
    }*/
}
