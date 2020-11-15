package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.*;

public class AlarmControllerPanelLabels extends JPanel {
    private static int HEIGHT;

    public void update(AlarmList alarmList) {
        for (Alarm a : alarmList.getAlarms()) {
            JLabel alarm = new JLabel();
            alarm.setFont(new Font("Times New Roman", Font.PLAIN, 10));
            alarm.setForeground(Color.BLACK);
            alarm.setBackground(Color.lightGray);
            alarm.setText(a.alarmToString());
            alarm.setOpaque(true);
            add(alarm);
            setBackground(new Color(255,0,0));
            setBounds(0,370, 390,300);
            setVisible(true);
        }
    }
}
