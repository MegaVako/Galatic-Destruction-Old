package galactic.destruction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

public class DrawStars {

    private int r, g, b;

    public int X, Y;

    public DrawStars() {
    }

    public void paint(Graphics gg) {
        int a = ThreadLocalRandom.current().nextInt(0, 30);
        if (a > 3) {
            gg.setColor(new Color(r, g, b));
            gg.fillRect(X, Y, 3, 3);
        }
        moveup();
    }

    public void RandomColor() {
        r = ThreadLocalRandom.current().nextInt(0, 256);
        g = ThreadLocalRandom.current().nextInt(0, 256);
        b = ThreadLocalRandom.current().nextInt(0, 256);
    }

    public void moveup() {
        Y += 3;
    }

    public void setXY() {
        RandomColor();
        X = ThreadLocalRandom.current().nextInt(0, 600);
        Y = ThreadLocalRandom.current().nextInt(0, 20);
    }
}
