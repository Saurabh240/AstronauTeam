package screens;

import javax.swing.*;
import java.awt.*;


/** מחלקה שמייצגת את הודעת השגיאה שתיקפוץ ברגע שהשחקן יינסה 3 פעמים להיתחבר ולא יצליח ואז ייחסם
 *
 */
public class Block_message_GUI extends JDialog {

    private JButton jButton;
    private JLabel jLabel;
    private JPanel panel;
    public Block_message_GUI(){

        setDefaultCloseOperation(JFrame. HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        jLabel = new JLabel("עקב 3 ניסיונות היתחברות שגויים הינך חסום מלהיכנס אנא נסה שוב בעוד 10 דקות ");
        jLabel.setPreferredSize(new Dimension(100,100));

        jButton = new JButton("אישור");
        jButton.setPreferredSize(new Dimension(50,30));

        panel.add(jLabel,BorderLayout.CENTER);
        panel.add(jButton,BorderLayout.SOUTH);

        add(panel);

    }
}
