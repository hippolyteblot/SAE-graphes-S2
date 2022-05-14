

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ActionChoice extends JPanel {
    
    int width;
    int height;
    Graphics2D g2d;

    JButton neighbors = new JButton("Voisins");
    JButton shortestPath = new JButton("Plus court chemin");
    JButton compare = new JButton("Comparer");
    JButton addLocation = new JButton("Ajouter un lieu");
    JButton removeLocation = new JButton("Supprimer un lieu");
    JButton manageRoad = new JButton("Gérer les routes");
    JButton manageData = new JButton("Gérer les données");
    JButton zoomIn = new JButton("+");
    JButton zoomOut = new JButton("-");
    JPanel zoomer = new JPanel();
    JLabel indication = new JLabel();
    boolean showingIndication = false;
    private int indicationType = -1;

    Map map;
    SmViewer smViewer;
    FrameManager frameManager;
    Main main;

    public ActionChoice() {

    }

    public ActionChoice(int width, int height, Map map, SmViewer smViewer, FrameManager frameManager, Main main) {

        this.frameManager = frameManager;
        this.width = width;
        this.height = height;
        this.map = map;
        this.smViewer = smViewer;
        this.main = main;
        //neighbors = new JButton(new GetNeighborsAction(this));
        //shortestPath = new JButton(new GetShortestPathAction(this));

        buildComponents();

        neighbors.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        shortestPath.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        compare.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        addLocation.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        removeLocation.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        manageRoad.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        manageData.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        zoomIn.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        zoomOut.setFont(new Font("Segoe UI", Font.PLAIN, 34));


        addBtnEvent();

        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    public void showIndication(int type) {
        indicationType = type;
        showingIndication = true;
        removeAll();
        setLayout(new FlowLayout());
        switch (type) {
            case 0:
                indication = new JLabel("Séléctionnez un lieu");
                indication.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                indication.setHorizontalAlignment(JLabel.CENTER);
                indication.setVerticalAlignment(JLabel.CENTER);
                indication.setSize(new Dimension(width, height));
                add(indication);
                repaint();
                break;
            case 1:
            case 2:
                indication = new JLabel("Séléctionnez deux lieux");
                indication.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                indication.setHorizontalAlignment(JLabel.CENTER);
                indication.setVerticalAlignment(JLabel.CENTER);
                indication.setSize(new Dimension(width, height));
                add(indication);
                repaint();
                break;
            case 3:
                indication = new JLabel("Séléctionnez le lieu à supprimer");
                indication.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                indication.setHorizontalAlignment(JLabel.CENTER);
                indication.setVerticalAlignment(JLabel.CENTER);
                indication.setSize(new Dimension(width, height));
                add(indication);
                repaint();
                break;
            default:
                break;
        }
    }

    public void hideIndication() {
        showingIndication = false;
        removeAll();
        buildComponents();
        repaint();
    }
    public void buildComponents() {
        setLayout(new GridLayout(2, 4));
        zoomer.setLayout(new GridLayout(1, 2));

        add(neighbors);
        add(shortestPath);
        add(compare);
        add(addLocation);
        add(removeLocation);
        add(manageRoad);
        add(manageData);

        zoomer.add(zoomIn);
        zoomer.add(zoomOut);
        add(zoomer);
    }

    public void addBtnEvent(){

        neighbors.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showIndication(0);
            }
        });
        shortestPath.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showIndication(1);
            }
        });
        compare.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showIndication(2);
            }
        });

        manageData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new DataManager(frameManager, main);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addLocation.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                smViewer.infoAboutAction(3, 0);
                frameManager.repaint();
            }
        });
        manageRoad.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                smViewer.infoAboutAction(4, 0);
                frameManager.repaint();
            }
        });

        removeLocation.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showIndication(3);
            }
        });
                // Zoom in event
        zoomIn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                map.zoomIn();
                //map.paintComponent(map.getGraphics());
                zoomOut.update(zoomOut.getGraphics());
            }
        });
                // Zoom out event
        zoomOut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                map.zoomOut();
                //map.paintComponent(map.getGraphics());
                zoomIn.update(zoomIn.getGraphics());
            }
        });
    }

    public boolean isShowingIndication() {
        return showingIndication;
    }
    public int getIndicationType() {
        return indicationType;
    }
    public void setIndicationType(int indicationType) {
        this.indicationType = indicationType;
    }
}