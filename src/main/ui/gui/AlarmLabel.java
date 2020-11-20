package ui.gui;

import model.Alarm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Class that sets up all alarm labels
public class AlarmLabel extends JLabel {
    private final String name;

    //Effects: sets up and initializes an alarm label
    public AlarmLabel(String name, Alarm alarm) {
        this.name = name;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        setText(alarm.alarmBodyToString() + alarm.daysToStringAbbr());
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        setFont(new Font("Times New Roman", Font.PLAIN, 20).deriveFont(fontAttributes));
        //imageio.read(path)
        //./data/name
        setForeground(new Color(255, 0, 0));
        setOpaque(false);
        setBorder(border);
        setVerticalAlignment(JLabel.TOP);
    }

    public String getName() {
        return name;
    }
}
