package screens;


    import javax.swing.*;
import java.awt.*;

    public class CircleHexGame {
        // צבעים
        static final Color COLOURBACK   = Color.WHITE;
        static final Color COLOURCELL   = new Color(0x66CCFF); // תאים רגילים
        static final Color COLOURGRID   = Color.BLACK;         // קווי מתאר
        static final Color COLOURCENTER = new Color(0xFF6666); // המרכז

        // גדלים
        static final int HEXSIZE = 17;  // גובה משושה (מרחק בין צלעות מקבילות)
        static final int BORDERS = 16;  // שוליים
        static final int RADIUS  = 14;   // רדיוס העיגול במרחקי-משושים (אפשר להגדיל/להקטין)

        public CircleHexGame() {
            HexMech.setXYasVertex(false);
            HexMech.setHeight(HEXSIZE);
            HexMech.setBorders(BORDERS);
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            JFrame f = new JFrame("Hex Circle Board");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new DrawingPanel());
            // גודל חלון משוער לפי רדיוס
            int w = (int)( (RADIUS*2+5) * (HexMech.s + HexMech.t) + BORDERS*2 + 40 );
            int h = (int)( (RADIUS*2+5) * (HexMech.h)           + BORDERS*2 + 60 );
            f.setSize(w, h);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }

       static   class DrawingPanel extends JPanel {
            DrawingPanel(){ setBackground(COLOURBACK); }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // נגדיר מרכז לוח (ביחידות "עמודה-שורה" של ההיסט המשושים שלנו)
                int centerI = RADIUS * 2 + 2;   // מרווח ביטחון כדי שהכל ייכנס יפה
                int centerJ = RADIUS * 2 + 2;

                // נטייל בסביבות המרכז ונצייר רק תאים שמרחקם ההקסגונלי <= RADIUS
                int span = RADIUS * 3 + 6; // מרובע סריקה מספיק גדול
                for (int i = centerI - span; i <= centerI + span; i++) {
                    for (int j = centerJ - span; j <= centerJ + span; j++) {
                        if (hexDistanceOffset(centerI, centerJ, i, j) <= RADIUS) {
                            // צבע מרכז שונה
                            if (i == centerI && j == centerJ) {
                                drawHexFilled(i, j, g2, COLOURCENTER);
                            } else {
                                drawHexFilled(i, j, g2, COLOURCELL);
                            }
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

            /**
             * מרחק בין שני תאים במונחי "מרחק הקסגונלי" (cube distance),
             * בהנחה של פריסת offset מסוג odd-q (העמודות המזוגגות למטה כש-i אי-זוגי).
             */
            private int hexDistanceOffset(int ci, int cj, int i, int j) {
                // המרה מ-offset (odd-q) לקואורדינטות axial (q,r), ואז ל-cube (x,y,z)
                int cq = ci;
                int cr = cj - (ci - (ci & 1)) / 2; // r_center
                int q  = i;
                int r  = j - (i - (i & 1)) / 2;

                // cube coords: x = q, z = r, y = -x - z
                int x1 = cq, z1 = cr, y1 = -x1 - z1;
                int x2 = q,  z2 = r,  y2 = -x2 - z2;

                int dx = x1 - x2;
                int dy = y1 - y2;
                int dz = z1 - z2;

                return Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
            }
        }

        /** כלי גאומטרי למשושה (כמו בגרסאות הקודמות) */
        static class HexMech {
            static boolean XYVertex = true; // לא משתמשים בו כאן (משאירים false)
            static int BORDERS = 0;
            static int s = 0, t = 0, r = 0, h = 0;

            static void setXYasVertex(boolean b){ XYVertex = b; }
            static void setBorders(int b){ BORDERS = b; }
            static void setHeight(int height){
                h = height;
                r = h / 2;
                s = (int) (h / 1.73205);  // ~ h / sqrt(3)
                t = (int) (r / 1.73205);
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
            SwingUtilities.invokeLater(CircleHexGame::new);
        }
    }


