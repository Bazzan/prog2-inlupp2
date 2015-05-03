package inlupp2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Main extends JFrame implements Serializable {

    Boolean change = false; //Om något görs i programmet
    Boolean named = false;

    Category c1 = new Category("Kyrkor", Color.CYAN);
    Category c2 = new Category("Butiker", Color.BLUE);
    Category c3 = new Category("Skultpurer", Color.GREEN);
    Category c4 = new Category("Portaler", Color.RED);
    Category c5 = new Category("Moskéer", Color.YELLOW);
    Category c6 = new Category("Skolor", Color.ORANGE);
    ArrayList<Category> catArr = new ArrayList<Category>();

    //slut på testattribut
    JComponent map;

    HashMap<Place, String> stringMap = new HashMap<>();
    HashMap<Position, Place> positionMap = new HashMap<>();
    HashMap<Boolean, Place> markMap = new HashMap<Boolean, Place>();

    MapListen mapListen = new MapListen();
    DefaultListModel<String> model = new DefaultListModel<String>();
    ArrayList<Category> categories = new ArrayList<Category>();
    JList<String> categoryList = new JList<String>(model);
    JTextField searchField = new JTextField("Search", 12);

    MapImage mapImg = null;


    Main() {
        catArr.add(c1); //testkategorier
        catArr.add(c2);
        catArr.add(c3);
        catArr.add(c4);
        catArr.add(c5);
        catArr.add(c6);

        for (int i = 0; i < 6; i++) {
            model.add(i, catArr.get(i).getName());
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
        newMapListener newMapListener = new newMapListener();
        newMap.addActionListener(newMapListener);
        archMenu.add(newMap);


        JMenuItem openItem = new JMenuItem("Open");
        newOpenListener openListener = new newOpenListener();
        openItem.addActionListener(openListener);
        archMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        archMenu.add(saveItem);

        JMenuItem exitProg = new JMenuItem("Exit");
        exitProg.addActionListener(new ExitListener());
        archMenu.add(exitProg);

			/* ---------- Panel för skapande och sökande av ställen ------------*/

        JPanel northPanel = new JPanel();

        JLabel newLabel = new JLabel("Ny:");                    //Skapa nya ställen. Behöver actionlisteners
        String[] newString = {"NamedPlace", "DescribedPlace"};
        JComboBox<String> newBox = new JComboBox<String>(newString);
        newBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                if (mapImg != null) {
                    JComboBox comboBox = (JComboBox) a.getSource();
                    Object selected = comboBox.getSelectedItem();

                    newPlace(selected.toString());
                    change = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Öppna/skapa karta först");
                }
            }
        });

        northPanel.add(newLabel);
        northPanel.add(newBox);

        //Sök efter ställen eller beskrivning, behöver AL
        northPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        northPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unMark();
                String find = searchField.getText();
                //Här behövs en metod för att plocka ut alla platser som innehåller söksträngen

                change = true;
            }
        });

        JButton hideButton = new JButton("Hide places");
        northPanel.add(hideButton);
        hideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unMark();
                for (Map.Entry<Place, String> mark : stringMap.entrySet()) {            //avmarkera alla platser
                    Place p = mark.getKey();
                    p.setVisible(false);
                    p.setMarked(false);
                    change = true;
                }
            }
        });

        JButton deletePlaces = new JButton("Delete places");
        northPanel.add(deletePlaces);
        JButton wihButton = new JButton("What is here?");
        northPanel.add(wihButton);

        JButton testOpen = new JButton("Testöppna");                //testa att läsa in fil
        northPanel.add(testOpen);
        testOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fis = new FileInputStream("test.k");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    mapImg = (MapImage) ois.readObject();
                    paintMap();
                    catArr=(ArrayList) ois.readObject();
                    ois.close();
                } catch (FileNotFoundException fnfe) {
                    System.err.println("Hittar ej filen");
                } catch (ClassNotFoundException cnfe) {
                    System.err.println("Hittar inte klassen");
                } catch (IOException ioe) {
                    System.err.println("Read error: " + ioe);
                }


                for (Category c : catArr) {
                    System.out.println(c.toString());
                    ArrayList<Place> a = c.getPlaces();
                    for (Place p : a) {
                        System.out.println(a.toString());
                    }
                }
            }

        });
        JButton testSave = new JButton("Testspara");                //testa att spara fil
        northPanel.add(testSave);
        testSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileOutputStream fos = new FileOutputStream("test.k");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(mapImg);
                    oos.writeObject(catArr);
                    //oos.writeObject(stringMap);
                    //oos.writeObject(positionMap);
                    //oos.writeObject(markMap);
                    oos.close();
                } catch (IOException ioe) {
                    System.err.println("Write error: " + ioe);
                }
            }
        });


        add(northPanel, BorderLayout.NORTH);

			/* ---------- Panel för kategorier ------------*/

        JPanel eastPanel = new JPanel();

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JLabel categories = new JLabel("Categories");
        eastPanel.add(categories);
        categoryList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                showCategory(categoryList.getSelectedValue());
            }
        });
        JScrollPane cScroll = new JScrollPane(categoryList);
        eastPanel.add(cScroll); //Lista över kategorier


        JButton hideC = new JButton("Hide category");        //Knappar
        eastPanel.add(hideC);
        hideC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = categoryList.getSelectedIndex();

                catArr.get(i).setVisible(false);
                validate();
                repaint();
                change = true;
            }
        });

        JButton newC = new JButton("New category");
        eastPanel.add(newC);
        newC.addActionListener(new newCListener());

        JButton deleteC = new JButton("Delete category");
        eastPanel.add(deleteC);
        deleteC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = categoryList.getSelectedIndex();
                System.out.println(i);
                model.remove(i);
                catArr.remove(i);
                change = true;
            }
        });


        add(eastPanel, BorderLayout.EAST);
        setVisible(true);
        pack();
    }

    public void newMap() {        //Öppnar bildfil för ny karta

        JFileChooser jfc = new JFileChooser("user.dir");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
        jfc.addChoosableFileFilter(fnef);

        int answer = jfc.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {
        	
            File f = jfc.getSelectedFile();

            mapImg = new MapImage(f);
            paintMap();
        }
    }
    
    public void paintMap(){
    	mapImg.setPreferredSize(new Dimension(mapImg.getWidth(), mapImg.getHeight()));
        mapImg.setLayout(null);
        mapImg.addMouseListener(mapListen);
        add(mapImg, BorderLayout.CENTER);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        change = true;
    }

    public void newPlace(final String type) {

        mapImg.removeMouseListener(mapListen);
        System.out.println(type);
        mapImg.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }

            public void mouseClicked(MouseEvent e) {
                System.out.println("Klickat");              //// Behöver break/cancel
                Position p = new Position(e.getX(), e.getY());
                System.out.println(e.getX() + " " + e.getY());
                String name;
                Color c = Color.BLACK;
                change = true;
                if (type.equals("NamedPlace")) {
                    name = JOptionPane.showInputDialog("Namn:");    //skapa ny namedPlace

                    NamedPlace n = new NamedPlace(name, p, c);

                    if (categoryList.getSelectedValue() != null) {     //Förutsätter att index är samma för bägge

                        int i = categoryList.getSelectedIndex();
                        c = catArr.get(i).getColor();
                        n = new NamedPlace(name, p, c);
                        stringMap.put(n, name);
                        positionMap.put(p, n);
                        n.setVisible(true);


                        catArr.get(i).addPlace(n);
                        System.out.println("Hittade categoryList");


                    } else {                                                        //Om kategori ej vald

                        stringMap.put(n, name);
                        positionMap.put(p, n);
                    }

                    mapImg.setLayout(null);
                    stringMap.put(n, name);
                    positionMap.put(p, n);
                    n.setVisible(true);
                    mapImg.add(n.getTriangle());
                    mapImg.add(n);
                    mapImg.validate();
                    mapImg.repaint();


                    //Här måste vi lägga in i kategori eller liknande, samt måla upp en triangel
                } else if (type.toString().equals("DescribedPlace")) {            //Denna och den ovan kan kortas ned
                    JPanel dp = new JPanel();
                    dp.setLayout(new BoxLayout(dp, BoxLayout.Y_AXIS));
                    JLabel nl = new JLabel("Namn:");
                    JTextField nt = new JTextField();
                    JLabel dl = new JLabel("Beskrivning:");
                    JTextArea dt = new JTextArea(3,1);
                    JScrollPane sp = new JScrollPane(dt);
                    nl.setAlignmentX(Component.LEFT_ALIGNMENT);
                    nt.setAlignmentX(Component.LEFT_ALIGNMENT);
                    dl.setAlignmentX(Component.LEFT_ALIGNMENT);
                    sp.setAlignmentX(Component.LEFT_ALIGNMENT);
                    dp.add(nl);
                    dp.add(nt);
                    dp.add(dl);
                    dp.add(sp);

                    int result = JOptionPane.showConfirmDialog(null, dp, "Described place", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        name = nt.getText();
                        DescribedPlace d = new DescribedPlace(name, dt.getText(), p, c);
                        if (categoryList.getSelectedValue() != null) {     //Förutsätter att index är samma för bägge

                            int i = categoryList.getSelectedIndex();
                            c = catArr.get(i).getColor();
                            d = new DescribedPlace(name, dt.getText(), p, c);

                            catArr.get(i).addPlace(d);
                            System.out.println("Hittade categoryList");
                        }                                                //om kategori ej vald

                        mapImg.setLayout(null);
                        stringMap.put(d, name);
                        positionMap.put(p, d);
                        d.setVisible(true);
                        mapImg.add(d.getTriangle());
                        mapImg.add(d);

                        for (Position key : positionMap.keySet()) {
                            Place place = positionMap.get(key);
                            System.out.println(place.getName());
                            System.out.println(key.getX() + " " + key.getY());
                        }

                        mapImg.validate();
                        mapImg.repaint();

                    }//skapa ny describedPlace
                }
                setCursor(Cursor.getDefaultCursor());
                mapImg.removeMouseListener(this);
                mapImg.addMouseListener(mapListen);
            }
        });
    }

    public void showCategory(String s) {
        for (int i = 0; i < catArr.size(); i++) {
            if (catArr.get(i).getName().equals(s)) {
                ArrayList<Place> places = catArr.get(i).getPlaces();
                for (Place p : places) {
                    p.setVisible(true);
                }
                break;
            }
            change = true;
        }

        //Kod för att visa kategorin.
    }

    //--------Avmarkering -------//

    public void unMark() {
        for (Map.Entry<Boolean, Place> mark : markMap.entrySet()) {            //avmarkera alla platser
            Place p = mark.getValue();
            p.setVisible(false);
            p.setMarked(false);
        }
        markMap = new HashMap<Boolean, Place>();
        change = true;
    }

    class newMapListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            newMap();
        }
    }

    class newOpenListener implements ActionListener {
        public void actionPerformed(ActionEvent ave) {
            open();
        }
    }

    //--------- SPARA -------------//

    public void save() {

        change = false;
    }

    //------------ OPEN ------------//

    public void open() {
        JFileChooser jfc = new JFileChooser("user.dir");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Karta", "karta");
        jfc.addChoosableFileFilter(fnef);

        int answer = jfc.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {

            File f = jfc.getSelectedFile();
            named = true;
        }
    }

    class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent ave) {
            System.exit(0);
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

            form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
            form.add(p);
            form.add(ccp);

            int answer = JOptionPane.showConfirmDialog(null, form, "New category", JOptionPane.OK_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                String n = t.getText();
                Color c = cc.getColor();

                Category cat = new Category(n, c);
                catArr.add(cat);
                model.add(model.size(), n);
                change = true;
                //måste lägga in namnet i listan och koppla till objektet
            }
        }
    }

    class MapListen implements MouseListener {
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            for (int i = x - 7; i < x + 7; i++) {
                for (int j = y - 7; j < y + 7; j++) {

                    Position pos = new Position(i, j);

                    if (positionMap.containsKey(pos)) {


                        Place p = positionMap.get(pos);
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!p.getMarked()) {
                                p.setMarked(true);
                                markMap.put(true, p);

                            } else {
                                p.setMarked(false);
                                markMap.remove(p);
                            }
                            change = true;

                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (!p.getShow()) {
                                p.setShow(true);
                                p.setVisible(false);

                            } else {
                                p.setShow(false);
                                p.setVisible(true);

                            }
                            change = true;
                        }


                        validate();
                        repaint();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}
