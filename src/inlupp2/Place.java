package inlupp2;

import javax.swing.*;

import java.awt.*;

public abstract class Place extends JComponent{
	
	private String name;
	private Position position;
	private Triangle t;
	public boolean marked=false;
	
	
	public Place(String name, Position position, Color c){
		this.name=name;
		this.position=position;
		t = new Triangle(position, c);
		
		setBounds((position.getX())-20, position.getY()-40, 40, 40);
		
		Dimension d = new Dimension(40, 40);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);	
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		if (marked) {
			g.setColor(Color.RED);
			g.drawRect(0,0,39, 39);
			
			//g.drawString(name, 10, 10);
		} 
	}
	
	public String getName(){return name;}
	
	public Position getPosition(){return position;}
	
	public Triangle getTriangle(){ return t;}
	
	public void setVisible(boolean b){ t.setVisible(b);}
	
	public void setMarked(boolean mark){ marked=mark;}
}
