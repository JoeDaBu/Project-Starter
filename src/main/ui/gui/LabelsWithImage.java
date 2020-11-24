package ui.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//the alarm labels with an attached image
public class LabelsWithImage extends AlarmControllerPanelLabels {
    private static Image bgImage2;

    //Effects: initializes and sets up the panel with an image
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
