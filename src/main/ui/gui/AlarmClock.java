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
    private AlarmControllerPanelLabels controllerLabelsWithImage;
    private final AlarmControllerPanelLabels controllerLabelsWithoutImage;
    private final MenuBar menuBar;
    private final AlarmControllerPanelButtons buttons;
    private final ClockPanel clockPanel;
    private final AlarmController controller;
    private final Update update;
    private static Boolean hasImageShowing;
    public String name;

    //Modifies: AlarmController, baseClock, menuBar, AlarmControllerButtons,
    //Effects: Initializes everything in GUI
    public AlarmClock() {
        hasImageShowing = true;
        ImageIcon imageIcon = new ImageIcon("./data/geometric-cool-elephant-wall-clocks.jpg");
        setIconImage(imageIcon.getImage());
        clockPanel = new ClockPanel();
        controller = new AlarmController(name);
        buttons = new AlarmControllerPanelButtons(controller);
        menuBar = new MenuBar(controller);
        setJMenuBar(menuBar);
        baseClock = new BaseClock(clockPanel, buttons);
        add(baseClock);
        controllerLabelsWithoutImage = new LabelsWithoutImage();
        controllerLabelsWithImage = new LabelsWithImage();
        //add(controllerLabels);
        update = new Update(controller, menuBar);
        addingObservers();
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
    }

    //Modifies: Update
    //Effects: Adds Observers to update
    private void addingObservers() {
        update.addObservers(this);
        update.addObservers(controllerLabelsWithImage);
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
        if (hasImageShowing) {
            add(controllerLabelsWithImage);
            pack();
        } else {
            add(controllerLabelsWithoutImage);
            pack();
        }
    }

    @Override
    public void updateRemove(String name, Alarm alarm) {
    }

    @Override
    public void updateView(String name) {
    }

    @Override
    public void updateChange(String oldName, Alarm oldAlarm, String newName, Alarm newAlarm) {
    }

    @Override
    public void updateShow() {
        if (hasImageShowing) {
            if (controllerLabelsWithImage.getShow()) {
                remove(controllerLabelsWithImage);
            } else {
                add(controllerLabelsWithImage);
            }
            pack();
        } else {
            if (controllerLabelsWithoutImage.getShow()) {
                remove(controllerLabelsWithoutImage);
            } else {
                add(controllerLabelsWithoutImage);
            }
            pack();
        }
    }

    @Override
    public void updateImage() {
        if (hasImageShowing) {
            update.removeObserver(controllerLabelsWithImage);
            controllerLabelsWithImage.updateRemoveAll();
            update.addObservers(controllerLabelsWithoutImage);
            remove(controllerLabelsWithImage);
            pack();
            hasImageShowing = false;
//            controllerLabelsWithImage = new LabelsWithImage();
            for (Alarm a : controller.alarmListGUI.getAlarms()) {
                controller.getUpdate().updateAdd(a.getAlarmName(), a);
            }
            add(controllerLabelsWithoutImage);
        } else {
            update.removeObserver(controllerLabelsWithoutImage);
            controllerLabelsWithoutImage.updateRemoveAll();
            update.addObservers(controllerLabelsWithImage);
            remove(controllerLabelsWithoutImage);
            pack();
            hasImageShowing = true;
            for (Alarm a : controller.alarmListGUI.getAlarms()) {
                controller.getUpdate().updateAdd(a.getAlarmName(), a);
            }
            add(controllerLabelsWithImage);
        }
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
        remove(controllerLabelsWithImage);
        remove(controllerLabelsWithoutImage);
        pack();
    }

/*public void update(AlarmControllerPanelLabels alarmControllerPanelLabels) {
        add(alarmControllerPanelLabels);
    }*/
}
