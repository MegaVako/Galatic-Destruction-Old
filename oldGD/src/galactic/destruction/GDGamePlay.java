package galactic.destruction;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import javax.sound.sampled.AudioSystem;
import sun.audio.AudioStream;

public class GDGamePlay extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private int delay = 10;
    public Enemy enemy;
    public Bullet bullet;
    public Score score;
    public MoveEnemy ME;
    public DrawExplosion drawexplosion;
    public Boss boss;

    public int Level = 1;
    private boolean gamestart;
    private boolean ML = false;
    private boolean MR = false;
    public boolean fire = false;

    private int PYpos = 700;
    public int PXpos = 260;
    private int cooldown = 80;
    private boolean lose, win = false;
    public int setEnemy = 0;

    public BufferedImage ship;

    private ArrayList<Bullet> b;
    private ArrayList<MoveEnemy> e;
    private ArrayList<DrawStars> ds;

    private int Enemynumber = 46;
    private int availableenemy = 46;
    private int totalstars = 0;
    private int enemyingame = 0;
    private int maxenemy = 3;

    public int ct = 50;
    private int t = 0;

    public boolean bosslevel = false;
    public boolean message = true;

    AudioStream b1, b2, b3, b4, b5, b6, b7, b8, b9 = null;
    InputStream bgm1 = null, bgm2 = null, bgm3 = null, bgm4 = null, bgm5 = null, bgm6 = null, bgm7 = null, bgm8 = null, bgm9 = null;

    public GDGamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        enemy = new Enemy(7, 10);
        score = new Score();
        bullet = new Bullet(0, 0, 0);
        drawexplosion = new DrawExplosion();
        boss = new Boss();
        timer = new Timer(delay, this);
        e = new ArrayList<MoveEnemy>();
        MoveEnemy MVE = new MoveEnemy();
        e.add(MVE);
        ds = new ArrayList<DrawStars>();
        DrawStars dsr = new DrawStars();
        ds.add(dsr);
        b = new ArrayList<Bullet>();
        timer.start();
        /*
        try {
            ship = ImageIO.read(new File("src/GDPictures/Ship1.png"));
            bgm1 = new FileInputStream(new File("src/GDSound/enemy_killed.wav"));
            bgm2 = new FileInputStream(new File("src/GDSound/explosion_playership.wav"));
            bgm3 = new FileInputStream(new File("src/GDSound/flying_enemy.wav"));
            bgm4 = new FileInputStream(new File("src/GDSound/Galaga_Coin_Sound_Effect.wav"));
            bgm5 = new FileInputStream(new File("src/GDSound/Galaga_Firing_Sound_Effect.wav"));
            bgm6 = new FileInputStream(new File("src/GDSound/Galaga_Level_Start_Sound_Effect.wav"));
            bgm7 = new FileInputStream(new File("src/GDSound/Galaga_Theme_Song.wav"));
            bgm8 = new FileInputStream(new File("src/GDSound/game_over.wav"));
            bgm9 = new FileInputStream(new File("src/GDSound/mothership.wav"));
            b1 = new AudioStream(bgm1);
            b2 = new AudioStream(bgm2);
            b3 = new AudioStream(bgm3);
            b4 = new AudioStream(bgm4);
            b5 = new AudioStream(bgm5);
            b6 = new AudioStream(bgm6);
            b7 = new AudioStream(bgm7);
            b8 = new AudioStream(bgm8);
            b9 = new AudioStream(bgm9);
        } catch (IOException e) {
            System.out.println("Can't find image or bgm");
            e.printStackTrace();
        }
    */
        try {
            ship = ImageIO.read(new File("src/GDPictures/Ship1.png"));
        } catch (IOException e) {
            System.out.println("Can't find image or bgm");
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        final double x = System.currentTimeMillis();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 800);
        enemy.draw((Graphics2D) g);
        score.paint(g);
        if (message) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Press Space to Start", 210, 450);
        }
        if (gamestart) {
            checklife();
            playermove();
            fire();
            addenemybullet();
            addstars();
            
            //Draw Bullet
            b.forEach((bt)->{
                bt.paint((Graphics2D) g);
            });
            //moving enemy paint
            for (MoveEnemy MVE : e) {
                MVE.paint((Graphics2D) g, PXpos, PYpos);
                if (setEnemy < Level && gamestart) {
                    System.out.println("setEnemy = " + setEnemy);
                    System.out.println("Level = " + Level);
                    setEnemy++;
                    RNGE();
                    e.remove(MVE);
                }
            }
            //draw stars
            for (int m = 0; m < ds.size(); m++) {
                ds.get(m).paint(g);
            }

            drawexplosion.paint(g);
            if (bosslevel) {
                boss.paint(g);
            }
        }
        
        if (lose) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER", 200, 400);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Press Space to Play Again", 210, 450);
        }
        if (win) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Win", 230, 400);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Press Space to Continue", 210, 450);
        }
        clearstuff();
        g.drawImage(ship, PXpos, PYpos, 30, 30, null);
        g.dispose();
        final double y = System.currentTimeMillis();
        System.out.println("Paint run time: " + (y-x));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamestart = true;
            message = false;
            fire = true;
            if (lose || win) {
                reset();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            ML = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            MR = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            ML = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            MR = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent m) {
        timer.start();
        final double starttime = System.currentTimeMillis();
        if (gamestart) {
            int EWidth = enemy.hspace;
            int EHeight = enemy.vspace;
            Rectangle player = new Rectangle(PXpos, PYpos, 27, 27);

            A: for (int i = 0; i < enemy.enemy.length; i++) {
                for (int j = 0; j < enemy.enemy[0].length; j++) {
                    int EX = j * enemy.hspace + 80 + enemy.move;
                    int EY = i * enemy.vspace + 10 + enemy.move;
                    Rectangle rect = new Rectangle(EX, EY, EWidth - 8, EHeight - 8);
                    for (int c = 0; c < b.size(); c++) {
                        int BX = b.get(c).Xpos;
                        int BY = b.get(c).Ypos;
                        Rectangle bbt = new Rectangle(BX, BY, 3, 5);
                        //Bullet type 1
                        switch (b.get(c).bt) {
                            case 1:
                                if (enemy.enemy[i][j] == 1) {
                                    if (bbt.intersects(rect)) {
                                        enemy.setEnemyValue(0, i, j);
                                        System.out.println("Hit static enemy");
                                        b.remove(c);
                                        score.setScore(sentscore(i, 1));
                                        Enemynumber--;
                                        availableenemy--;
                                        System.out.println(availableenemy);
                                        if (Enemynumber > 0 && availableenemy > 1 && enemyingame < maxenemy) {
                                            RNGE();
                                        }
                                        drawexplosion.receivetype(EX, EY, 1, i);
                                        //AudioPlayer.player.start(b1);
                                        break A;
                                    }
                                } else if (enemy.enemy[i][j] == 2) {
                                    for (int k = 0; k < e.size(); k++) {
                                        int EXm = e.get(k).EX;
                                        int EYm = e.get(k).EY;
                                        Rectangle movingE = new Rectangle(EXm, EYm, EWidth - 8, EHeight - 8);
                                        if (bbt.intersects(movingE)) {
                                            enemy.setEnemyValue(0, i, j);
                                            System.out.println("Hit moving enemy");
                                            b.remove(c);
                                            e.remove(k);
                                            enemyingame--;
                                            if (Enemynumber > 3 && availableenemy > 3 && enemyingame < maxenemy - 1) {
                                                System.out.println(availableenemy);
                                                RNGE();
                                                RNGE();
                                            }
                                            drawexplosion.receivetype(EXm, EYm, 1, i);
                                            Enemynumber--;
                                            score.setScore(sentscore(i, 2));
                                            break A;
                                        }
                                    }
                                } else if (bosslevel) {
                                    int BSXp = boss.Xpos;
                                    int BSYp = boss.Ypos;
                                    Rectangle BOSS = new Rectangle(BSXp, BSYp, 240, 90);
                                    if (bbt.intersects(BOSS)) {
                                        boss.HP -= 20;
                                        b.remove(c);
                                        drawexplosion.receivetype(BX, BY - 10, 1, 0);
                                        System.out.println("Hit Boss");
                                        break A;
                                    }
                                }
                                break;
                            //Bullet type 2        
                            case 2:
                                if (bbt.intersects(player)) {
                                    score.setLife();
                                    b.remove(c);
                                    System.out.println("Bullet Hit player");
                                    drawexplosion.receivetype(PXpos, PYpos, 2, 0);
                                    break A;
                                }
                                break;
                            //Bullet type 3
                            case 3:
                                Rectangle bossBullet = new Rectangle(BX, BY, 3, 8);
                                if (bossBullet.intersects(player)) {
                                    score.setLife();
                                    b.remove(c);
                                    System.out.println("Boss Bullet Hit player");
                                    drawexplosion.receivetype(PXpos, PYpos, 2, 0);
                                    break A;
                                }   break;
                            default:
                                break;
                        }
                    }
                }
            }

            for (int k = 0; k < e.size(); k++) {
                int EXm = e.get(k).EX;
                int EYm = e.get(k).EY;
                int Ii = e.get(k).Ii;
                int Ji = e.get(k).Ji;
                Rectangle movingE = new Rectangle(EXm, EYm, EWidth - 8, EHeight - 8);
                if (player.intersects(movingE)) {
                    enemy.setEnemyValue(0, Ii, Ji);
                    System.out.println("Got HIT");
                    score.setLife();
                    enemyingame--;
                    if (score.life > 0 && Enemynumber > 0 && availableenemy > 1 && enemyingame < maxenemy) {
                        RNGE();
                    }
                    drawexplosion.receivetype(PXpos, PYpos, 2, 0);
                    Enemynumber--;
                    e.remove(k);
                    break;
                }
            }
            repaint();
        }
        final double endtime = System.currentTimeMillis();
        System.out.println("CD run time " + (endtime-starttime));
    }

    public void fire() {
        if (fire) {
            if (cooldown == ct - 10) {
                Bullet BL = new Bullet(PXpos + 13, 690, 1);
                b.add(BL);
                cooldown--;
            } else {
                cooldown--;
            }
        } else {
            if (cooldown != ct - 10) {
                cooldown--;
                fire = false;
            }
        }
        if (cooldown == 0) {
            cooldown = ct;
        }
    }

    public void RNGE() {
        int i = 0;
        int j = 0;
        int X;
        int Y;
        do {
            i = ThreadLocalRandom.current().nextInt(0, 7);
            j = ThreadLocalRandom.current().nextInt(0, 10);
        } while (enemy.enemy[i][j] != 1);
        enemy.enemy[i][j] = 2;
        X = j * enemy.hspace + 80 + enemy.move;
        Y = i * enemy.vspace + 10;
        MoveEnemy MVE = new MoveEnemy();
        e.add(MVE);
        System.out.println("New Enemy Check");
        MVE.setXY(j, i, X, Y);
        System.out.println(i + "," + j);
        availableenemy--;
        enemyingame++;
    }

    public void playermove() {
        if (ML && PXpos > 10) {
            PXpos -= 5;
        } else if (MR && PXpos < 570) {
            PXpos += 5;
        }
    }

    public int sentscore(int i, int f) {
        if (f == 1) {
            switch(i) {
                case 1:
                    i = 60;
                    break;
                case 2:
                    i = 50;
                    break;
                case 3:
                    i = 40;
                    break;
                case 4:
                case 5:
                case 6:
                    i = 30;
                    break;
            }
        } else if (f == 2) {
            switch(i) {
                case 1:
                    i = 120;
                    break;
                case 2:
                    i = 100;
                    break;
                case 3:
                    i = 80;
                    break;
                case 4:
                case 5:
                case 6:
                    i = 60;
                    break;
            }
        }
        return i;
    }

    public void checklife() {
        //Loss condition
        if (score.life == 0) {
            e.clear();
            gamestart = false;
            lose = true;
            System.out.println("Moving Enemy number " + e.size());
            System.out.println("Moving Bullet number " + b.size());
        }
        //Win || go to boss level
        if (Enemynumber == 0) {
            int i = ThreadLocalRandom.current().nextInt(0, 5);
            if (i < 4 && !bosslevel) {
                boss();
                System.out.println("boss level");
            } else if (!bosslevel) {
                gamestart = false;
                win = true;
            }
        }
        //Boss level win condition
        if (bosslevel && boss.HP <= 0) {
            gamestart = false;
            win = true;
            score.playerscore += 1000;
        }
    }

    public void reset() {
        enemy = new Enemy(7, 10);
        e.clear();
        b.clear();
        RNGE();
        if (lose) {
            score.life = 3;
            score.playerscore = 0;
            maxenemy = 3;
            score.Level = 1;
            b.clear();
        }
        if (win) {
            if (maxenemy < 7) {
                maxenemy++;
            }
            score.Level++;
        }
        Enemynumber = 46;
        availableenemy = 46;
        enemyingame = 0;
        bosslevel = false;
        lose = false;
        win = false;
        boss.reset();
    }

    public void addstars() {
        if (t == 1) {
            if (totalstars < 10) {
                DrawStars drawstars = new DrawStars();
                drawstars.setXY();
                ds.add(drawstars);
                totalstars++;
            }
        }
        if (t == 20) {
            t = 0;
        }
        t++;
    }

    public void clearstuff() {
        for (int g = 0; g < b.size(); g++) {
            if (b.get(g).Ypos < 0 || b.get(g).Ypos > 800) {
                if (b.get(g).bt == 3) {
                    boss.bulletn--;
                }
                b.remove(g);
            }
        }
        for (int k = 0; k < ds.size(); k++) {
            if (ds.get(k).Y > 800) {
                ds.remove(k);
                totalstars--;
            }
        }
    }

    public void addenemybullet() {
        for (int i = 0; i < e.size(); i++) {
            if (e.get(i).fire) {
                Bullet blt = new Bullet(e.get(i).EX, e.get(i).EY, 2);
                b.add(blt);
                e.get(i).fire = false;
            }
        }
        if (boss.firebullet) {
            Bullet blt = new Bullet(boss.Xpos + 120, boss.Ypos + 90, 3);
            b.add(blt);
            boss.bulletn++;
        }
    }

    public void boss() {
        enemy.clearforboss();
        bosslevel = true;
    }
}
