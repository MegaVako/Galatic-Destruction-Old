package galactic.destruction;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.Timer;

public class Bullet {

    public int Ypos, Xpos, bt;

    public Timer t;

    public Bullet(int X, int Y, int type) {
        Xpos = X;
        Ypos = Y;
        bt = type;
    }

    public void paint(Graphics2D g2d) {
        if (bt == 1) {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(Xpos, Ypos, 3, 8);
            Ypos -= 7;
        } else if (bt == 2) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(Xpos, Ypos, 3, 8);
            Ypos += 5;
        } else if (bt == 3) {
            g2d.setColor(new Color(255, 128, 0));
            g2d.fillRect(Xpos, Ypos, 4, 10);
            Ypos += 5;
        }
    }
}
