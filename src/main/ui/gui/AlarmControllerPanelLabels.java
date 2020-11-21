package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//Class of all alarm labels
public abstract class AlarmControllerPanelLabels extends JPanel implements Observer {
    protected static final int WIDTH = 390;
    protected static int HEIGHT;
    protected static int rows = 0;
    protected static Boolean show;
    protected static Boolean view;
    protected ArrayList<AlarmLabel> labels;

    //Effects: initializes and sets up the panel
    public AlarmControllerPanelLabels() {
        labels = new ArrayList<>();
        show = true;
        view = true;
        setLayout();
//        setBackground(Color.lightGray);
        setMinimumSize(new Dimension(0, 0));
        //setBounds(0,0, WIDTH,HEIGHT);
        //setPreferredSize();

        setVisible(true);
    }

    public static Boolean getShow() {
        return show;
    }

    //Effects:sets the preferred size of the panel
    protected void setPreferredSize() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    //Effects: sets the layout of the panel
    protected void setLayout() {
        setLayout(new GridLayout(rows, 1));
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(bgImage, 40, 5, null);
//    }

    @Override
    public void updateAdd(String name, Alarm alarm) {
        HEIGHT += 20;
        rows++;
        AlarmLabel alarmLabel = new AlarmLabel(name, alarm);
        labels.add(alarmLabel);
        add(alarmLabel);
        changeRender();

    }


    @Override
    public void updateRemove(String name, Alarm alarm) {
        AlarmLabel toRemove = null;
        rows--;
        for (AlarmLabel alarmLabel : labels) {
            if (alarmLabel.getName().equals(name)) {
                toRemove = alarmLabel;
            }
        }
        remove(toRemove);
        labels.remove(toRemove);
        changeRender();
    }

    @Override
    public void updateView(String name) {
        for (AlarmLabel alarmLabel : labels) {
            if (alarmLabel.getName().equals(name)) {
                if (view) {
                    alarmLabel.setOpaque(false);
                    alarmLabel.setVisible(false);
                    view = false;
                } else {
                    alarmLabel.setOpaque(true);
                    alarmLabel.setVisible(true);
                    view = true;
                    changeRender();
                }
            }
        }
    }

    //Effects: renders any changes made
    protected void changeRender() {
        setLayout();
        setPreferredSize();
        setVisible(true);
        revalidate();
        repaint();
    }

    @Override
    public void updateChange(String oldName, Alarm oldAlarm, String newName, Alarm newAlarm) {
        int m = 0;
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getName().equals(oldName)) {
                remove(labels.get(i));
                m = i;
            }
        }
        AlarmLabel alarmLabel = new AlarmLabel(newName, newAlarm);
        labels.add(m, alarmLabel);
        add(alarmLabel, m);
        changeRender();
    }

    @Override
    public void updateShow() {
        if (show) {
            setVisible(false);
            show = false;
        } else {
            show = true;
            changeRender();
        }
    }

    @Override
    public void updateSort(AlarmList alarmList) {
        for (AlarmLabel alarmLabel : labels) {
            remove(alarmLabel);
        }
        labels = new ArrayList<>();
        for (Alarm a : alarmList.getAlarms()) {
            AlarmLabel alarmLabel = new AlarmLabel(a.getAlarmName(), a);
            labels.add(alarmLabel);
            add(alarmLabel);
        }
        changeRender();
    }

    @Override
    public void updateName(String name) {
    }

    @Override
    public void updateRemoveAll() {
        HEIGHT = 0;
        rows = 0;
        removeAll();
        labels = new ArrayList<>();
        setVisible(false);
    }
}
