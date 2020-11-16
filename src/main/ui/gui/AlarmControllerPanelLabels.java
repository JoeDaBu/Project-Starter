package ui.gui;

import model.Alarm;
import model.AlarmList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Class of all alarm labels
public class AlarmControllerPanelLabels extends JPanel implements Observer {
    private static final int WIDTH = 390;
    private static int HEIGHT;
    private static int rows = 0;
    private static Boolean show;
    private static Boolean view;
    private ArrayList<AlarmLabel> labels;

    //Effects: initializes and sets up the panel
    public AlarmControllerPanelLabels() {
        labels = new ArrayList<>();
        show = true;
        view = true;
        setLayout();
        setBackground(Color.lightGray);
        setMinimumSize(new Dimension(0, 0));
        //setBounds(0,0, WIDTH,HEIGHT);
        //setPreferredSize();

        setVisible(true);
    }

    public static Boolean getShow() {
        return show;
    }

    //Effects:sets the preferred size of the panel
    private void setPreferredSize() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    //Effects: sets the layout of the panel
    private void setLayout() {
        setLayout(new GridLayout(rows, 1));
    }

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
        HEIGHT -= 20;
        rows--;
        for (AlarmLabel alarmLabel : labels) {
            if (alarmLabel.getName().equals(name)) {
                remove(alarmLabel);
                labels.remove(alarmLabel);
            }
        }
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
    private void changeRender() {
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
