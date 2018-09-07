package galactic.destruction;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class Boss {

    public BufferedImage b;

    public int Xpos, Ypos, dir, HP = 1000, bulletn = 0;

    public boolean firebullet = false;

    public Boss() {
        try {
            b = ImageIO.read(new File("src/GDPictures/mship.png"));
        } catch (IOException e) {
            System.out.println("Can't find image");
        }
        dir = 2;
        Ypos = -90;
    }

    public void paint(Graphics g) {
        g.drawImage(b, Xpos, Ypos, 240, 90, null);
        if (Ypos <= 30) {
            enter();
        } else {
            moveLR();
        }
        if (HP > 500) {
            g.setColor(Color.GREEN);
        } else if (HP < 500 && HP > 200) {
            g.setColor(Color.ORANGE);
        } else if (HP < 200) {
            g.setColor(Color.RED);
        }
        g.fillRect(200, 5, HP / 5, 5);
        fire();
    }

    public void moveLR() {
        int i = ThreadLocalRandom.current().nextInt(0, 100);
        Xpos += dir;
        if (Xpos == 400 || Xpos == -10 || i == 3) {
            dir = -dir;
        }
    }

    public void enter() {
        Ypos += 1;
    }

    public void fire() {
        if (HP > 500) {
            firerate(1);
        } else if (HP < 500 && HP > 200) {
            firerate(2);
        } else if (HP < 200) {
            firerate(3);
        }
    }

    public void firerate(int c) {
        int i = ThreadLocalRandom.current().nextInt(0, 30);
        int MaxB = 0;
        int Rate = 0;
        switch(i) {
            case 1:
                MaxB = 10;
                Rate = 5;
                break;
            case 2:
                MaxB = 30;
                Rate = 15;
                break;
            case 3:
                MaxB = 50;
                Rate = 25;
                break;
        }
        if (bulletn < MaxB && i < Rate) {
            firebullet = true;
        } else {
            firebullet = false;
        }
    }

    public void reset() {
        HP = 1000;
        dir = 2;
        Ypos = -90;
        Xpos = 0;
        bulletn = 0;
    }
}
