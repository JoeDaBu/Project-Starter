package ui.gui;

import model.Alarm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

//Class that sets up all alarm labels
public class AlarmLabel extends JLabel {
    private final String name;

    //Effects: sets up and initializes an alarm label
    public AlarmLabel(String name, Alarm alarm) {
        this.name = name;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        setText(alarm.alarmToString());
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        setFont(new Font("Times New Roman", Font.PLAIN, 20).deriveFont(fontAttributes));
        setForeground(Color.BLACK);
        setBackground(Color.lightGray);
        setOpaque(true);
        setBorder(border);
        setVerticalAlignment(JLabel.TOP);
    }

    public String getName() {
        return name;
    }
}
