package IHM;

import DataEngine.DataManager;
import Launch.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ActionChoice extends JPanel {
    
    private final int width;
    private final int height;
    private final JButton neighbors = new JButton("Voisins");
    private final JButton shortestPath = new JButton("Plus court chemin");
    private final JButton compare = new JButton("Comparer");
    private final JButton manageLocation = new JButton("Gérer les lieux");
    private final JButton settings = new JButton("Options");
    private final JButton manageRoad = new JButton("Gérer les routes");
    private final JButton manageData = new JButton("Gérer les données");
    private final JButton zoomIn = new JButton("+");
    private final JButton zoomOut = new JButton("-");
    private final JPanel zoomer = new JPanel();
    private int indicationType = -1;

    private final Map map;
    private final SmViewer smViewer;
    private final FrameManager frameManager;
    private final Main main;

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

    public void showIndication(int type) {
        indicationType = type;
        removeAll();
        setLayout(new FlowLayout());
        JLabel indication;
        switch (type) {
            case 0 -> {
                indication = new JLabel("Sélectionnez un lieu");
                indication.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                indication.setHorizontalAlignment(JLabel.CENTER);
                indication.setVerticalAlignment(JLabel.CENTER);
                indication.setSize(new Dimension(width, height));
                add(indication);
                repaint();
            }
            case 1, 2 -> {
                indication = new JLabel("Sélectionnez deux lieux");
                indication.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                indication.setHorizontalAlignment(JLabel.CENTER);
                indication.setVerticalAlignment(JLabel.CENTER);
                indication.setSize(new Dimension(width, height));
                add(indication);
                repaint();
            }
            case 3 -> {
                indication = new JLabel("Sélectionnez le lieu à supprimer");
                indication.setFont(new Font("Segoe UI", Font.PLAIN, 34));
                indication.setHorizontalAlignment(JLabel.CENTER);
                indication.setVerticalAlignment(JLabel.CENTER);
                indication.setSize(new Dimension(width, height));
                add(indication);
                repaint();
            }
            default -> {
            }
        }
        updateUI();
    }

    public void hideIndication() {
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
        add(zoomer);
    }

    public void addBtnEvent(){

        neighbors.addActionListener(e -> showIndication(0));
        shortestPath.addActionListener(e -> showIndication(1));
        compare.addActionListener(e -> showIndication(2));

        manageData.addActionListener(e -> {
            try {
                new DataManager(frameManager, main);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        manageLocation.addActionListener(e -> {
            smViewer.infoAboutManageNodes();
            map.setNothingTouched(false);
            frameManager.repaint();
        });
        manageRoad.addActionListener(e -> {
            smViewer.infoAboutRoads();
            map.setNothingTouched(false);
            frameManager.repaint();
        });

        settings.addActionListener(e -> new SettingsFrame(main));
                // Zoom in event
        zoomIn.addActionListener(e -> {
            map.zoomIn();
            //map.paintComponent(map.getGraphics());
            zoomOut.update(zoomOut.getGraphics());
        });
                // Zoom out event
        zoomOut.addActionListener(e -> {
            map.zoomOut();
            //map.paintComponent(map.getGraphics());
            zoomIn.update(zoomIn.getGraphics());
        });
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

    public JButton getShortestPath() {
        return shortestPath;
    }
    public JButton getCompare() {
        return compare;
    }

    public JButton getManageData() {
        return manageData;
    }

}