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
	JPanel northPanel = new JPanel();
	JPanel southPanel = new JPanel();
	JPanel mapPanel = new JPanel();
	JPanel categoryPanel = new JPanel();
	String[]cats = {"Kyrkor", "Portaler", "Skolor", "Skulpturer", "Moskéer", "Butiker"};
	ArrayList <Category> categories = new ArrayList<Category>();
	JList<String> categoryList = new JList(cats);
	
	
		Main(){
			
			/* ---------- Arkiv-meny ------------*/
			setLayout(new FlowLayout());

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
			
			/* ---------- Panel för skapande och sökande av ställen ------------*/
			
			JPanel newPanel = new JPanel();			
			
			JLabel newLabel = new JLabel("Ny:");					//Skapa nya ställen. Behöver actionlisteners
			String[] newString = {"NamedPlace", "DescribedPlace"};
			JComboBox newBox = new JComboBox(newString);
			newPanel.add(newLabel);
			newPanel.add(newBox);
			
			JTextField searchField = new JTextField("Search", 12);				//Sök efter ställen eller beskrivning, behöver AL
			newPanel.add(searchField);
			JButton searchButton = new JButton("Search");
			newPanel.add(searchButton);
			
			JButton hideButton = new JButton("Hide places");
			newPanel.add(hideButton);
			JButton deletePlaces = new JButton("Delete places");
			newPanel.add(deletePlaces);
			JButton wihButton = new JButton("What is here?");
			newPanel.add(wihButton);
			
			
			northPanel.add(newPanel, BorderLayout.WEST);
			add(northPanel, BorderLayout.NORTH);
			
			/* ---------- Panel för kategorier ------------*/
			
			categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
			
			JScrollPane cScroll = new JScrollPane(categoryList);
			categoryList.setVisibleRowCount(10); 						//Lista över kategorier
			categoryList.setFixedCellWidth(75);
			categoryPanel.add(cScroll);

			

			JButton hideC = new JButton ("Hide category");		//Knappar
			categoryPanel.add(hideC);
			
			JButton newC = new JButton("New category");
			categoryPanel.add(newC);
			newC.addActionListener(new newCListener());

			JButton deleteC = new JButton("Delete category");
			categoryPanel.add(deleteC);
			
			
			categoryPanel.setMaximumSize(new Dimension(150, 250));
			
			mapPanel.setPreferredSize(new Dimension(400, 350));
			southPanel.add(mapPanel, BorderLayout.WEST);
			southPanel.add(categoryPanel, BorderLayout.EAST);
			
			
			add(southPanel, BorderLayout.SOUTH);
			pack();

			//setSize(850, 500);
			setVisible(true);
			
			
		}
		
		public void newMap(){		//Öppnar bildfil för ny karta
			JFileChooser jfc = new JFileChooser("user.dir");
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
			jfc.addChoosableFileFilter(fnef);
			
			int	 answer =  jfc.showOpenDialog(null);
			if (answer == JFileChooser.APPROVE_OPTION){
				File f = jfc.getSelectedFile();
				MapImage m = new MapImage(f);
				m.setPreferredSize(new Dimension(750, 750));
				add(m, BorderLayout.WEST);
				setSize(800, 800);

				validate();
				repaint();
			}
		}
		
		public void newPlace(){
			
		}

		class newListener implements ActionListener{ 
			public void actionPerformed(ActionEvent a){
				newMap();
			}
		}
		
		class newPlaceListener implements ActionListener{ 
			public void actionPerformed(ActionEvent a){
				newPlace();
			}
		}
		
		
		/*-------- Kategorilyssnare --------*/
		
		class newCListener implements ActionListener{ 
			public void actionPerformed(ActionEvent a){
				
				JLabel l = new JLabel("Name:"); 	//textinput
				JTextField t = new JTextField(8);
				JPanel p = new JPanel();
				p.add(l,BorderLayout.WEST);
				p.add(t,BorderLayout.WEST);
				
				JColorChooser cc = new JColorChooser(); 	//Färgval
				JPanel ccp = new JPanel();
				ccp.add(cc, BorderLayout.WEST);

				JPanel form = new JPanel();
				
				BorderLayout b= new BorderLayout();
				//form.add(p, BorderLayout.NORTH);
				//form.add(c, BorderLayout.SOUTH);
				
				form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
				form.add(p);
				form.add(ccp);
				
				int answer = JOptionPane.showConfirmDialog(null, form, "New category", JOptionPane.OK_CANCEL_OPTION);
				if (answer==JOptionPane.YES_OPTION){
					String n=t.getText();
					Color c = cc.getColor();
					
					Category cat = new Category(n, c);
					categories.add(cat);
					//måste lägga in namnet i listan och koppla till objektet
				}
			}
		}
		
		
	public static void main(String[] args){
		new Main();
	}	
}
