package inlupp2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JComponent;

public class MapImage extends JComponent{
	
		private Image i;
		
		public MapImage(File f){
			Toolkit tk = Toolkit.getDefaultToolkit();
			i = tk.getImage(f.getAbsolutePath());
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(i, 0, 0, this);
		}
	
}
