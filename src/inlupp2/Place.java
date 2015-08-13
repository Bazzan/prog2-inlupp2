package inlupp2;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public abstract class Place extends JComponent implements Serializable {

    private String name;
    private Position position;
    private Triangle t;
    private boolean marked = false;
    private boolean show = false;


    public Place(String name, Position position, Color c) {
        this.name = name;
        this.position = position;
        t = new Triangle(position, c);

        setBounds((position.getX()) - 11, position.getY() - 21, 150, 150);

        Dimension d = new Dimension(140, 140);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }

    @Override


    public String getName() {
        return name;

    }

    public Position getPosition() {
        return position;
    }

    public Triangle getTriangle() {
        return t;
    }

    public void setVisible(boolean b) {
        if (b) {
            if (!getShow() && !t.getVisible()) {
                t.setVisible(b);
            } else if (getShow() && !t.getVisible()) {
                t.setVisible(b);
                setShow(false);
            }
        } else {
            t.setVisible(b);
//            setShow(false);
        }
    }

    public void setShow(boolean b) {
        show = b;
    }

    public boolean getShow() {
        return show;
    }

    public void setMarked(boolean mark) {
        marked = mark;
    }

    public boolean getMarked() {
        return marked;
    }

    @Override
    public String toString() {
        return name;
    }
}
