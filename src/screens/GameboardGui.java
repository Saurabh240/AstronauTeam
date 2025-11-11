package screens;

import datamodels.User;

import javax.swing.*;
import java.awt.*;

import java.lang.reflect.Method;

/** מחלקה שמייצגת ייצוג גרפי של שחקן בודד
 *
 */
class PlayerToken extends JComponent {
    private static final int SIZE = 16;
    private final User player;

    public PlayerToken(User player) {
        this.player = player;
        setPreferredSize(new Dimension(SIZE, SIZE));

        setToolTipText(player.getFirstname() + " " + player.getLastname());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // אם ל-Player יש צבע:
        Color tokenColor = Color.BLUE;
        try {

            Method m = player.getClass().getMethod("getColor");
            Object res = m.invoke(player);
            if (res instanceof Color) tokenColor = (Color) res;
        } catch (Exception ignored) { /* ברירת מחדל כחול */ }

        g2.setColor(tokenColor);
        g2.fillOval(0, 0, SIZE - 1, SIZE - 1);

        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, SIZE - 1, SIZE - 1);

        g2.dispose();
    }




}

