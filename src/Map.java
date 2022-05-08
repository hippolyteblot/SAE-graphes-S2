
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


public class Map extends JPanel implements MouseListener {

    Color bgColor = new Color(238,238,238);
    int width;
    int height;
    NodeList tab;
    ActionChoice actionChoice;
    PathFinder pf;
    Sommet clicked;
    Sommet tmp1 = null;
    Sommet tmp2 = null;
    Double zoom = 1.0;
    SmViewer smViewer;
    boolean drawNeighbours = false;
    boolean drawPath = false;


    public Map(int width, int height, NodeList tab){

        this.tab = tab;

        this.width = width;
        this.height = height;

        setPosSm();
        findEquivalence();

        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        addMouseListener(this);
        setVisible(true);
        printPosition();

        pf = new PathFinder(tab);
    }

    public void printPosition(){
        for(NeighborsList s : tab){
            for(Cell c : s.getNeighbors()){
                System.out.println(s.getSommet().getX() + ", " + s.getSommet().getY() + " - " + c.getValue().getX() + ", " + c.getValue().getY());
            }
        }
    }
    public void setSmViewer(SmViewer smViewer){
        this.smViewer = smViewer;
    }
    public void setActionChoice(ActionChoice actionChoice){
        this.actionChoice = actionChoice;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);

        AffineTransform tr2 = new AffineTransform(g2d.getTransform());
        tr2.scale(zoom,zoom);
        g2d.setTransform(tr2);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

