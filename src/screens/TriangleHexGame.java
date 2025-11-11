package screens;

import java.awt.*;
import javax.swing.*;

public class TriangleHexGame {
    static final Color COLOURBACK = Color.WHITE;
    static final Color COLOURCELL = Color.CYAN;
    static final Color COLOURGRID = Color.BLACK;
    static final int HEXSIZE = 17;
    static final int BORDERS = 15;
    static final int SIZE = 20; // גובה המשולש (מספר שורות)

    public TriangleHexGame() {
        HexMech.setXYasVertex(false);
        HexMech.setHeight(HEXSIZE);
        HexMech.setBorders(BORDERS);

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Triangle Hex Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new DrawingPanel());
        frame.setSize(450, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class DrawingPanel extends JPanel {
        DrawingPanel() {
            setBackground(COLOURBACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // ציור המשושים בצורת משולש
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col <= row; col++) {
                    int i = col + (SIZE - row) / 2;
                    int j = row;
                    HexMech.drawHex(i, j, g2);
                }
            }
        }
    }

    // אותו HexMech כמו בפרח
    static class HexMech {
        static final boolean orFLAT = true;
        static final boolean orPOINT = false;
        static boolean XYVertex = true;
        static int BORDERS = 0;
        static int s = 0, t = 0, r = 0, h = 0;

        static void setXYasVertex(boolean b) { XYVertex = b; }
        static void setBorders(int b) { BORDERS = b; }
        static void setHeight(int height) {
            h = height;
            r = h / 2;
            s = (int) (h / 1.73205);
            t = (int) (r / 1.73205);
        }

        static Polygon hex(int x0, int y0) {
            int x = x0 + BORDERS;
            int y = y0 + BORDERS;
            int[] cx = {x + t, x + s + t, x + s + 2*t, x + s + t, x + t, x};
            int[] cy = {y, y, y + r, y + 2*r, y + 2*r, y + r};
            return new Polygon(cx, cy, 6);
        }

        static void drawHex(int i, int j, Graphics2D g2) {
            int x = i * (s + t);
            int y = j * h + (i % 2) * h / 2;
            Polygon hex = hex(x, y);
            g2.setColor(COLOURCELL);
            g2.fillPolygon(hex);
            g2.setColor(COLOURGRID);
            g2.drawPolygon(hex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TriangleHexGame::new);
    }
}
