package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class BaseClock extends JInternalFrame implements Serializable {
    ClockPanel clockPanel;
    AlarmControllerPanelButtons controllerButtons;
    private static final int HEIGHT = 294;
    private static final int WIDTH = 406;

    public BaseClock(ClockPanel clockPanel, AlarmControllerPanelButtons controllerButtons) {
        this.controllerButtons = controllerButtons;
        this.clockPanel = clockPanel;
        add(controllerButtons);
        add(clockPanel);
        setName("Control Panel");
        setTitle("Control Panel");
        setBounds(0,0, WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setResizable(false);
        setClosable(false);
        setMaximizable(false);
        setIconifiable(false);
        setVisible(true);
    }
}