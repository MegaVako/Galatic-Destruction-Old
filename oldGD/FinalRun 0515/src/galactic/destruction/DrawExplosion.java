package galactic.destruction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class DrawExplosion {

    public BufferedImage E1, E2, E3, E4, E5, E6, E7, E8;

    public int type, t, Xpos, Ypos, points, ses, les;

    public boolean explosion = false;

    public DrawExplosion() {
        try {
            E1 = ImageIO.read(new File("src/GDPictures/Small Explosion1.png"));
            E2 = ImageIO.read(new File("src/GDPictures/Small Explosion2.png"));
            E3 = ImageIO.read(new File("src/GDPictures/Small Explosion3.png"));
            E4 = ImageIO.read(new File("src/GDPictures/Small Explosion4.png"));
            E5 = ImageIO.read(new File("src/GDPictures/Big Explosion1.png"));
            E6 = ImageIO.read(new File("src/GDPictures/Big Explosion2.png"));
            E7 = ImageIO.read(new File("src/GDPictures/Big Explosion3.png"));
            E8 = ImageIO.read(new File("src/GDPictures/Big Explosion4.png"));
        } catch (IOException e) {
            System.out.println("Can't find image");
        }
        ses = 30;
        les = 50;
    }

    public void paint(Graphics g) {
        if (explosion) {
            if (type == 1) {
                if (t < 20) {
                    g.drawImage(E1, Xpos, Ypos, ses, ses, null);
                } else if (t > 20 && t < 40) {
                    g.drawImage(E2, Xpos, Ypos, ses, ses, null);
                } else if (t > 40 && t < 60) {
                    g.drawImage(E3, Xpos, Ypos, ses, ses, null);
                } else if (t > 60 && t < 80) {
                    g.drawImage(E4, Xpos, Ypos, ses, ses, null);
                }
            } else if (type == 2) {
                if (t < 15) {
                    g.drawImage(E5, Xpos, Ypos, les, les, null);
                } else if (t > 15 && t < 30) {
                    g.drawImage(E6, Xpos, Ypos, les, les, null);
                } else if (t > 30 && t < 45) {
                    g.drawImage(E7, Xpos, Ypos, les, les, null);
                } else if (t > 45 && t < 60) {
                    g.drawImage(E8, Xpos, Ypos, les, les, null);
                }
            }
            t++;
            if (t > 100) {
                explosion = false;
            }
        }
    }

    public void receivetype(int X, int Y, int T, int pp) {
        type = T;
        Xpos = X;
        Ypos = Y;
        explosion = true;
        t = 0;
    }
}
