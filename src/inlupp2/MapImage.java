package inlupp2;

import javax.swing.*;

import java.awt.*;
import java.io.*;

public class MapImage extends JPanel implements Serializable{

    private ImageIcon i;

    public MapImage(File f) {
        i = new ImageIcon(f.getAbsolutePath());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i.getImage(), 0, 0, this);
    }

    public int getWidth() {
        return i.getImage().getWidth(null);
    }

    public int getHeight() {
        return i.getImage().getHeight(null);
    }
}
