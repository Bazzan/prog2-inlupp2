package inlupp2;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JComponent {
	Position p;
	Color c;
	
	/*public Triangle(Position p, Color c){
		this.p=p;
		this.c=c;
		
	}*/
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int x=p.getX();
		int y=p.getX();
		int[] xP={x, x+30, x+50};
		int[] yP={y, y+100, y};
		
		
		g.fillPolygon(xP, yP, 3);
		g.setColor(c);
	}
	
	public void setPos(Position p){
		this.p=p;
	}
	
	public void setCol(Color c){
		this.c=c;
	}
	
}
