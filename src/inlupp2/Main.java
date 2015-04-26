package inlupp2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

class Main extends JFrame {
    int x = 10;
    int y = 80;
    Color c = Color.BLUE;
    Triangle t = new Triangle();
    JComponent map;
    //	JPanel northPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel mapPanel = new JPanel();
    //	JPanel categoryPanel = new JPanel();
    String[] cats = {"Kyrkor", "Portaler", "Skolor", "Skulpturer", "Moskéer", "Butiker"};
    ArrayList<Category> categories = new ArrayList<Category>();
    JList<String> categoryList = new JList(cats);


    Main() {

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


//			eastPanel.setMaximumSize(new Dimension(150, 250));

//			mapPanel.setPreferredSize(new Dimension(400, 350));
//			southPanel.add(mapPanel, BorderLayout.WEST);
//			southPanel.add(eastPanel, BorderLayout.EAST);


//			add(southPanel, BorderLayout.SOUTH);
//			pack();

        //setSize(850, 500);

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
            MapImage m = new MapImage(f);
            Dimension imgSize = new Dimension(m.getWidth(), m.getHeight());
            m.setPreferredSize(imgSize);
            add(m, BorderLayout.CENTER);
            pack();
            setVisible(true);
            validate();
            repaint();
        }
    }

    public void newPlace() {

    }

    class newListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            newMap();
        }
    }

    class newPlaceListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            newPlace();
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
                //måste lägga in namnet i listan och koppla till objektet
            }
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}
