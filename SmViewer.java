import java.awt.*;
import java.util.*;
import javax.swing.*;


public class SmViewer extends JPanel {
    
    int width;
    int height;
    ArrayList<Liste> tab;
    Graphics2D g2d;
    JLabel jl;
    Map map;

    public SmViewer() {

    }

    public SmViewer(int width, int height, ArrayList<Liste> tab, Map map) {

        this.tab = tab;
        this.width = width;
        this.height = height;
        this.map = map;
        jl = new JLabel("Rehcercher un lieu");
        add(jl);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
/*
        g2d = (Graphics2D) g;

        g2d.setColor(Color.red);
        g2d.fillRect(0, 0, width, height);
        setVisible(true);
        */
    }

    public void neighborsMod(){

        jl.setText("Voisins de " + map.getTmp1().getName());
        JTextField distance = new JTextField();
        add(distance);
    }
}
