package screens;

import javax.swing.*;
import java.awt.*;

public class DiamondHexGame {
    static final Color COLOURBACK   = Color.WHITE;
    static final Color COLOURCELL   = new Color(0x99CCFF);
    static final Color COLOURGRID   = Color.BLACK;
    static final Color COLOURCENTER = new Color(0xFF6666);

    static final int HEXSIZE = 17;
    static final int BORDERS = 16;
    static final int RADIUS  = 14;   // "חצי הגובה" של המעוין

    public DiamondHexGame() {
        HexMech.setXYasVertex(false);
        HexMech.setHeight(HEXSIZE);
        HexMech.setBorders(BORDERS);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame f = new JFrame("Hex Diamond Board");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new DrawingPanel());
        int w = (int)((RADIUS*2+6) * (HexMech.s + HexMech.t) + 60);
        int h = (int)((RADIUS*2+6) * (HexMech.h) + 80);
        f.setSize(w, h);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    static class DrawingPanel extends JPanel {
        DrawingPanel(){ setBackground(COLOURBACK); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerI = RADIUS * 3 + 2;
            int centerJ = RADIUS * 3 + 2;

            // ציור המעוין
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                int rowLen = RADIUS - Math.abs(dy);
                for (int dx = -rowLen; dx <= rowLen; dx++) {
                    int i = centerI + dx;
                    int j = centerJ + dy;
                    if (i == centerI && j == centerJ)
                        drawHexFilled(i, j, g2, COLOURCENTER);
                    else
                        drawHexFilled(i, j, g2, COLOURCELL);
                }
            }
        }

        private void drawHexFilled(int i, int j, Graphics2D g2, Color fill) {
            int x = i * (HexMech.s + HexMech.t);
            int y = j * HexMech.h + (i % 2) * HexMech.h / 2;
            Polygon hex = HexMech.hex(x, y);
            g2.setColor(fill);
            g2.fillPolygon(hex);
            g2.setColor(COLOURGRID);
            g2.drawPolygon(hex);
        }
    }

    static class HexMech {
        static boolean XYVertex = true;
        static int BORDERS = 0;
        static int s = 0, t = 0, r = 0, h = 0;

        static void setXYasVertex(boolean b){ XYVertex = b; }
        static void setBorders(int b){ BORDERS = b; }
        static void setHeight(int height){
            h = height;
            r = h / 2;
            s = (int)(h / 1.73205);
            t = (int)(r / 1.73205);
        }

        static Polygon hex(int x0, int y0){
            int x = x0 + BORDERS;
            int y = y0 + BORDERS;
            int[] cx = { x + t, x + s + t, x + s + 2*t, x + s + t, x + t, x };
            int[] cy = { y, y, y + r, y + 2*r, y + 2*r, y + r };
            return new Polygon(cx, cy, 6);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiamondHexGame::new);
    }
}
