package ui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Clock extends JFrame {

    private SimpleDateFormat timeFormat;
    private JLabel timeLabel;
    private String time;
    private SimpleDateFormat dayFormat;
    private JLabel dayLabel;
    private String day;
    private SimpleDateFormat dateFormat;
    private JLabel dateLabel;
    private String date;

    public Clock() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("The Clock");
        setLayout(new FlowLayout());
        setSize(500, 500);
        setResizable(true);
        setLabels();
        setVisible(true);
        tickTock();
    }

    public void setLabels() {
        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        timeLabel.setForeground(new Color(0xFF6100));
        timeLabel.setBackground(Color.black);
        timeLabel.setOpaque(true);
        add(timeLabel);
        dayFormat = new SimpleDateFormat("EEEE");
        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
        dayLabel.setForeground(new Color(0x9999));
        dayLabel.setBackground(Color.black);
        dayLabel.setOpaque(true);
        add(dayLabel);
        dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Comic Sans MC", Font.PLAIN, 30));
        dateLabel.setBackground(Color.black);
        dateLabel.setForeground(new Color(0x640096));
        dateLabel.setOpaque(true);
        add(dateLabel);
    }

    public void tickTock() {
        while (true) {
            Date t = Calendar.getInstance().getTime();
            time = timeFormat.format(t);
            timeLabel.setText(time);
            day = dayFormat.format(t);
            dayLabel.setText(day);
            date = dateFormat.format(t);
            dateLabel.setText(date);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
