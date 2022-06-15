package IHM;

import DataStructure.*;
import Launch.*;
import ProcessingEngine.PathFinder;

import javax.swing.*;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.net.URISyntaxException;

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
    JMenu dataMenu = new JMenu("Données");
    JMenuItem loadData = new JMenuItem("Gérer les données");
    JMenuItem saveData = new JMenuItem("Sauvgarder le graphe");
    JMenuItem eraseData = new JMenuItem("Supprimer le graphe");

    JMenu ToolsMenu = new JMenu("Outils");
    JMenuItem findShortestPath = new JMenuItem("Plus court chemin");
    JMenuItem compare = new JMenuItem("Comparer");

    JMenu settingsMenu = new JMenu("Réglages");
    JMenuItem modifier = new JMenuItem("Paramètres");

    JMenu helpMenu = new JMenu("Aide");
    JMenuItem aboutDataImport = new JMenuItem("A propos de l'importation");
    JMenuItem aboutPathFinder = new JMenuItem("A propos de la recherche de chemin");
    JMenuItem aboutPathBuiler = new JMenuItem("A propos de la Structure de données");
    JMenuItem aboutElse = new JMenuItem("A propos");
    JCheckBoxMenuItem darkMode = new JCheckBoxMenuItem("Mode sombre");

    //Color backgroundColor = new Color(40, 40, 40);


    public FrameManager(NodeList tab, int width, int height, PathFinder ps, Main main) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {

        this.main = main;
        this.width = width;
        this.height = height;
        this.tab = tab;
        buildMenu();


        map = new Map(5000, 4000, tab, main); // 1600, 800
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
        loadData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionChoice.getManageData().doClick();
            }
        });
        findShortestPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionChoice.getShortestPath().doClick();
            }
        });
        compare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionChoice.getCompare().doClick();
            }
        });

        aboutDataImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Google webPage
                try {
                    Desktop.getDesktop().browse(new java.net.URI("https://hippolyteblot.notion.site/Importation-de-donn-es-et-traitement-9bba51c875bc4cd4a9a9c5dee402d5f3"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        aboutPathFinder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Google webPage
                try {
                    Desktop.getDesktop().browse(new java.net.URI("https://hippolyteblot.notion.site/Recherche-de-chemins-2bea83d07c7c418a96acb34b5bfd95d0"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        aboutPathBuiler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Google webPage
                try {
                    Desktop.getDesktop().browse(new java.net.URI("https://hippolyteblot.notion.site/Gestion-des-donn-es-Stockage-structure-enregistrement-789a09e4ead04d0ab178fb5fcd443d5c"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        aboutElse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Google webPage
                try {
                    Desktop.getDesktop().browse(new java.net.URI("https://hippolyteblot.notion.site/Notes-SAE-Graphe-65ab0045d0774c0bb528ecbf45ca3b1c"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
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

        if(main.getSettings().isDarkMod()) {
            darkMode.setSelected(true);
        } else {
            darkMode.setSelected(false);
        }

        menuBar.add(dataMenu);
        dataMenu.add(loadData);
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