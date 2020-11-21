package ui.gui;

public class LabelsWithoutImage extends AlarmControllerPanelLabels implements Observer {
    private static final Boolean hasImage = false;
    private static Boolean showing = false;

    public LabelsWithoutImage() {
        super();
    }

    @Override
    public void updateImage() {
        if (showing) {
            setVisible(false);
            showing = false;
        } else {
            setVisible(true);
            showing = true;
        }
    }
}
