package IHM;

import DataEngine.DataManager;
import IHM.FrameManager;
import IHM.SettingsFrame;
import IHM.SmViewer;
import Launch.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActionChoice extends JPanel {
    
    int width;
    int height;
    Graphics2D g2d;

    JButton neighbors = new JButton("Voisins");
    JButton shortestPath = new JButton("Plus court chemin");
    JButton compare = new JButton("Comparer");
    JButton manageLocation = new JButton("Gérer les lieux");
    JButton settings = new JButton("Options");
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

    public ActionChoice(int width, int height, Map map, SmViewer smViewer, FrameManager frameManager, Main main) {

        this.frameManager = frameManager;
        this.width = width;
        this.height = height;
        this.map = map;
        this.smViewer = smViewer;
        this.main = main;

        buildComponents();

        neighbors.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        shortestPath.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        compare.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        manageLocation.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        settings.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        manageRoad.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        manageData.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        zoomIn.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        zoomOut.setFont(new Font("Segoe UI", Font.PLAIN, 34));


        addBtnEvent();

        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    public void setBtnBackground() {
        Color backgroundColor = new Color(40, 40, 40);
        neighbors.setBackground(backgroundColor);
        shortestPath.setBackground(backgroundColor);
        compare.setBackground(backgroundColor);
        manageLocation.setBackground(backgroundColor);
        settings.setBackground(backgroundColor);
        manageRoad.setBackground(backgroundColor);
        manageData.setBackground(backgroundColor);
        zoomIn.setBackground(backgroundColor);
        zoomOut.setBackground(backgroundColor);

        Color foregroundColor = new Color(60, 60, 60);
        neighbors.setForeground(foregroundColor);
        shortestPath.setForeground(foregroundColor);
        compare.setForeground(foregroundColor);
        manageLocation.setForeground(foregroundColor);
        settings.setForeground(foregroundColor);
        manageRoad.setForeground(foregroundColor);
        manageData.setForeground(foregroundColor);
        zoomIn.setForeground(foregroundColor);
        zoomOut.setForeground(foregroundColor);

        // Change btn color
        neighbors.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        shortestPath.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        compare.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        manageLocation.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        settings.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        manageRoad.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        manageData.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        zoomIn.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        zoomOut.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));

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
        add(manageLocation);
        add(manageRoad);
        add(manageData);
        add(settings);

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
        manageLocation.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                smViewer.infoAboutAction(8, 0);
                map.setNothingTouched(false);
                frameManager.repaint();
            }
        });
        manageRoad.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                smViewer.infoAboutRoads();
                map.setNothingTouched(false);
                frameManager.repaint();
            }
        });

        settings.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsFrame(main);
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
    public JButton getZoomIn() {
        return zoomIn;
    }
    public JButton getZoomOut() {
        return zoomOut;
    }
}