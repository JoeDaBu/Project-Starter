package ui.gui;

import javax.swing.*;
import java.awt.*;

public class AlarmClock extends JFrame {
    private ClockPanel clockPanel;
    private AlarmControllerPanel controller;

    public AlarmClock() {
        controller = new AlarmControllerPanel();
        setTitle("Alarm Clock");//Sets time of gui
        setDefaultCloseOperation(EXIT_ON_CLOSE);//action of the x button/change later
        setResizable(true);//prevents the frame from changing in size
        setSize(405, 520);//sets the dimension of the frame
        setLayout(null);
        setVisible(true);//sets frame to visible
        clockPanel = new ClockPanel();
        add(clockPanel);
//        ImageIcon image = new ImageIcon("geometric-cool-elephant-wall-clocks.png");//Creates an image icon
//        setIconImage(image.getImage());//Change icon of frame
//        JLabel label = new JLabel();
//        label.setIcon(image);
//        add(label);
        add(controller);
        getContentPane().setBackground(new Color(255, 0, 0));//changes color of background
        System.out.println("Working");
    }
}
