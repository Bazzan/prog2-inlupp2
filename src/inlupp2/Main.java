package inlupp2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;

class Main extends JFrame {
    int x = 10;						//testattribut
    int y = 80;
    Color c = Color.BLUE;
    Category cA = new Category("Kyrkor", Color.CYAN);
    ArrayList<Category> catArr= new ArrayList<Category>();
    
    //Triangle t = new Triangle();	
    JComponent map;
    
    HashMap<String, NamedPlace> h = new HashMap<>();
    HashMap<Position, NamedPlace> h2 = new HashMap<>();
    

    DefaultListModel model = new DefaultListModel();
    ArrayList<Category> categories = new ArrayList<Category>();
    JList<String> categoryList = new JList(model);
    
    JPanel jp = new JPanel();


    Main() {
    	catArr.add(cA); //testkategori
    	String[] cats = {"Kyrkor", "Portaler", "Skolor", "Skulpturer", "Moskéer", "Butiker"};
    	for (int i=0; i<cats.length; i++){
    		model.add(i, cats[i]);
    	}


			/* ---------- Arkiv-meny ------------*/
        setLayout(new BorderLayout());
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        JPanel northPanel = new JPanel();

        JLabel newLabel = new JLabel("Ny:");                    //Skapa nya ställen. Behöver actionlisteners
        String[] newString = {"NamedPlace", "DescribedPlace"};
        JComboBox newBox = new JComboBox(newString);
        newBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
            	 JComboBox comboBox =(JComboBox) a.getSource();
              	 Object selected = comboBox.getSelectedItem();
            	 newPlace(selected.toString());
            	
           }});
        
        northPanel.add(newLabel);
        northPanel.add(newBox);

        JTextField searchField = new JTextField("Search", 12);                //Sök efter ställen eller beskrivning, behöver AL
        northPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        northPanel.add(searchButton);

        JButton hideButton = new JButton("Hide places");
        northPanel.add(hideButton);
        JButton deletePlaces = new JButton("Delete places");
        northPanel.add(deletePlaces);
        JButton wihButton = new JButton("What is here?");
        northPanel.add(wihButton);


        add(northPanel, BorderLayout.NORTH);

			/* ---------- Panel för kategorier ------------*/

        JPanel eastPanel = new JPanel();

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JLabel categories = new JLabel("Categories");
        eastPanel.add(categories);

        JScrollPane cScroll = new JScrollPane(categoryList);
        categoryList.setVisibleRowCount(10);                        //Lista över kategorier
        categoryList.setFixedCellWidth(75);
        eastPanel.add(cScroll);


        JButton hideC = new JButton("Hide category");        //Knappar
        eastPanel.add(hideC);

        JButton newC = new JButton("New category");
        eastPanel.add(newC);
        newC.addActionListener(new newCListener());

        JButton deleteC = new JButton("Delete category");
        eastPanel.add(deleteC);

        add(eastPanel, BorderLayout.EAST);
        setVisible(true);
        
        

    }

    public void newMap() {        //Öppnar bildfil för ny karta
        JFileChooser jfc = new JFileChooser("user.dir");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
        jfc.addChoosableFileFilter(fnef);

        int answer = jfc.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            Image img = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());
            JLabel jl = new JLabel();
            
            jl.setIcon(new ImageIcon(img));
            jp.add(jl);
            add(jp, BorderLayout.CENTER);
            pack();
            setVisible(true);
            validate();
            repaint();
            setLocationRelativeTo(null);
        }
    }

    public void newPlace(final String type) {
    	 
    	System.out.println(type);
    	jp.addMouseListener(new MouseListener(){
        	public void mousePressed(MouseEvent e){}
        	
        	public void mouseReleased(MouseEvent e){}
        	
        	public void mouseEntered(MouseEvent e){
        		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));}
        	
        	public void mouseExited(MouseEvent e){
        		setCursor(Cursor.getDefaultCursor());
        	}
        	
        	public void mouseClicked(MouseEvent e){
        		System.out.println("Klickat");
        		Position p = new Position(e.getX(), e.getY());
        		String name;
              	if(type.equals("NamedPlace")){
              		name=JOptionPane.showInputDialog("Namn:");	//skapa ny namedPlace
              		NamedPlace n = new NamedPlace(name);
              		h.put(name, n);
              		h2.put(p, n);
              		if (categoryList.getSelectedValue()!=null){     //Förutsätter att index är samma för bägge
              			catArr.get(categoryList.getSelectedIndex()).addPlace(n);
              			System.out.println("Hittade categoryList");
              		}
              		//Här måste vi lägga in i kategori eller liknande, samt måla upp en triangel
              	}
              	else if (type.toString().equals("DescribedPlace")){
              																	//skapa ny describedPlace
              	}
              	setCursor(Cursor.getDefaultCursor());
              	jp.removeMouseListener(this);
        	}
        });
     	
      	

    }
    
    public void drawTriangle(int x, int y){
    	/*Triangle t = new Triangle(x, y);
    	jp.add(t);
    	jp.validate();
    	jp.repaint();
    	 System.out.println(x + " och " + y);*/
    	 
    }

    class newListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            newMap();
        }
    }

    
    /*---------- PlatsLyssnare -----------------*/
    class boxListen implements ActionListener {
        public void actionPerformed(ActionEvent a) {
        	
        }
       }
    
		/*-------- Kategorilyssnare --------*/

    class newCListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {

            JLabel l = new JLabel("Name:");    //textinput
            JTextField t = new JTextField(8);
            JPanel p = new JPanel();
            p.add(l, BorderLayout.WEST);
            p.add(t, BorderLayout.WEST);

            JColorChooser cc = new JColorChooser();    //Färgval
            JPanel ccp = new JPanel();
            ccp.add(cc, BorderLayout.WEST);

            JPanel form = new JPanel();

            BorderLayout b = new BorderLayout();
            //form.add(p, BorderLayout.NORTH);
            //form.add(c, BorderLayout.SOUTH);

            form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
            form.add(p);
            form.add(ccp);

            int answer = JOptionPane.showConfirmDialog(null, form, "New category", JOptionPane.OK_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                String n = t.getText();
                Color c = cc.getColor();

                Category cat = new Category(n, c);
                categories.add(cat);
                model.add(model.size(), n);
                //måste lägga in namnet i listan och koppla till objektet
            }
        }
    }

    

    public static void main(String[] args) {
        new Main();
    }
}
