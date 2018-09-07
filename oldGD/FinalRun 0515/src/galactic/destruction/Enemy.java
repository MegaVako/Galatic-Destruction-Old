package galactic.destruction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Enemy {

    public int enemy[][];

    public int hspace;

    public int vspace;

    public int move, t;

    public int r, g, b;

    public BufferedImage R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12;

    public BufferedImage B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12;

    public BufferedImage P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12;

    public BufferedImage Y1, Y2, Y3, Y4, Y5, Y6, Y7, Y8, Y9, Y10;

    private int dir = 0;

    public Enemy(int row, int col) {
        enemy = new int[row][col];
        for (int i = 0; i < enemy.length; i++) {
            for (int j = 0; j < enemy[0].length; j++) {
                if ((i == 1 && j == 3) || (i == 1 && j == 6) || (i > 1) && (enemy[i][j] != 2)) {
                    enemy[i][j] = 1;
                } else {
                    enemy[i][j] = 0;
                }
            }
        }
        enemy[2][0] = enemy[2][1] = enemy[2][8] = enemy[2][9] = 0;
        enemy[3][0] = enemy[3][9] = 0;
        hspace = 400 / col;
        vspace = 200 / row;
        try {
            R11 = ImageIO.read(new File("src/GDPictures/R11.png"));
            R12 = ImageIO.read(new File("src/GDPictures/R12.png"));
            B11 = ImageIO.read(new File("src/GDPictures/B11.png"));
            B12 = ImageIO.read(new File("src/GDPictures/B12.png"));
            P11 = ImageIO.read(new File("src/GDPictures/P11.png"));
            P12 = ImageIO.read(new File("src/GDPictures/P12.png"));
            Y1 = ImageIO.read(new File("src/GDPictures/Y1.png"));
        } catch (IOException e) {
            System.out.println("Can't find image");
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < enemy.length; i++) {
            for (int j = 0; j < enemy[0].length; j++) {
                if (enemy[i][j] == 1) {
                    if (t % 60 > 30) {
                        switch(i) {
                            case 1:
                                g2d.drawImage(Y1, j * hspace + 80 + move, i * vspace + 7, 30, 30, null);
                                break;
                            case 2:
                                g2d.drawImage(R11, j * hspace + 80 + move, i * vspace + 10, 30, 15, null);
                                break;
                            case 3:
                                g2d.drawImage(P11, j * hspace + 80 + move, i * vspace + 10, 30, 15, null);
                                break;
                            case 4:
                            case 5:
                            case 6:
                                g2d.drawImage(B11, j * hspace + 80 + move, i * vspace + 10, 30, 15, null);
                                break;
                            default:
                                break;
                        }
                    } else {
                        switch(i) {
                            case 1:
                                g2d.drawImage(Y1, j * hspace + 80 + move, i * vspace + 7, 30, 30, null);
                                break;
                            case 2:
                                g2d.drawImage(R12, j * hspace + 80 + move, i * vspace + 10, 30, 15, null);
                                break;
                            case 3:
                                g2d.drawImage(P12, j * hspace + 80 + move, i * vspace + 10, 30, 15, null);
                                break;
                            case 4:
                            case 5:
                            case 6:
                                g2d.drawImage(B12, j * hspace + 80 + move, i * vspace + 10, 30, 15, null);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        t++;
        moveLR();
    }

    public void moveLR() {
        if (dir % 200 < 100) {
            if (dir % 3 == 1) {
                move++;
            }
        } else {
            if (dir % 3 == 1) {
                move--;
            }
        }
        dir++;
    }

    public void setEnemyValue(int v, int i, int j) {
        enemy[i][j] = v;
    }

    public void clearforboss() {
        for (int i = 0; i < enemy.length; i++) {
            for (int j = 0; j < enemy[0].length; j++) {
                enemy[i][j] = 0;
            }
        }
    }
}
