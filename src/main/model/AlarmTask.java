package model;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.util.TimerTask;

//When an alarm goes off this class shows what happens
public class AlarmTask extends TimerTask {
    private Clip ring;

    //Effects: constructs a siren wave file and sets up audio system
    public AlarmTask() {
        try {
            String sep = System.getProperty("file.separator");
            File soundFile = new File(System.getProperty("user.dir") + sep
                    + "data" + sep + "alarm.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = sound.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                ring = null;
            } else {
                ring = (Clip) AudioSystem.getLine(info);
                //line.setLoopPoints(0, -1);
                ring.open(sound);
            }
        } catch (Exception ex) {
            ring = null;
        }
    }

    @Override
    public void run() {
        if (ring != null) {
            ring.setFramePosition(0);
            ring.start();
//            ring.stop();
        }
        System.out.println("It is Time!\nIt is Time!");
        System.out.println("Hammer Time!");
        JOptionPane.showMessageDialog(null,
                "Tik Tok On The Clock!",
                "Hammer Time?",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
