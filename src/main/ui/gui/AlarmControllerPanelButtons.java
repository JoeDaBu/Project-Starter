package ui.gui;

import javax.swing.*;
import java.awt.*;

//The panel containing all the buttons
public class AlarmControllerPanelButtons extends JPanel {
    AlarmController controller;

    //Modifies: this
    //Effects: initializes and sets up the panel
    AlarmControllerPanelButtons(AlarmController controller) {
//        String a = alarmList.getName();
        this.controller = controller;
        setBackground(new Color(0, 0, 0));
        setBounds(5, 150, 387, 100);
        //controller.addAlarm.addActionListener(this);
        setButtons();
        setVisible(true);
    }

    //Effects: adds all buttons to panel
    private void setButtons() {
        setLayout(new GridLayout(3, 1));
        add(controller.addAlarm);
        add(controller.removeAlarm);
        add(controller.changeAlarm);
        add(controller.viewAlarm);
        add(controller.showAlarms);
        add(controller.sort);
        add(controller.changeName);
        add(controller.imageToggle);
    }

    public AlarmController getController() {
        return controller;
    }

}
