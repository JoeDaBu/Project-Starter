package ui.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//The counter of time panel
public class ClockPanel extends JPanel {

    private Timer timer;
    private SimpleDateFormat timeFormat;
    private JLabel timeLabel;
    private String time;
    private SimpleDateFormat dayFormat;
    private JLabel dayLabel;
    private String day;
    private SimpleDateFormat dateFormat;
    private JLabel dateLabel;
    private String date;
    //private  JLabel image;

    //Effects: Creates a clock
    public ClockPanel() {
        Border border = BorderFactory.createLineBorder(Color.green, 5); //creates a border
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        setLabels();
        setVisible(true);
        timer = new Timer();
        setBackground(new Color(0,0,0));
        setBorder(border);
        setBounds(0,0, 390, 170);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tickTock();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    //Modifies: This
    //Effects: Prepares the labels for a given time
    public void setLabels() {
        setTimeLabel();
        setDayLabel();
        setDateLabel();
        //setImage();
    }

    //Modifies: This
    //Effects: Prepares the date label to receive a time
    private void setDateLabel() {
        dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        dateLabel.setBackground(Color.black);
        dateLabel.setForeground(new Color(0xAB00FF));
        dateLabel.setOpaque(true);
        add(dateLabel);
    }

    //Modifies: This
    //Effects: Prepares the day label to receive a time
    private void setDayLabel() {
        dayFormat = new SimpleDateFormat("EEEE");
        dayLabel = new JLabel();
        dayLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 40));
        dayLabel.setForeground(new Color(0x00FFFF));
        dayLabel.setBackground(Color.black);
        dayLabel.setOpaque(true);
        add(dayLabel);
    }

    //Modifies: This
    //Effects: Prepares the time label to receive a time
    private void setTimeLabel() {
        timeFormat = new SimpleDateFormat("hh:mm:ss a"); //sets how to showcase time
        timeLabel = new JLabel();//initializes the label
        timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));//declares font to use
        timeLabel.setForeground(new Color(0xFFFF0000, true));//sets foreground color
        timeLabel.setBackground(Color.black);//sets background color
        timeLabel.setOpaque(true);//displays background color
        add(timeLabel);
    }

    //Modifies: This
    //Effects: Gets the time and sets labels
    public void tickTock() {
        Calendar t2 = Calendar.getInstance();//gets current time
        t2.add(Calendar.MILLISECOND, 550);//adds time to time retrieved
        Date t = t2.getTime();//set t to time gotten
        time = timeFormat.format(t);//formats the time for the timeLabel
        timeLabel.setText(time);//sets the time for the time label
        day = dayFormat.format(t);
        dayLabel.setText(day);
        date = dateFormat.format(t);
        dateLabel.setText(date);

/*public void setImage() {
        ImageIcon icon = new ImageIcon("geometric-cool-elephant-wall-clocks.jpg");
        image = new JLabel();
        image.setIcon(icon);
        add(image);
    }
*/
    }
}
