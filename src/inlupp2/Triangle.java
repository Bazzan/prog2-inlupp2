package inlupp2;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JComponent {
	int[] xP={20, 50, 80};
	int[] yP={50, 150, 50};
	
	@Override
	protected void paintComponent(Graphics g){
		
		g.fillPolygon(xP, yP, 3);
		g.setColor(Color.BLUE);
	}
	
	public Triangle(int x, int y){
		int[] xP={x, x+30, x+50};
		int[] yP={y, y+100, y};
		
	}
	
	
}
