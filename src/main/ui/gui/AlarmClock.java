package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.*;

public class AlarmClock extends JFrame implements Observer {
    private final BaseClock baseClock;
    private AlarmControllerPanelLabels controllerLabels;
    private MenuBar menuBar;
    private AlarmControllerPanelButtons buttons;
    private ClockPanel clockPanel;
    private AlarmController controller;
    private Update update;
    public AlarmList alarmList;

    private static final int HEIGHT = 356;
    private static final int WIDTH = 422;

    public AlarmClock() {
        alarmList = new AlarmList("My Alarm List");
        clockPanel = new ClockPanel();
        controller = new AlarmController();
        buttons = new AlarmControllerPanelButtons(controller);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        baseClock = new BaseClock(clockPanel, buttons);
        add(baseClock);
        controllerLabels = new AlarmControllerPanelLabels();
        //add(controllerLabels);
        update = new Update(this, controllerLabels, controller);
        setTitle("Alarm Clock");//Sets time of gui
        setDefaultCloseOperation(EXIT_ON_CLOSE);//action of the x button/change later
        setResizable(false);//prevents the frame from changing in size
        setLayout(new GridLayout());//or null
        setSize(WIDTH, HEIGHT);//sets the dimension of the frame
        setVisible(true);//sets frame to visible
//        ImageIcon image = new ImageIcon("geometric-cool-elephant-wall-clocks.png");//Creates an image icon
//        setIconImage(image.getImage());//Change icon of frame
//        JLabel label = new JLabel();
//        label.setIcon(image);
//        add(label);
        pack();//or use set sizes
        getContentPane().setBackground(new Color(255, 255, 255));//changes color of background
        //update();
//        pack();
        //new panel
        //pack()
    }

    //new static method

//    public void updateAdd() {
//        try {
//            Thread.sleep(1000);
//            setSize(WIDTH, HEIGHT + 100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void updateAdd(String name, Alarm alarm) {
        add(controllerLabels);
        pack();
    }

    @Override
    public void updateRemove(String name, Alarm alarm) {

    }

    /*public void update(AlarmControllerPanelLabels alarmControllerPanelLabels) {
        add(alarmControllerPanelLabels);
    }*/
}
