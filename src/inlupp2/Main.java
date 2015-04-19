package inlupp2;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import javax.swing.filechooser.*;

 class Main extends JFrame {
	int x=10;
	int y=80;
	Color c = Color.BLUE;
	Triangle t = new Triangle();
	JComponent map;
	JPanel mapPanel = new JPanel();
	
	
		Main(){
			
			//Meny
			JMenuBar mbar = new JMenuBar();
			setJMenuBar(mbar);
			
			JMenu archMenu = new JMenu("Archive");
			mbar.add(archMenu);
			
			JMenuItem newMap = new JMenuItem("New map"); //Öppna bildfil
			newListener n = new newListener();
			newMap.addActionListener(n);
			archMenu.add(newMap);

			
			JMenuItem openItem = new JMenuItem("Open");
			archMenu.add(openItem);
			
			JMenuItem saveItem = new JMenuItem("Save");
			archMenu.add(saveItem);
			
			JMenuItem exitProg = new JMenuItem("Exit");
			archMenu.add(exitProg);
			
			
			
			
			
			setSize(600, 500);
			setVisible(true);
			add(t);
			
		}
		
		public void newMap(){		//Öppnar bildfil för ny karta
			JFileChooser jfc = new JFileChooser("user.dir");
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
			jfc.addChoosableFileFilter(fnef);
			
			int	 answer =  jfc.showOpenDialog(null);
			if (answer == JFileChooser.APPROVE_OPTION){
				File f = jfc.getSelectedFile();
				MapImage m = new MapImage(f);
				add(m);
				//behöver läggas till JPanel istället

				validate();
				repaint();
			}
		}
		
	
		
		class newListener implements ActionListener{
			public void actionPerformed(ActionEvent a){
				newMap();
			}
		}


		
		
		
	public static void main(String[] args){
		new Main();
	}	
}
