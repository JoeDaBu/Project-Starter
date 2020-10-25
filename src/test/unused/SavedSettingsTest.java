package unused;

import model.unused.SavedSettings;
import model.unused.SettingsList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SavedSettingsTest {
    public SettingsList settingsList;
    public SettingsList testList;
    public SavedSettings savedSettings;

    @BeforeEach
    public void makeSettings() {
        settingsList = new SettingsList("1", "2", "3", "4", "test");
        testList = new SettingsList("0", "1", "0", "2", "test2");
        savedSettings = new SavedSettings();
        savedSettings.addSettings(settingsList);
    }

    @Test
    public void testAddSettings() {
        assertEquals(savedSettings.getSettings().size(), 1);
        savedSettings.addSettings(testList);
        assertEquals(savedSettings.getSettings().size(), 2);
        assertEquals(savedSettings.getSettings().get(0), settingsList);
    }

    @Test
    public void testRemoveSettings() {
        savedSettings.addSettings(testList);
        assertEquals(savedSettings.removeSettings("test"), settingsList);
        assertEquals(savedSettings.getSettings().size(), 1);
    }

    @Test
    public void testRemoveSettings2() {
        assertNull(savedSettings.removeSettings("s"));
    }

    @Test
    public void testViewSettings() {
        savedSettings.addSettings(testList);
        assertEquals(savedSettings.viewSettings("test2"), testList);
    }

    @Test
    public void testViewSettings2() {
        savedSettings.addSettings(testList);
        assertNull(savedSettings.viewSettings("x"));
    }

    @Test
    public void testChangeSettings() {
        savedSettings.addSettings(testList);
        SettingsList test = new SettingsList("1", "2", "3", "0", "notlist");
        assertEquals(savedSettings.changeSettings("test", test), settingsList);
        assertEquals(savedSettings.getSettings().size(), 2);
        assertEquals(savedSettings.viewSettings("notlist"), test);
    }

    @Test
    public void testChangeSettings2() {
        SettingsList test = new SettingsList("1", "2", "3", "0", "notlist");
        assertNull(savedSettings.changeSettings("x", test));
    }

    @Test
    public void testGetSetting1() {
        assertEquals(settingsList.getSetting1(), "1");
    }

    @Test
    public void testSetSetting2() {
        settingsList.setSetting2("10");
        assertEquals(settingsList.getSetting2(), "10");
    }
}
