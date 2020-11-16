package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

//The foundation of the gui(sets all classes that are to be untouched)
public class BaseClock extends JInternalFrame implements Serializable {
    private static final int HEIGHT = 294;
    private static final int WIDTH = 406;
    ClockPanel clockPanel;
    AlarmControllerPanelButtons controllerButtons;

    //Effects: Initializes and sets up the base clock
    public BaseClock(ClockPanel clockPanel, AlarmControllerPanelButtons controllerButtons) {
        this.controllerButtons = controllerButtons;
        this.clockPanel = clockPanel;
        add(controllerButtons);
        add(clockPanel);
        setName("Control Panel");
        setTitle("Control Panel");
        setBounds(0, 0, WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setClosable(false);
        setMaximizable(false);
        setIconifiable(false);
        setVisible(true);
    }
}