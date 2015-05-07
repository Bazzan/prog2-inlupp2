package inlupp2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Main extends JFrame implements Serializable {

    File fInUse = new File("");

    Boolean change = false; //Om något görs i programmet
    Boolean named = false;

    Category noCat = new Category("Ingen", Color.BLACK);

    Category c1 = new Category("Kyrkor", Color.CYAN);
    Category c2 = new Category("Butiker", Color.BLUE);
    Category c3 = new Category("Skultpurer", Color.GREEN);
    Category c4 = new Category("Portaler", Color.RED);
    Category c5 = new Category("Moskéer", Color.YELLOW);
    Category c6 = new Category("Skolor", Color.ORANGE);
    ArrayList<Category> catArr = new ArrayList<Category>();

    //slut på testattribut

    HashMap<Place, String> stringMap = new HashMap<>();
    HashMap<Position, Place> positionMap = new HashMap<>();
    ArrayList<Place> markMap = new ArrayList<Place>();

    MapListen mapListen = new MapListen();
    DefaultListModel<String> model = new DefaultListModel<String>();
    JList<String> categoryList = new JList<String>(model);
    JTextField searchField = new JTextField("Search", 12);
    ListListener listListen = new ListListener();

    MapImage mapImg = null;


    Main() {

        catArr.add(noCat);
        catArr.add(c1); //testkategorier
        catArr.add(c2);
        catArr.add(c3);
        catArr.add(c4);
        catArr.add(c5);
        catArr.add(c6);

        for (int i = 0; i < catArr.size(); i++) {
            model.addElement(catArr.get(i).getName());
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
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        archMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        archMenu.add(saveItem);

        JMenuItem exitProg = new JMenuItem("Exit");
        exitProg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
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
                boolean foundResult = false;

                for (Map.Entry<Place, String> me : stringMap.entrySet()) {
                    if (find.equals(me.getValue())) {
                        Place p = me.getKey();
                        p.setMarked(true);
                        p.setVisible(true);
                        p.setShow(false);
                        markMap.add(p);
                        foundResult = true;
                    }
                }

                if (!foundResult) {
                    JOptionPane.showMessageDialog(null, "Det finns inga platser med detta namn!");
                }
                change = true;
            }
        });

        JButton hideButton = new JButton("Hide places");
        northPanel.add(hideButton);
        hideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (Map.Entry<Place, String> mark : stringMap.entrySet()) {
                    if (mark.getKey().getMarked()) {
                        Place p = mark.getKey();
                        p.setVisible(false);
                        p.setMarked(false);
                        p.setShow(false);
                        markMap.remove(p);
                        change = true;
                    }

                }

                validate();
                repaint();
            }
        });

        JButton deletePlaces = new JButton("Delete places");
        northPanel.add(deletePlaces);
        deletePlaces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Place p : markMap) {
                    deletePlace(p);
                }
                change = true;
                markMap.clear();
            }
        });

        JButton wihButton = new JButton("What is here?");
        northPanel.add(wihButton);
        wihButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mapImg != null) {
                    mapImg.addMouseListener(new WihListen());
                } else {
                    JOptionPane.showMessageDialog(null, "Ladda först en karta!");
                }
            }
        });

        add(northPanel, BorderLayout.NORTH);

			/* ---------- Panel för kategorier ------------*/

        JPanel eastPanel = new JPanel();

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JLabel catLabel = new JLabel("Categories");
        eastPanel.add(catLabel);

        categoryList.addListSelectionListener(listListen);
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
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, "Får inte radera kategori 'Ingen'!");
                    return;
                }
                categoryList.removeListSelectionListener(listListen);
                Category c = catArr.get(i);
                for (int index = (c.getPlaces().size() - 1); index >= 0; index--) {
                    deletePlace(c.getPlaces().get(index));
                }
                System.out.println(model.getSize());
                System.out.println(i);
                catArr.remove(i);
                model.remove(i);
                System.out.println(model.getSize());
                categoryList.addListSelectionListener(listListen);
                change = true;
                validate();
                repaint();
            }
        });


        add(eastPanel, BorderLayout.EAST);
        addWindowListener(new WindowListener() {


            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {

                JFrame frame = (JFrame) e.getSource();
                if (!change) {
                    System.exit(0);
                } else {
                    JLabel changeMsg = new JLabel("Ändringar har gjorts. Vill du spara dessa förändringar?");
                    int result = JOptionPane.showConfirmDialog(null, changeMsg, "Varning", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    } else if (result == JOptionPane.YES_OPTION) {
                        save();
                        System.exit(0);
                    } else {
                        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    }

                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        setVisible(true);
        pack();
    }

    public void newMap() {        //Öppnar bildfil för ny karta
        if (mapImg != null) {
        	 int result = 0;
             if (change) {
                 JLabel changeMsg = new JLabel("Ändringar har gjorts. Vill du spara dessa förändringar?");
                 result = JOptionPane.showConfirmDialog(null, changeMsg, "Varning", JOptionPane.YES_NO_CANCEL_OPTION);

                 if (result == JOptionPane.YES_OPTION) {
                     save();
                 } else if (result == JOptionPane.CANCEL_OPTION) {
                     return;
                 }
             }
           
        }
        JFileChooser jfc = new JFileChooser("user.dir");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
        jfc.addChoosableFileFilter(fnef);

        int answer = jfc.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {

            File f = jfc.getSelectedFile();
            
            if (change){
            	remove(mapImg);
            	mapImg.removeMouseListener(mapListen);
            }
            
            mapImg = new MapImage(f);
            validate();
            repaint();
            paintMap();
        }
    }

    public void paintMap() {

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
                //// Behöver break/cancel
                Position p = new Position(e.getX(), e.getY());

                String name;
                Color c = Color.BLACK;
                change = true;
                if (type.equals("NamedPlace")) {
                    name = JOptionPane.showInputDialog("Namn:");    //skapa ny namedPlace
                    if (name != null) {

                        NamedPlace n = new NamedPlace(name, p, c);
//                    model.getElementAt(categoryList.getSelectedIndex());

                        if (categoryList.getSelectedValue() != null) {     //Förutsätter att index är samma för bägge
                            int i = categoryList.getSelectedIndex();
                            c = catArr.get(i).getColor();
                            n = new NamedPlace(name, p, c);

                            n.setVisible(true);


                            catArr.get(i).addPlace(n);


                        } else {                                                        //Om kategori ej vald
                            noCat.addPlace(n);
                        }

                        mapImg.setLayout(null);
                        stringMap.put(n, name);
                        positionMap.put(p, n);
                        n.setVisible(true);
                        mapImg.add(n.getTriangle());
                        mapImg.add(n);
                        mapImg.validate();
                        mapImg.repaint();
                    }

                    
                } else if (type.toString().equals("DescribedPlace")) {            //Denna och den ovan kan kortas ned
                    JPanel dp = new JPanel();
                    dp.setLayout(new BoxLayout(dp, BoxLayout.Y_AXIS));
                    JLabel nl = new JLabel("Namn:");
                    JTextField nt = new JTextField();
                    JLabel dl = new JLabel("Beskrivning:");
                    JTextArea dt = new JTextArea(3, 1);
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

                            Category tempC = catArr.get(i);
                            tempC.addPlace(d);

                        }                                                //om kategori ej vald

                        else {
                            noCat.addPlace(d);
                        }

                        mapImg.setLayout(null);
                        stringMap.put(d, name);
                        positionMap.put(p, d);
                        d.setVisible(true);
                        mapImg.add(d.getTriangle());
                        mapImg.add(d);
                        mapImg.validate();
                        mapImg.repaint();

                    }//skapa ny describedPlace
                }
                setCursor(Cursor.getDefaultCursor());
                mapImg.removeMouseListener(this);
                mapImg.addMouseListener(mapListen);
                categoryList.setSelectedIndex(-1);

            }
        });
    }

    public void showCategory(Category c) {
        for (Place p : c.getPlaces()) {
            p.setVisible(true);
        }
//            if (c.getName().equals(s)) {
//                ArrayList<Place> places = catArr.get(i).getPlaces();
//                for (Place p : places) {
//                    p.setVisible(true);
//                }
//                break;
//            }
        validate();
        repaint();
        change = true;
    }

    //Kod för att visa kategorin.


    //--------Avmarkering -------//

    public void unMark() {
        for (Place markedP : markMap) {            //avmarkera alla platser
            markedP.setMarked(false);
        }
        markMap.clear();
        change = true;
        validate();
        repaint();
    }

    class newMapListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            newMap();
        }
    }


    //--------- SPARA -------------//

    public void save() {
        categoryList.removeListSelectionListener(listListen);
        mapImg.removeMouseListener(mapListen);
        File fToSave = null;

        if (named) {
            fToSave = fInUse;
        } else {
            JFileChooser jfc = new JFileChooser("user.dir");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Karta", "karta", "krt");
            jfc.setFileFilter(fnef);

            int answer = jfc.showSaveDialog(this);
            if (answer == JFileChooser.APPROVE_OPTION) {

                fToSave = jfc.getSelectedFile();

                named = true;
            }
        }

        try {
            String fileName = fToSave.toString();
            System.out.println(fileName);
            if (!fileName.endsWith(".krt")) {
                fToSave = new File(fileName += ".krt");
            }

            FileOutputStream fos = new FileOutputStream(fToSave);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mapImg);
            oos.writeObject(catArr);
            oos.writeObject(stringMap);
            oos.writeObject(positionMap);
            oos.writeObject(markMap);

            oos.close();
            fInUse = fToSave;
            named = true;
        } catch (IOException ioe) {
            System.err.println("Write error: " + ioe);
        }

        change = false;
        mapImg.addMouseListener(mapListen);
        categoryList.addListSelectionListener(listListen);
    }

    //------------ OPEN ------------//

    public void open() {

        int result = 0;
        boolean needReset=false;
        if (change) {
        	needReset=true;
            JLabel changeMsg = new JLabel("Ändringar har gjorts. Vill du spara dessa förändringar?");
            result = JOptionPane.showConfirmDialog(null, changeMsg, "Varning", JOptionPane.YES_NO_CANCEL_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                save();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }


        JFileChooser jfc = new JFileChooser("user.dir");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("karta", "krt");
        jfc.setFileFilter(fnef);

        int answer = jfc.showOpenDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION) {

            File f = jfc.getSelectedFile();

            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);

                categoryList.removeListSelectionListener(listListen);
                if (needReset){
                    reset();
                    remove(mapImg);
                }
                mapImg = (MapImage) ois.readObject();
                catArr = (ArrayList) ois.readObject();
                stringMap = (HashMap) ois.readObject();
                positionMap = (HashMap) ois.readObject();
                markMap = (ArrayList) ois.readObject();
                ois.close();

                model.clear();
                for (Category c : catArr) {
                    model.addElement(c.getName());
                }

                categoryList.addListSelectionListener(listListen = new ListListener());
               
                paintMap();

                named = true;

            } catch (FileNotFoundException fnfe) {
                System.err.println("Hittar ej filen");
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Hittar inte klassen");
            } catch (IOException ioe) {
                System.err.println("Read error: " + ioe);
            }
            validate();
            repaint();
        }

    }
    
    /*-------------- EXIT ------------*/


    public void exit() {
        if (!change) {
            System.exit(0);
        } else {
            JLabel changeMsg = new JLabel("Ändringar har gjorts. Vill du spara dessa förändringar?");
            int result = JOptionPane.showConfirmDialog(null, changeMsg, "Varning", JOptionPane.YES_NO_CANCEL_OPTION);

            if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else if (result == JOptionPane.YES_OPTION) {
                save();
                System.exit(0);
            } else {
                return;
            }
        }
    }
    
    /*-------------- RESET -----------*/

    public void reset() {
        stringMap = null;
        markMap = null;
        positionMap = null;
        catArr = null;
       // mapImg=null;
        categoryList.removeListSelectionListener(listListen);
        categoryList.clearSelection();
        model.clear();
        categoryList.addListSelectionListener(listListen);
        //categoryList = null;															////////	RESET
        //categoryList.removeListSelectionListener(listListen);
        change = false;
        named = false;

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

                model.addElement(n);

                categoryList.setSelectedIndex(-1);
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

                    if (positionMap.containsKey(pos) && (positionMap.get(pos).getTriangle().getVisible() || positionMap.get(pos).getShow())) {

                        Place p = positionMap.get(pos);

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!p.getMarked()) {
                                p.setMarked(true);
                                markMap.add(p);

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

    class WihListen implements MouseListener {
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
            int x = e.getX();
            int y = e.getY();

            for (int i = x - 7; i < x + 7; i++) {
                for (int j = y - 7; j < y + 7; j++) {

                    Position pos = new Position(i, j);

                    if (positionMap.containsKey(pos)) {

                        Place p = positionMap.get(pos);
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            p.setVisible(true);
                            change = true;
                            validate();
                            repaint();

                        }
                    }
                }
            }
            setCursor(Cursor.getDefaultCursor());
            mapImg.removeMouseListener(this);
        }
    }

    public void deletePlace(Place p) {
        stringMap.remove(p);
        positionMap.remove(p.getPosition());
        for (Category c11 : catArr) {
            if (c11.getPlaces().contains(p)) {
                c11.removePlace(p);
            }
        }
        mapImg.remove(p);
        p.setVisible(false);
        p.setMarked(false);
        validate();
        repaint();
    }

    class ListListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            int i = categoryList.getSelectedIndex();
            Category c = catArr.get(i);
            c.setVisible(true);
            validate();
            repaint();
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}
