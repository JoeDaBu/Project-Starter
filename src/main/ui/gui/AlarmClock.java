package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.*;

//The Class To Run the GUI App
public class AlarmClock extends JFrame implements Observer {
    private static final int HEIGHT = 356;
    private static final int WIDTH = 422;
    private final BaseClock baseClock;
    private final AlarmControllerPanelLabels controllerLabels;
    private final MenuBar menuBar;
    private final AlarmControllerPanelButtons buttons;
    private final ClockPanel clockPanel;
    private final AlarmController controller;
    private final Update update;
    public String name;

    //Effects: Initializes everything in GUI
    public AlarmClock() {
        clockPanel = new ClockPanel();
        controller = new AlarmController(name);
        buttons = new AlarmControllerPanelButtons(controller);
        menuBar = new MenuBar(controller, this);
        setJMenuBar(menuBar);
        baseClock = new BaseClock(clockPanel, buttons);
        add(baseClock);
        controllerLabels = new AlarmControllerPanelLabels();
        //add(controllerLabels);
        update = new Update(this, controllerLabels, controller, menuBar);
        name();
        setTitle(name);
        controller.setAlarmListName(name);
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

    //Modifies: this
    //Effects: sets name to a user input
    private void name() {
        String[] choices = {"New", "Load", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null,
                "Chose How To Begin",
                "Paths",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                -1);
        if (choice == 0) {
            name = getString();
        } else if (choice == 1) {
            menuBar.doLoad();
        } else {
            System.exit(0);
        }
    }

    //Effects: gets a name from the user
    private String getString() {
        String name = JOptionPane.showInputDialog(null,
                "Name Your New AlarmList",
                "A New Beginning",
                JOptionPane.PLAIN_MESSAGE);
        return name;
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
        add(controllerLabels);
        pack();
    }

    @Override
    public void updateView(String name) {
        add(controllerLabels);
        pack();
    }

    @Override
    public void updateChange(String oldName, Alarm oldAlarm, String newName, Alarm newAlarm) {
        add(controllerLabels);
        pack();
    }

    @Override
    public void updateShow() {
        Boolean show = AlarmControllerPanelLabels.getShow();
        if (show) {
            remove(controllerLabels);
            pack();
//        setSize(new Dimension(WIDTH,HEIGHT));
        } else {
            add(controllerLabels);
            pack();
        }
    }

    @Override
    public void updateImage() {
        pack();
    }

    @Override
    public void updateSort(AlarmList type) {
        pack();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void updateName(String name) {
        this.name = name;
        setTitle(name);
    }

    @Override
    public void updateRemoveAll() {
        remove(controllerLabels);
        pack();
    }

/*public void update(AlarmControllerPanelLabels alarmControllerPanelLabels) {
        add(alarmControllerPanelLabels);
    }*/
}
