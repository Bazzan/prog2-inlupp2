package inlupp2;

import javax.swing.*;

import java.awt.*;
import java.io.*;

public class MapImage extends JPanel implements Serializable{

    private ImageIcon imgIcn;

    public MapImage(File f) {
        imgIcn = null;
        imgIcn = new ImageIcon(f.getAbsolutePath());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgIcn.getImage(), 0, 0, this);
    }

    public int getWidth() {
        return imgIcn.getImage().getWidth(null);
    }

    public int getHeight() {
        return imgIcn.getImage().getHeight(null);
    }
}
