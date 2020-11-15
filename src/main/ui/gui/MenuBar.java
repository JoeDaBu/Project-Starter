package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar implements ActionListener {
    JMenu menu;
    JMenu menu2;
    JMenu menu3;
    JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;

    MenuBar() {
        menu = new JMenu("File");
        menu.setMnemonic('F');
        menu.setMnemonic(KeyEvent.VK_F);

        menuItem1 = new JMenuItem("Save");
        menuItem1.setMnemonic('S');
        menuItem1.addActionListener(this);

        menuItem2 = new JMenuItem("Load");
        menuItem2.setMnemonic('L');
        menuItem2.addActionListener(this);

        menuItem3 = new JMenuItem("Quit");
        menuItem3.setMnemonic('Q');
        menuItem3.addActionListener(this);

        int h = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        menuItem1.setAccelerator(KeyStroke.getKeyStroke('S', h));
        menuItem2.setAccelerator(KeyStroke.getKeyStroke('L', h));
        menuItem3.setAccelerator(KeyStroke.getKeyStroke('Q', h));

        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
