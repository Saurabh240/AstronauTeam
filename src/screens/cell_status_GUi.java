package screens;

import javax.swing.*;
import java.awt.*;

/** מחלקה שמייצגת הודעה שתיקפוץ ברגע שהשחקן מגיע למשבצת מיוחדת כמו: משבצת חמצן,משבצת קרינה שדרכה שחקן עם ציוד מיוחד לא יכול לעבור וכוליי...
 *
 */

public class cell_status_GUi extends JDialog {

    private JLabel jLabel,image;
    private JPanel jPanel;
    private  JButton jButton;
    private ImageIcon imageIcon;
    public cell_status_GUi(String notification,String imagefile){

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        imageIcon = new ImageIcon(imagefile);
        jPanel = new JPanel();

        jButton = new JButton("אישור");
        jButton.setPreferredSize(new Dimension(80,20));

        jLabel = new JLabel(notification);
        jLabel.setPreferredSize(new Dimension(200,100));
        jLabel.setFont(new Font("Ariel",Font.ITALIC,20));
        image = new JLabel(imageIcon);

        jLabel.add(jLabel, BorderLayout.NORTH);
        jLabel.add(image, BorderLayout.CENTER);
        jLabel.add(jButton,BorderLayout.SOUTH);

        add(jPanel);
    }
}
