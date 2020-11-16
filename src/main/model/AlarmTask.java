package model;

import javax.swing.*;
import java.util.TimerTask;

//When an alarm goes off this class shows what happens
public class AlarmTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("It is Time!\nIt is Time!");
        System.out.println("Hammer Time!");
        JOptionPane.showMessageDialog(null,
                "Tik Tok On The Clock!",
                "Hammer Time?",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
