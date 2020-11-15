// package ui.gui;
//
// import model.Alarm;
//
// import javax.swing.*;
// import javax.swing.border.Border;
// import java.awt.*;
// import java.awt.font.TextAttribute;
// import java.util.HashMap;
// import java.util.Map;
//
// public class AlarmControllerPanelLabels extends JInternalFrame implements Observer {
//     private static int HEIGHT;
//     private static int rows = 0;
//     private static final int WIDTH = 390;
//     private LayoutManager labels;
//
//
//     public AlarmControllerPanelLabels() {
//         setLayout();
//         setBackground(new Color(255,0,0));
//         //setBounds(0,0, WIDTH,HEIGHT);
//         //setPreferredSize();
//
//         setVisible(true);
//     }
//
//     private void setPreferredSize() {
//         setPreferredSize(new Dimension(WIDTH, HEIGHT));
//     }
//
//     private void setLayout() {
//         setLayout(new GridLayout(rows, 1));
//     }
//
//     @Override
//     public void updateAdd(String name, Alarm alarm) {
//         HEIGHT += 20;
//         rows++;
//         setLayout();
//         setPreferredSize();
//         setVisible(true);
//         Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
//         JLabel alarmLabel = new JLabel(alarm.alarmToString());
//         Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
//         fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
//         alarmLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20).deriveFont(fontAttributes));
//         alarmLabel.setForeground(Color.BLACK);
//         alarmLabel.setBackground(Color.lightGray);
//         alarmLabel.setOpaque(true);
//         alarmLabel.setBorder(border);
//         alarmLabel.setVerticalAlignment(JLabel.TOP);
//         add(alarmLabel);
//         revalidate();
//         repaint();
//
//     }
//
//     @Override
//     public void updateRemove(Alarm alarm) {
//         HEIGHT -= 20;
//         rows--;
//         remove(JLabel);
//         setLayout();
//         setPreferredSize();
//         setVisible(true);
//
//     }
// }
