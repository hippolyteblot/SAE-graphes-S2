import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Timer;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class SmViewer extends JPanel {
    
    int width;
    int height;
    ArrayList<Liste> tab;
    Graphics2D g2d;

    public SmViewer() {

    }

    public SmViewer(int width, int height, ArrayList<Liste> tab) {

        this.tab = tab;
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    
    @Override
    protected void paintComponent(Graphics g) {

        g2d = (Graphics2D) g;

        g2d.setColor(Color.red);
        g2d.fillRect(0, 0, width, height);
        setVisible(true);
    }
}
