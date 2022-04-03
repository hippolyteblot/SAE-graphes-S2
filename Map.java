import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Map extends JPanel implements MouseListener {

    int width;
    int height;
    ArrayList<Liste> tab;
    Graphics2D g2d;
    PathSearcher ps;
    Sommet tmp1;
    Sommet tmp2;
    JPanel interactionPanel;

    public Map() {

    }

    public Map(int width, int height, ArrayList<Liste> tab) {

        this.tab = tab;
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        addMouseListener(this);
        setVisible(true);
        ps = new PathSearcher(tab);
    }

    @Override
    protected void paintComponent(Graphics g) {

        g2d = (Graphics2D) g;

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        
        setLayout(null);
        g2d.setStroke(new BasicStroke(2));

        for(int i = 0; i < this.tab.size(); i++){

            g2d.setColor(this.getSommetColor(this.tab.get(i).getOrigin()));   
            Ellipse2D.Double circle = new Ellipse2D.Double(tab.get(i).getOrigin().getValue().getX(), 
                tab.get(i).getOrigin().getValue().getY(), 
                50, 50);
            g2d.fill(circle);
            g2d.setColor(this.getSommetColor(this.tab.get(i).getOrigin()));

            paintLink(this.tab.get(i), i, false, 0);
            
            g2d.drawOval(tab.get(i).getOrigin().getValue().getX(), 
                tab.get(i).getOrigin().getValue().getY(),
                50, 50);
        }
        setVisible(true);
    }

    public void paintLink(Liste ls, int indice2, boolean exception, int TTL){

        int length = ls.lenghtList();
        Cell actuel = ls.getOrigin().getSuivant();
        //Graphics2D g2d = (Graphics2D) interactionPanel.getGraphics();

        for(int i = 1; i < length; i++){
            
            int indice = findSm(actuel.getValue().getName());

            Cell cl = tab.get(indice).getOrigin();
            if(exception){
                g2d.setStroke(new BasicStroke(7));
                g2d.setColor(Color.cyan);
                g2d.drawLine(
                    tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                    tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                    cl.getValue().getX()+25, // x2
                    cl.getValue().getY()+25 // y2
                    );
                g2d.drawOval(cl.getValue().getX()-2, 
                    cl.getValue().getY()-2, 50+4, 50+4);
                if(TTL > 0)
                    paintLink(tab.get(indice), indice, exception, TTL-1);
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(getRoadColor(actuel)); 
            g2d.drawLine(
                tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                cl.getValue().getX()+25, // x2
                cl.getValue().getY()+25 // y2
                );
            actuel = actuel.getSuivant();
        }
        g2d.setStroke(new BasicStroke(3));
    }


    public int findSm(String nom){
        int indice = 0;
        for(int i = 0;i < this.tab.size(); i++){
            if(tab.get(i).getOrigin().getValue().getName().equals(nom)){
                indice = i;
                break;
            }
        }

        return indice;
    }



    public Color getRoadColor(Cell cl){
        Color color = new Color(0,0,0);
        switch(cl.getRoute().getType()){
            case 1:
                color = new Color(250,20,100);
                break;
            case 2:
                color = new Color(200,20,150);
                break;
            case 3:
                color = new Color(150,20,200);
                break;
            default:
                color = new Color(100,20,250);
                break;
        }
        return color;
    }
    public Color getSommetColor(Cell cl){
        Color color = new Color(0,0,0);
        switch(cl.getValue().getType()){
            case 1:
                color = new Color(20,200,100);
                break;
            case 2:
                color = new Color(20,150,150);
                break;
            default:
                color = new Color(20,100,200);
                break;
        }
        return color;
    }
    
    public boolean checkSmArea(int x, int y, Sommet sm){

        if(sm.getX() >= x-50 &&sm.getY() > y-50 && sm.getX() < x && sm.getY() < y){
            return true;
        }
        return false;
    }

    public void paintSpecificLink(Sommet sm1, Sommet sm2){
        
        int indice2 = ps.indexSm(sm1);

        for(int i = 0; i < tab.get(indice2).lenghtList(); i++){
            if(tab.get(indice2).getACell(i).getValue().getName().equals(sm2.getName())){
                g2d.setStroke(new BasicStroke(4));
                g2d.setColor(Color.red); 
                Cell goodOne = tab.get(ps.indexSm(sm2)).getOrigin();
                g2d.drawLine(
                    tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                    tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                    goodOne.getValue().getX()+25, // x2
                    goodOne.getValue().getY()+25 // y2
                    );
            }
        }
    }


    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.get(i).getOrigin().getValue())){
                
                g2d.setStroke(new BasicStroke(5));
                g2d.setColor(Color.cyan);
                g2d.drawOval(this.tab.get(i).getOrigin().getValue().getX()-2, 
                    this.tab.get(i).getOrigin().getValue().getY()-2, 50+4, 50+4);
                paintLink(this.tab.get(i), i, true, 0);
            }
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        g2d = (Graphics2D) getGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        paintComponent(getGraphics());

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.get(i).getOrigin().getValue())){
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawOval(this.tab.get(i).getOrigin().getValue().getX()-2, 
                    this.tab.get(i).getOrigin().getValue().getY()-2, 50+4, 50+4);
                this.tmp1 = this.tab.get(i).getOrigin().getValue();
                break;
            }
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.get(i).getOrigin().getValue())){
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawOval(this.tab.get(i).getOrigin().getValue().getX()-2, 
                    this.tab.get(i).getOrigin().getValue().getY()-2, 50+4, 50+4);
                this.tmp2 = this.tab.get(i).getOrigin().getValue();
                break;
            }
        }
        ArrayList<String> ls =  ps.dijkstra(tmp1, tmp2);

        System.out.println();
        for(int i = 0; i < ls.size()-1; i++){
            Sommet sm1 = ps.findSmByString(ls.get(i));
            Sommet sm2 = ps.findSmByString(ls.get(i+1));
            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(4));
            paintSpecificLink(sm1, sm2);
            if(i == 0)
                System.out.print(ls.get(i));
            else
                System.out.print(" -> " + ls.get(i));
        }
        System.out.println(" -> " + tmp2.getName());
    }
    

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
