

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class SmViewer extends JPanel {
    
    int width;
    int height;
    NodeList tab;
    Map map;
    PathFinder pf;

    JTextField distance = new JTextField("test");
    JLabel jl2 = new JLabel("Distance :");
    JPanel inputArea = new JPanel();

    JLabel name;
    JLabel type;
    JLabel nbNeighLabel;
    JLabel imageLabel;



    public SmViewer() {

    }

    public SmViewer(int width, int height, NodeList tab, Map map, PathFinder pf) throws IOException {

        this.tab = tab;
        this.width = width;
        this.height = height;
        this.map = map;
        this.pf = pf;
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setVisible(true);
    }

    public void infoAboutSm() throws IOException {
        this.removeAll();

        System.out.println("Nom : : " + map.getClicked().getName());
        name = new JLabel("Nom : " + map.getClicked().getName());
        type = new JLabel("Type : " + map.getClicked().getStringType());
        int nbNeigh = tab.get(tab.getNodeIndex(map.getClicked())).size();
        nbNeighLabel = new JLabel("Nombre de voisins : " + String.valueOf(nbNeigh));

        name.setFont(new Font("arial", Font.PLAIN, 22));
        type.setFont(new Font("arial", Font.PLAIN, 22));

        add(name);
        add(type);
        //add(imageLabel);
        repaint();

    }
}
