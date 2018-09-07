package galactic.destruction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;

public class GalacticDestruction {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GDGamePlay gdgp = new GDGamePlay();
        obj.setBounds(300, 10, 600, 800);
        obj.setTitle("Galatic Desctruction");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gdgp);
    }
}
