package inlupp2;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JComponent {
	Position p;
	Color c;
	
	public Triangle(Position p, Color c){
		this.p=p;
		this.c=c;
		
		setBounds(p.getX(), p.getY(), 70, 70);
		Dimension d = new Dimension(70, 70);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int x=p.getX();
		int y=p.getX();
		int[] xP={x, x+30, x+50};
		int[] yP={y, y+100, y};
		
		g.setColor(c);
		//g.fillRect(0,0,getWidth(), getHeight());
		g.fillPolygon(xP, yP, 3);
		
	}
	
	/*public void setPos(Position p){
		this.p=p;
	}
	
	public void setCol(Color c){
		this.c=c;
	}*/
	
}
