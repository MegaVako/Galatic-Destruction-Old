package galactic.destruction;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Score {

    public int playerscore = 0;

    public BufferedImage shiplife, F1, F2;

    public int life = 3;

    public int Level = 1;

    public Score() {
        try {
            shiplife = ImageIO.read(new File("src/GDPictures/Ship2.png"));
            F1 = ImageIO.read(new File("src/GDPictures/Flag1.png"));
            F2 = ImageIO.read(new File("src/GDPictures/Flag2.png"));
        } catch (IOException e) {
            System.out.println("Can't find image");
        }
    }

    public void paint(Graphics g) {
        g.setColor(new Color(0, 255, 255));
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: ", 200, 32);
        g.drawString(Integer.toString(playerscore), 300, 32);
        for (int ship = 0; ship < life; ship++) {
            g.drawImage(shiplife, 360 + ship * 20, 15, 20, 20, null);
        }
        for (int flag = 0; flag < Level; flag++) {
            if (Level < 10) {
                g.drawImage(F1, 460 + flag * 20, 15, 20, 40, null);
            }
            if (Level > 10) {
                for (int f2 = 0; f2 < Level - 10; f2 += 10) {
                    g.drawImage(F2, 460 + f2 * 20, 15, 40, 40, null);
                }
                for (int f1 = 0; f1 < Level - 10; f1++) {
                    g.drawImage(F1, 460 + 40 + f1 * 20, 15, 20, 40, null);
                }
            }
        }
    }

    public void setScore(int s) {
        playerscore += s;
    }

    public void setLife() {
        life--;
    }
}
