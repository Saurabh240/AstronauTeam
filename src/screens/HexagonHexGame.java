package screens;

import javax.swing.*;
import java.awt.*;

public class HexagonHexGame {
    static final Color COLOURBACK   = Color.WHITE;
    static final Color COLOURCELL   = new Color(0xA7E4FF);
    static final Color COLOURGRID   = Color.BLACK;
    static final Color COLOURCENTER = new Color(0xFF6666);

    static final int HEXSIZE = 17;
    static final int BORDERS = 16;
    static final int RADIUS  = 14;   // מספר טבעות סביב המרכז (משושה בגודל 1=רק המרכז)

    public HexagonHexGame() {
        HexMech.setXYasVertex(false);
        HexMech.setHeight(HEXSIZE);
        HexMech.setBorders(BORDERS);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame f = new JFrame("Hexagon Board");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new DrawingPanel());
        int w = (int)((RADIUS*2+5) * (HexMech.s + HexMech.t) + 80);
        int h = (int)((RADIUS*2+5) * (HexMech.h) + 80);
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

            int centerI = RADIUS * 3 + 3;
            int centerJ = RADIUS * 3 + 3;

            // מצייר את כל התאים שהמרחק שלהם <= RADIUS
            int span = RADIUS * 3 + 6;
            for (int i = centerI - span; i <= centerI + span; i++) {
                for (int j = centerJ - span; j <= centerJ + span; j++) {
                    if (hexDistanceOffset(centerI, centerJ, i, j) <= RADIUS) {
                        if (i == centerI && j == centerJ)
                            drawHexFilled(i, j, g2, COLOURCENTER);
                        else
                            drawHexFilled(i, j, g2, COLOURCELL);
                    }
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

        private int hexDistanceOffset(int ci, int cj, int i, int j) {
            // המרה לקואורדינטות axial ומרחק הקסגונלי אמיתי
            int cq = ci;
            int cr = cj - (ci - (ci & 1)) / 2;
            int q  = i;
            int r  = j - (i - (i & 1)) / 2;

            int x1 = cq, z1 = cr, y1 = -x1 - z1;
            int x2 = q,  z2 = r,  y2 = -x2 - z2;

            int dx = x1 - x2;
            int dy = y1 - y2;
            int dz = z1 - z2;

            return Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
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
        SwingUtilities.invokeLater(HexagonHexGame::new);
    }
}
