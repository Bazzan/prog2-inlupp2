package inlupp2;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JComponent {
	Position p;
	Color c;
	boolean visible = true;
	
	public Triangle(Position p, Color c){
		this.p=p;
		this.c=c;
		
		setBounds((p.getX()-20), p.getY()-40, 40, 40);
		Dimension d = new Dimension(40, 40);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int[] xP={0, 20, 40};
		int[] yP={0, 40, 0};
		if (visible) {

			g.setColor(c);
			//g.fillRect(0,0,getWidth(), getHeight());
			g.fillPolygon(xP, yP, 3);
		} else {

		}
	}
	
	/*public void setPos(Position p){
		this.p=p;
	}
	
	public void setCol(Color c){
		this.c=c;
	}*/
	
}
