

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

    JButton neighbors = new JButton("Neighbors");
    JButton shortestPath = new JButton("Shortest path");
    JButton compare = new JButton("Compare");
    JButton addLocation = new JButton("Add location");
    JButton removeLocation = new JButton("Remove location");
    JButton addRoad = new JButton("Add road");
    JButton manageData = new JButton("Manage data");
    JButton zoomIn = new JButton("+");
    JButton zoomOut = new JButton("-");
    JPanel zoomer = new JPanel();
    boolean showingIndication = false;
    int indicationType = -1;

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
        addRoad.setFont(new Font("Segoe UI", Font.PLAIN, 34));
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
                JLabel label = new JLabel("Select a location");
                label.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setSize(new Dimension(width, height));
                add(label);
                repaint();
                break;
            case 1:
                JLabel label2 = new JLabel("Select two locations");
                label2.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                label2.setHorizontalAlignment(JLabel.CENTER);
                label2.setVerticalAlignment(JLabel.CENTER);
                label2.setSize(new Dimension(width, height));
                add(label2);
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
        add(addRoad);
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
    public int indicationType() {
        return indicationType;
    }
    public void setIndicationType(int indicationType) {
        this.indicationType = indicationType;
    }
}