package galactic.destruction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class MoveEnemy {

    public int EXi, EYi, hsapce, vspace, move, EX, EY, t, EXdir, EYdir, Ii, Ji;

    private boolean comeb = false;

    public Enemy enemy;

    private BufferedImage c;

    public BufferedImage R1, R2, R3, R4, R5, R6, R7, R8, R9, R10;

    public BufferedImage B1, B2, B3, B4, B5, B6, B7, B8, B9, B10;

    public BufferedImage P1, P2, P3, P4, P5, P6, P7, P8, P9, P10;

    public BufferedImage Y1, Y2, Y3, Y4, Y5, Y6, Y7, Y8, Y9, Y10;

    public boolean fire;

    public int bulletX, bulletY;

    public MoveEnemy() {
        try {
            Y2 = ImageIO.read(new File("src/GDPictures/Y2.png"));
            Y3 = ImageIO.read(new File("src/GDPictures/Y3.png"));
            Y4 = ImageIO.read(new File("src/GDPictures/Y4.png"));
            Y5 = ImageIO.read(new File("src/GDPictures/Y5.png"));
            Y6 = ImageIO.read(new File("src/GDPictures/Y6.png"));
            Y7 = ImageIO.read(new File("src/GDPictures/Y7.png"));
            R5 = ImageIO.read(new File("src/GDPictures/R5.png"));
            R6 = ImageIO.read(new File("src/GDPictures/R6.png"));
            R7 = ImageIO.read(new File("src/GDPictures/R7.png"));
            R8 = ImageIO.read(new File("src/GDPictures/R8.png"));
            R9 = ImageIO.read(new File("src/GDPictures/R9.png"));
            R10 = ImageIO.read(new File("src/GDPictures/R10.png"));
            P5 = ImageIO.read(new File("src/GDPictures/P5.png"));
            P6 = ImageIO.read(new File("src/GDPictures/P6.png"));
            P7 = ImageIO.read(new File("src/GDPictures/P7.png"));
            P8 = ImageIO.read(new File("src/GDPictures/P8.png"));
            P9 = ImageIO.read(new File("src/GDPictures/P9.png"));
            P10 = ImageIO.read(new File("src/GDPictures/P10.png"));
            B5 = ImageIO.read(new File("src/GDPictures/B5.png"));
            B6 = ImageIO.read(new File("src/GDPictures/B6.png"));
            B7 = ImageIO.read(new File("src/GDPictures/B7.png"));
            B8 = ImageIO.read(new File("src/GDPictures/B8.png"));
            B9 = ImageIO.read(new File("src/GDPictures/B9.png"));
            B10 = ImageIO.read(new File("src/GDPictures/B10.png"));
        } catch (IOException e) {
            System.out.println("Can't find image");
        }
    }

    public void paint(Graphics2D g, int PX, int PY) {
        if (EX > PX) {
            g.drawImage(c, EX + 32, EY + 32, -32, -32, null);
        } else {
            g.drawImage(c, EX, EY + 32, 32, -32, null);
        }
        if (EX < 600 || EX > 0 || EY < 800 && !comeb) {
            move(PX, PY);
        }
        if (EY > 300 && !fire && EY < 350) {
            firebullet(EX, EY);
        }
        bound();
    }

    public void setXY(int j, int i, int X, int Y) {
        EXi = X;
        EYi = Y;
        EX = EXi;
        EY = EYi;
        Ii = i;
        Ji = j;
        setIdir();
    }

    public void move(int PX, int PY) {
        EYdir = 4;
        if (EX > PX) {
            if (t % 8 == 3) {
                EXdir--;
            }
        } else {
            if (t % 8 == 3) {
                EXdir++;
            }
        }
        if (t % 2 == 1) {
            EX += EXdir;
            EY += EYdir;
        }
        t++;
        if (EX > 600 || EX < 0 || EY > 800) {
            EY = 0;
            comeb = true;
            comeback();
        }
        setImage(PX, PY);
    }

    public void bound() {
        if (EX > 600 || EX < 0 || EY < EYi || EY > 800) {
            comeb = true;
            comeback();
        }
    }

    public int check(int x, int y) {
        return x * y;
    }

    public void setIdir() {
        if (EXi > 300) {
            EXdir = -1;
        } else {
            EXdir = 1;
        }
    }

    public void setImage(int PX, int PY) {
        int b = EX - PX;
        int a = PY - EY;
        if (b == 0) {
            b = 2;
        }
        double i = Math.atan((a) / Math.abs(b));
        switch(Ii) {
            case 1:
                if (i > (Math.PI / 2.5)) {
                    c = Y2;
                } else if (i > (Math.PI / 3) && i < (Math.PI / 2.5)) {
                    c = Y3;
                } else if (i > (Math.PI / 4) && i < (Math.PI / 3)) {
                    c = Y4;
                } else if (i > (Math.PI / 6) && i < (Math.PI / 4)) {
                    c = Y5;
                } else if (i > (Math.PI / 8) && i < (Math.PI / 6)) {
                    c = Y6;
                } else if (i < (Math.PI / 8)) {
                    c = Y7;
                }
                break;
            case 2:
                if (i > (Math.PI / 2.5)) {
                    c = R5;
                } else if (i > (Math.PI / 3) && i < (Math.PI / 2.5)) {
                    c = R6;
                } else if (i > (Math.PI / 4) && i < (Math.PI / 3)) {
                    c = R7;
                } else if (i > (Math.PI / 6) && i < (Math.PI / 4)) {
                    c = R8;
                } else if (i > (Math.PI / 8) && i < (Math.PI / 6)) {
                    c = R9;
                } else if (i < (Math.PI / 8)) {
                    c = R10;
                }
                break;
            case 3:
                if (i > (Math.PI / 3)) {
                    c = P5;
                } else if (i > (Math.PI / 5) && i < (Math.PI / 3)) {
                    c = P6;
                } else if (i > (Math.PI / 6) && i < (Math.PI / 5)) {
                    c = P7;
                } else if (i > (Math.PI / 7) && i < (Math.PI / 6)) {
                    c = P8;
                } else if (i > (Math.PI / 10) && i < (Math.PI / 7)) {
                    c = P9;
                } else if (i < (Math.PI / 10)) {
                    c = P10;
                }
                break;
            case 4:
            case 5:
            case 6:
                if (i > (Math.PI / 3)) {
                    c = B5;
                } else if (i > (Math.PI / 5) && i < (Math.PI / 3)) {
                    c = B6;
                } else if (i > (Math.PI / 6) && i < (Math.PI / 5)) {
                    c = B7;
                } else if (i > (Math.PI / 7) && i < (Math.PI / 6)) {
                    c = B8;
                } else if (i > (Math.PI / 10) && i < (Math.PI / 7)) {
                    c = B9;
                } else if (i < (Math.PI / 10)) {
                    c = B10;
                }
                break;
        }
    }

    public void comeback() {
        EX = EXi;
        if (EY < EYi) {
            EY++;
            EXdir = 0;
            EYdir = 1;
        }
        if (EY == EYi) {
            comeb = false;
        }
        EX += EXdir;
        EY += EYdir;
        fire = false;
    }

    public void firebullet(int X, int Y) {
        int i = ThreadLocalRandom.current().nextInt(0, 50);
        if (i == 1 || i == 5) {
            fire = true;
            System.out.println("Enemy fire");
        }
    }
}
