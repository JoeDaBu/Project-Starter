package ui.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LabelsWithImage extends AlarmControllerPanelLabels implements Observer {
    private static Image bgImage2;
    private static final Boolean hasImage = true;
    private static Boolean showing = true;

    public LabelsWithImage() {
        super();
        setImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage2, 40, 5, null);
    }

    //Effects: gets the image from file
    private void setImage() {
        try {
            Image image;
            image = ImageIO.read(new File("./data/geometric-cool-elephant-wall-clocks.jpg"));
            bgImage2 = image.getScaledInstance(WIDTH - 60,290, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Image Error");
        }
    }

    @Override
    public void updateImage() {
    }
}
