package inlupp2;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public abstract class Place extends JComponent implements Serializable{
	
	private String name;
	private Position position;
	private Triangle t;
	public boolean marked=false;
	public boolean show=false;
	
	
	public Place(String name, Position position, Color c){
		this.name=name;
		this.position=position;
		t = new Triangle(position, c);
		
		setBounds((position.getX())-20, position.getY()-40, 150, 150);
		
		Dimension d = new Dimension(140, 140);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);	
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		
		if (show && marked){

			g.setColor(Color.RED);
			g.drawRect(0, 0, 100, 80);
		
			g.drawString(toString(), 22, 20);	//Behöver kunna ritas på olika rader vid DescribedPlace
			
		}
		
		if (marked && !show) {

			g.setColor(Color.RED);
			g.drawRect(0,0,39, 39);
			
			//g.drawString(name, 10, 10);
		}
		
		if (show && !marked){
						
			g.setColor(Color.BLUE);
			g.drawRect(0, 0, 100, 80);
			g.drawString(toString(), 22, 20);
			
		}
		
		
	}
	
	public String getName(){return name;}
	
	public Position getPosition(){return position;}
	
	public Triangle getTriangle(){ return t;}
	
	public void setVisible(boolean b){ t.setVisible(b);}
	
	public void setShow(boolean b){show=b;}
	
	public boolean getShow(){return show;}
	
	public void setMarked(boolean mark){ marked=mark;}
	
	public boolean getMarked(){ return marked;}
	@Override
	public String toString(){
		return name;
	}
}
