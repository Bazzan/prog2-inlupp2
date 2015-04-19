package inlupp2;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JComponent {

	protected void paintComponent(Graphics g){
		int[] xP={20, 50, 80};
		int[] yP={50, 150, 50};
		g.fillPolygon(xP, yP, 3);
		g.setColor(Color.BLUE);
	}
}
