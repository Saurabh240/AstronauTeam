package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;


/** מסך שמציג את הפעולות האפשריות לשחקן בכל תור שלו
 *
 */
public class Possiable_Actions_GUI extends JDialog {

    private JLabel title;
    public Possiable_Actions_GUI(String first_action, String second_action, String third_action, String four_action) {
        setTitle("בחר פעולה");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        title = new JLabel("מה תבחר לעשות כעת");
        title.setFont(new Font("Ariel",Font.ITALIC,23));

        // Horizontal panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creating options
        addOption(optionsPanel, first_action);
        addOption(optionsPanel, second_action);
        addOption(optionsPanel, third_action);
        addOption(optionsPanel, four_action);

        add(optionsPanel, BorderLayout.CENTER);
        add(title,BorderLayout.NORTH);
    }

    // פונקצייה להוספת אפשרויות בחירה לפאנל
    private void addOption(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.ITALIC, 19));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // מאזין לחיצה
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                send_action(text);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK);
            }
        });

        panel.add(label);
    }

    // Send action to server
    public void send_action(String action) {


    }


}

