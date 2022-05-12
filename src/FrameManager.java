
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

    JMenuBar menuBar = new JMenuBar();
    JMenu dataMenu = new JMenu("Data");
    JMenuItem loadData = new JMenuItem("Load data");
    JMenuItem saveData = new JMenuItem("Save data");
    JMenuItem eraseData = new JMenuItem("Erase data");

    JMenu ToolsMenu = new JMenu("Tools");
    JMenuItem findShortestPath = new JMenuItem("Find shortest path");
    JMenuItem compare = new JMenuItem("Compare");

    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutDataImport = new JMenuItem("About data import");
    JMenuItem aboutPathFinder = new JMenuItem("About path finder");
    JMenuItem aboutPathBuiler = new JMenuItem("About path builder");
    JMenuItem aboutElse = new JMenuItem("About else");



    public FrameManager(NodeList tab, int width, int height, PathFinder ps, Main main) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {

        this.main = main;
        this.width = width;
        this.height = height;
        this.tab = tab;

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        buildMenu();

        map = new Map(1600, 800, tab, this);
        smViewer = new SmViewer(width-1600, height, tab, map, ps, main);
        map.setSmViewer(smViewer);
        actionChoice = new ActionChoice(width, height-800, map, smViewer, this, main);
        map.setActionChoice(actionChoice);

        setSize(this.width, this.height);
        setTitle("Graphe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addResizeEvent();

        contentPane.setSize(width, height);
        contentPane.setLayout(new BorderLayout());

        map.setPreferredSize(new Dimension(1600, 800));
        map.setSize(new Dimension(1600, 800));

        JScrollPane jsp = new JScrollPane(map);
        JPanel container = new JPanel();
        container.setLayout(null);
        jsp.add(container);
        jsp.setBounds(new Rectangle(0, 0, 1600, 800));

        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JSplitPane spliterVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp, smViewer);
        spliterVertical.setResizeWeight(0.80);

        JSplitPane spliter2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spliterVertical, actionChoice);
        spliter2.setResizeWeight(0.83);
        contentPane.add(spliter2);

        add(contentPane);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void buildMenu() {
        dataMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        loadData.setFont(new Font("Arial", Font.PLAIN, 16));
        saveData.setFont(new Font("Arial", Font.PLAIN, 16));
        eraseData.setFont(new Font("Arial", Font.PLAIN, 16));
        ToolsMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        findShortestPath.setFont(new Font("Arial", Font.PLAIN, 16));
        compare.setFont(new Font("Arial", Font.PLAIN, 16));
        helpMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutDataImport.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutPathFinder.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutPathBuiler.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutElse.setFont(new Font("Arial", Font.PLAIN, 16));

        menuBar.add(dataMenu);
        dataMenu.add(loadData);
        dataMenu.add(saveData);
        dataMenu.add(eraseData);
        menuBar.add(ToolsMenu);
        ToolsMenu.add(findShortestPath);
        ToolsMenu.add(compare);
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

}