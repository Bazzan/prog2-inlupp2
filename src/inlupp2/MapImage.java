package inlupp2;

import javax.swing.*;

import java.awt.*;
import java.io.File;

public class MapImage extends JPanel {

    private ImageIcon i;

    public MapImage(File f) {
    	
    	i = new ImageIcon(f.getAbsolutePath());
    	
       /* Toolkit tk = Toolkit.getDefaultToolkit();
        i = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());*/
     
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i.getImage(), 0, 0, this);
    }

   /* public int getWidth() {
        return i.getImage.getWidth(null);
    }

    public int getHeight() {
        return i.getHeight(null);
    }*/
}
