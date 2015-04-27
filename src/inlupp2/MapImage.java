package inlupp2;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MapImage extends JComponent {

    private Image i;

    public MapImage(File f) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        i = tk.getImage(f.getAbsolutePath());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i, 0, 0, this);
    }

    public int getWidth() {
        return i.getWidth(null);
    }

    public int getHeight() {
        return i.getHeight(null);
    }
}
