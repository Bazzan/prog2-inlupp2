package inlupp2;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Triangle extends JComponent implements Serializable{
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
	public void setVisible(boolean b){
		visible=b;
	}
	
	public boolean getVisible(){
		return visible;
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (visible){
			int[] xP={0, 20, 40};
			int[] yP={0, 40, 0};

			g.setColor(c);
			g.fillPolygon(xP, yP, 3);
		}
	}
}
