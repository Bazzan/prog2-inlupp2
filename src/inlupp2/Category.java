package inlupp2;
import java.awt.Color.*;
import java.awt.*;
import java.util.ArrayList;

public class Category {
	
	private String name;
	private Color color;
	private ArrayList<Place> places = new ArrayList<Place>();
	
	public Category(String name, Color color){
		this.name=name;
		this.color=color;
	}
	
	public void addPlace(Place n){
		places.add(n);
	}
	

	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}

}
