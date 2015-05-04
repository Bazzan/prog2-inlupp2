package inlupp2;

import java.awt.*;
import java.io.Serializable;

public class NamedPlace extends Place implements Serializable {


    public NamedPlace(String name, Position position, Color c) {
        super(name, position, c);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (getShow() && getMarked()) {

            g.setColor(Color.RED);
            g.drawRect(0, 0, 100, 80);

            g.drawString(getName().toString(), 22, 20);

        }

        if (getMarked() && !getShow()) {

            g.setColor(Color.RED);
            g.drawRect(0, 0, 22, 22);

        }

        if (getShow() && !getMarked()) {

            g.setColor(Color.BLUE);
            g.drawRect(0, 0, 100, 80);
            g.drawString(toString(), 22, 20);


        }
    }


}
