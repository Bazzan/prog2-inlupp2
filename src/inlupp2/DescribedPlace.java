package inlupp2;

import java.awt.*;
import java.io.Serializable;

public class DescribedPlace extends Place implements Serializable {

    private String description;

    public DescribedPlace(String name, String description, Position position, Color c) {
        super(name, position, c);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBounds(getX(),getY(),110,210);
        if (getShow() && getMarked()) {
            String[] lines = description.split("\\n");

            int newline = g.getFont().getSize() + 5;
            int z = 20;
            g.setColor(Color.RED);
			g.drawRect(0, 0, 100, 200);
            g.drawString(getName().toString(),22,20);

            for (int i = 0; i< lines.length; i++) {
                g.drawString(lines[i], 22, z+=newline);
            }
        }

        if (getMarked() && !getShow()) {

            g.setColor(Color.RED);
            g.drawRect(0, 0, 22, 22);

        }

        if (getShow() && !getMarked()) {
            String[] lines = description.split("\\n");

            int newline = g.getFont().getSize() + 5;
            int z = 20;
            g.setColor(Color.BLUE);
            g.drawRect(0, 0, 100, 200);
            g.drawString(getName().toString(),22,20);

            for (int i = 0; i< lines.length; i++) {
                g.drawString(lines[i], 22, z+=newline);
            }
        }
    }

    @Override
    public String toString() {
        return "" + getName() + "\n" + description;
    }

}
