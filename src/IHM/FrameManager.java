package IHM;

import DataStructure.*;
import Launch.*;
import ProcessingEngine.PathFinder;

import javax.swing.*;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

public class FrameManager extends JFrame {

    NodeList tab;
    private int width;
    private int height;
    JPanel contentPane = new JPanel();
    Map map;
    SmViewer smViewer;
    ActionChoice actionChoice;
    Main main;
    JSplitPane spliter2;

    JMenuBar menuBar = new JMenuBar();
    JMenu dataMenu = new JMenu("Data");
    JMenuItem loadData = new JMenuItem("Load data");
    JMenuItem saveData = new JMenuItem("Save data");
    JMenuItem eraseData = new JMenuItem("Erase data");

    JMenu ToolsMenu = new JMenu("Outils");
    JMenuItem findShortestPath = new JMenuItem("Find shortest path");
    JMenuItem compare = new JMenuItem("Compare");

    JMenu settingsMenu = new JMenu("Réglages");
    JMenuItem modifier = new JMenuItem("Modifier");

    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutDataImport = new JMenuItem("About data import");
    JMenuItem aboutPathFinder = new JMenuItem("About path finder");
    JMenuItem aboutPathBuiler = new JMenuItem("About path builder");
    JMenuItem aboutElse = new JMenuItem("About else");
    JCheckBoxMenuItem darkMode = new JCheckBoxMenuItem("Mode sombre");

    //Color backgroundColor = new Color(40, 40, 40);


    public FrameManager(NodeList tab, int width, int height, PathFinder ps, Main main) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {

        this.main = main;
        this.width = width;
        this.height = height;
        this.tab = tab;
        buildMenu();


        map = new Map(5000, 4000, tab, this, main); // 1600, 800
        smViewer = new SmViewer(width-1600, height, tab, map, ps, main, null);
        map.setSmViewer(smViewer);
        actionChoice = new ActionChoice(width, height-800, map, smViewer, this, main);
        smViewer.setActionChoice(actionChoice);
        map.setActionChoice(actionChoice);

        setSize(this.width, this.height);
        setTitle("Graphe");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main.serializeSettings();
                System.exit(0);
            }
        });

        addResizeEvent();

        contentPane.setSize(width, height);
        contentPane.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(map);
        jsp.setPreferredSize(new Dimension(1600, 800));

        jsp.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    map.zoomIn();
                } else {
                    map.zoomOut();
                }
            }
        });
        // Move the map as the mouse is dragged


        JPanel container = new JPanel();
        container.setLayout(null);
        jsp.add(container);
        jsp.setBounds(new Rectangle(0, 0, 1600, 800));

        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JSplitPane spliterVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp, smViewer);
        spliterVertical.setResizeWeight(0.80);

        spliter2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spliterVertical, actionChoice);
        spliter2.setResizeWeight(0.83);
        contentPane.add(spliter2);

        add(contentPane);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


    public void setActionChoice(ActionChoice actionChoice) {
        this.actionChoice = actionChoice;
    }
    public void buildMenu() {
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsFrame(main);
            }
        });
        darkMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(darkMode.isSelected()) {
                    main.applyDarkMode(true);
                    map.darkText(true);
                } else {
                    main.applyDarkMode(false);
                    map.darkText(false);
                }
            }
        });

        dataMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        loadData.setFont(new Font("Arial", Font.PLAIN, 16));
        saveData.setFont(new Font("Arial", Font.PLAIN, 16));
        eraseData.setFont(new Font("Arial", Font.PLAIN, 16));
        ToolsMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        findShortestPath.setFont(new Font("Arial", Font.PLAIN, 16));
        compare.setFont(new Font("Arial", Font.PLAIN, 16));
        settingsMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        modifier.setFont(new Font("Arial", Font.PLAIN, 16));
        helpMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutDataImport.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutPathFinder.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutPathBuiler.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutElse.setFont(new Font("Arial", Font.PLAIN, 16));
        darkMode.setFont(new Font("Arial", Font.PLAIN, 16));

        menuBar.add(dataMenu);
        dataMenu.add(loadData);
        dataMenu.add(saveData);
        dataMenu.add(eraseData);
        menuBar.add(ToolsMenu);
        ToolsMenu.add(findShortestPath);
        ToolsMenu.add(compare);
        menuBar.add(settingsMenu);
        settingsMenu.add(modifier);
        settingsMenu.add(darkMode);
        menuBar.add(helpMenu);
        helpMenu.add(aboutDataImport);
        helpMenu.add(aboutPathFinder);
        helpMenu.add(aboutPathBuiler);
        helpMenu.add(aboutElse);

        setJMenuBar(menuBar);

    }

    public void addResizeEvent(){
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                update(getGraphics());
                Rectangle r = getBounds();
                setWidth(r.width);
                setHeight(r.height);
                setSize(r.width, r.height);
                paint(getGraphics());
            }
        });
    }
    public void setFullScreen() {
        dispose();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void setWindowed() {
        dispose();
        setUndecorated(false);
        setExtendedState(JFrame.NORMAL);
        setVisible(true);
    }

    public void reinit(){
        tab = main.getTab();
        map.setTab(tab);
        map.setPosSm();
        map.repaint();
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void setTab(NodeList tab){
        this.tab = tab;
    }

}