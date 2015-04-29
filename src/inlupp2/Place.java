package inlupp2;

import javax.swing.*;
import java.awt.*;

public abstract class Place {
	
	private String name;
	private Position position;
	private Triangle t;
	
	
	public Place(String name, Position position, Color c){
		this.name=name;
		this.position=position;
		t = new Triangle();
		t.setCol(c);
		t.setPos(position);
	}
	
	public String getName(){
		return name;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public Triangle getTriangle(){
		return t;
	}
	
	public void setVisible(boolean b){
		t.setVisible(b);
	}


}
