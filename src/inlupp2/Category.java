package inlupp2;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private String name;
    private Color color;
    private ArrayList<Place> places = new ArrayList<Place>();
    private boolean visible = false;

    public Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void addPlace(Place n) {
        places.add(n);
    }

    public void removePlace(Place n) {
        places.remove(n);
    }

    public void setVisible(boolean v) {
        for (Place p : places) {
            if (!v){
            	p.hidePlace();
            }
            
            else{
            	if(!p.getShow()){
            		p.showPlace();
            	}
            }

        }

    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

}
