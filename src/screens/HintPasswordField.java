package screens;

import javax.swing.*;
import java.awt.*;


/** מחלקת עיצוב מותאם אישית של שדה הסיסמא
 *
 */
public class HintPasswordField extends JPasswordField {

    private final String hint;

    public HintPasswordField(String hint) {
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            g2.drawString(hint, insets.left + 2, getHeight() / 2 + getFont().getSize() / 2 - 2);
            g2.dispose();
        }
    }
}