        paintEverything(g);
        if(clicked != null && drawNeighbours){
            showNeighbours(clicked, 1, g);
            drawNeighbours = false;
            actionChoice.setIndicationType(-1);
        } else if (drawPath){
            drawPath(g);
            drawPath = false;
            actionChoice.setIndicationType(-1);
            tmp1 = null;
            tmp2 = null;
        }
    }

    public void preparePainting(){

        paintSpecificLink(tmp1, tmp2, this.getGraphics());
    }

    public void paintLink(Sommet s1, Sommet s2, Color c, Graphics g, int stroke){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(stroke));
        g2d.setColor(c);
        g2d.drawLine(
            s1.getX()+25, // x1
            s1.getY()+25, // y1
            s2.getX()+25, // x2
            s2.getY()+25 // y2
            );
    }
    public void drawPath(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        ArrayList<Sommet> ls =  pf.dijkstra(tmp1, tmp2);
        System.out.println();
        for(int i = 0; i < ls.size()-1; i++){
            Sommet sm1 = ls.get(i);
            Sommet sm2 = ls.get(i+1);

            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(4));

            paintSpecificLink(sm1, sm2, g);
            if(i == 0)
                System.out.print(ls.get(i));
            else
                System.out.print(" -> " + ls.get(i));
        }
        System.out.println(" -> " + tmp2.getName());
    }


    public void paintEverything(Graphics g){
        paintAllEllipses(g);
        paintAllLinks(g);
    }
    public void paintAllLinks(Graphics g){
        for(NeighborsList s : tab){
            for(Cell c : s.getNeighbors()){
                paintLink(s.getSommet(), c.getValue(), getRoadColor(c.getValue()), g, 3);
            }
        }
    }
    public void paintAllEllipses(Graphics g){
        for(int i = 0; i < this.tab.size(); i++){
            paintEllipse(g, this.tab.getNode(i));
        }
    }
    public void paintSpecificLink(Sommet s1, Sommet s2, Graphics g){
        paintLink(s1, s2, Color.red, g, 5);
    }

    public void paintEllipse(Graphics g, Sommet s){
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(
                s.getX()+((50*(zoom-1))/4), // x
                s.getY()+((50*(zoom-1))/4), // y
                50/zoom, 50/zoom);
        g2d.setColor(getSommetColor(s));
        g2d.fill(circle);
    }
    public void findEquivalence(){
        for(int i = 0; i < this.tab.size(); i++){
            for(int j = 0; j < this.tab.get(i).size(); j++){
                for(int k = 1; k < this.tab.size(); k++){
                    if (tab.getNode(k).getName().equals(tab.get(i).get(j).getValue().getName())){
                        tab.get(i).get(j).setValue(tab.getNode(k));
                        System.out.println("Equivalence found " + tab.get(i).get(j).getValue().getX() + ", " + tab.get(i).get(j).getValue().getY());
                        System.out.println("and " + tab.getNode(k).getX() + ", " + tab.getNode(k).getY());
                    }
                }
            }
        }
    }

    public void setPosSm(){

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;

        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        double tmp;

        for(int i = 0; i < tab.size(); i++){
            if((tmp = tab.getNode(i).getLocX()) < minX){
                minX = tmp;
            }
            if((tmp = tab.getNode(i).getLocX()) > maxX){
                maxX = tmp;
            }
            if((tmp = tab.getNode(i).getLocY()) < minY){
                minY = tmp;
            }
            if((tmp = tab.getNode(i).getLocY()) > maxY){
                maxY = tmp;
            }
        }
        maxX = maxX - minX;
        maxY = maxY - minY;
        double factX = 0.9*(1600/(maxX));
        double factY = 0.8*(800/(maxY));

        for(int i = 0; i < tab.size(); i++){
            tab.getNode(i).setX((int) ((tab.getNode(i).getLocX()-minX)*factX));
            tab.getNode(i).setY((int) ((tab.getNode(i).getLocY()-minY)*factY));
        }
    }


    public Color getRoadColor(Sommet sm){
        return switch (sm.getType()) {
            case 1 -> new Color(100, 20, 100);
            case 2 -> new Color(200, 20, 150);
            case 3 -> new Color(150, 20, 200);
            default -> new Color(100, 20, 250);
        };
    }

    public Color getSommetColor(Sommet sm){
        new Color(0, 0, 0);
        return switch (sm.getType()) {
            case 1 -> new Color(20, 200, 100);
            case 2 -> new Color(20, 150, 150);
            default -> new Color(20, 100, 200);
        };
    }

    public void zoomIn(){

        zoom = zoom + 0.1;
        width = (int) (width*1.1);
        height = (int) (height*1.1);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }
    public void zoomOut(){

        zoom = zoom - 0.1;
        width = (int) (width*0.9);
        height = (int) (height*0.9);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }
    
    public boolean checkSmArea(int x, int y, Sommet sm){

        return sm.getX() * zoom >= x - 50 * zoom && sm.getY() * zoom > y - 50 * zoom && sm.getX() * zoom < x && sm.getY() * zoom < y;
    }

    public void showNeighbours(Sommet sm, int ttl, Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        ttl--;
        ArrayList<Cell> neighbours = tab.get(tab.getNodeIndex(sm)).getNeighbors();
        for(int i = 0; i < neighbours.size(); i++){
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.red);
            g2d.drawLine(
                sm.getX()+25, // x1
                sm.getY()+25, // y1
                neighbours.get(i).getValue().getX()+25, // x2
                neighbours.get(i).getValue().getY()+25 // y2
                );
            paintLink(neighbours.get(i).getValue(), sm, Color.red, g, 4);
            if(ttl > 0){
                showNeighbours(neighbours.get(i).getValue(), ttl, g); // Recursive call
            }
        }

    }


    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.getNode(i))){
                clicked = this.tab.getNode(i);
                System.out.println(clicked.getName());
                /*
                try {
                    smViewer.infoAboutSm();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                 */

                //tmp1 = this.tab.getNode(i);
            }
        }
        if(actionChoice.indicationType() == 0){
            drawNeighbours = true;
            drawPath = false;
            actionChoice.hideIndication();
            repaint();
        }
        else if(actionChoice.indicationType() == 1){
            if(tmp1 == null) {
                tmp1 = clicked;
                System.out.println("tmp1 : " + tmp1.getName());
            }
            else {
                tmp2 = clicked;
                System.out.println("tmp2 : " + tmp2.getName());
                actionChoice.hideIndication();
                drawPath = true;
            }
            drawNeighbours = false;
            repaint();
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

        /*
        tmp1 = null;
        tmp2 = null;
         */
        int x = e.getX();
        int y = e.getY();
        /*
        g2d = (Graphics2D) getGraphics();
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);
        paintComponent(getGraphics());

         */

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.getNode(i))){
                /*
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawOval(
                    this.tab.getNode(i).getX()-2,  // x1
                    this.tab.getNode(i).getY()-2,  // y1
                    50+4, // x2
                    50+4 // y2
                    );

                 */
                //this.tmp1 = this.tab.getNode(i);

                break;

            }
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();


        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.getNode(i))){
                /*
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawOval(this.tab.getNode(i).getX()-2,
                    this.tab.getNode(i).getY()-2, 50+4, 50+4);
                 */
                //this.tmp2 = this.tab.getNode(i);
                break;
            }
        }

        if(tmp1 != null && tmp2 != null && tmp1 != tmp2){
            ArrayList<Sommet> ls =  pf.dijkstra(tmp1, tmp2);

            System.out.println();
            for(int i = 0; i < ls.size()-1; i++){
                Sommet sm1 = ls.get(i);
                Sommet sm2 = ls.get(i+1);
                /*
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(4));
                 */
                //paintSpecificLink(sm1, sm2, );
                if(i == 0)
                    System.out.print(ls.get(i));
                else
                    System.out.print(" -> " + ls.get(i));
            }
            System.out.println(" -> " + tmp2.getName());
        }


    }
    

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public Sommet getClicked(){
        return clicked;
    }
    public void setTab(NodeList tab){
        this.tab = tab;
    }
}
