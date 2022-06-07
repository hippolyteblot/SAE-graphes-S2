package IHM;

import DataStructure.*;
import Launch.*;
import ProcessingEngine.PathFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.sqrt;


public class Map extends JPanel implements MouseListener {

    HashMap<RoadType, Color> roadColor = new HashMap<RoadType, Color>();
    HashMap<NodeType, Color> nodeColor = new HashMap<NodeType, Color>();

    int width;
    int height;
    NodeList tab;
    ActionChoice actionChoice;
    PathFinder pf;
    Sommet clicked;
    Sommet tmp1 = null;
    Sommet tmp2 = null;
    Sommet tmp3 = null;
    Double zoom = 1.0;
    SmViewer smViewer;
    boolean drawNeighbours = false;
    boolean drawPath = false;
    boolean compare = false;
    boolean drawSpecialPath = false;
    int zoomCounter = 0;
    FrameManager fm;
    int drawNeighboursModified = 0;
    Main main;
    NodeType specialDraw = null;
    boolean nothingTouched = true;
    boolean dragging = false;
    Point dragStart = null;
    Color textColor = new Color(0, 0, 0);


    public Map(int width, int height, NodeList tab, FrameManager fm, Main main) {

        this.tab = tab;
        this.main = main;
        this.fm = fm;

        this.width = width;
        this.height = height;

        setColorMap();
        darkText(true);

        setPosSm();

        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        addMouseListener(this);
        setVisible(true);

        pf = new PathFinder(tab);

        addDragListener();

    }

    public void addDragListener() {
        // Change map location relative to the mouse drag
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    Point start = dragStart;
                    Point end = e.getPoint();
                    int dx = end.x - start.x;
                    int dy = end.y - start.y;
                    setLocation(getX() + dx, getY() + dy);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    dragging = true;
                    dragStart = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    dragging = false;
                }
            }
        });
    }

    public void darkText(boolean dark) {
        if(dark)
            textColor = new Color(120, 120, 120);
        else
            textColor = new Color(0, 0, 0);
    }
    public void drawName(Sommet sm, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x = sm.getX();
        int y = sm.getY();
        g2d.setColor(textColor);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, (int) (18/zoom)));
        //Create a font object with background color
        g2d.drawString(sm.getName(), x-50, y);
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

        if(specialDraw == null){
            paintEverything(g);
        } else{
            paintSpecial(g);
        }
        if(drawSpecialPath){
            drawSpecialPath(g);
            smViewer.infoAboutPath();
            tmp1 = null;
            tmp2 = null;
            tmp3 = null;
            drawSpecialPath = false;
        }
        else if(drawNeighboursModified > 0){
            drawNeighbours = true;
            showNeighbours(clicked, drawNeighboursModified, g);
            smViewer.infoAboutNeigh(drawNeighboursModified);
            drawNeighboursModified = 0;
            drawNeighbours = false;
        }
        else if(clicked != null && drawNeighbours){
            showNeighbours(clicked, 1, g);

            actionChoice.setIndicationType(-1);
            smViewer.infoAboutNeigh(1);
            drawNeighbours = false;
            //clicked = null;
        } else if (drawPath) {
            drawPath(g);

            actionChoice.setIndicationType(-1);
            smViewer.infoAboutPath();
            tmp1 = null;
            tmp2 = null;
            drawPath = false;
            clicked = null;
        } else if (compare) {

            actionChoice.setIndicationType(-1);
            smViewer.infoAboutCompare();
            tmp1 = null;
            tmp2 = null;
            compare = false;
            clicked = null;
        }
        else if(clicked != null && !nothingTouched) {
            smViewer.infoAboutAction(7, 0);
            clicked = null;
        }
        else if(nothingTouched) {
            smViewer.infoAboutAction(11, 0);
        }
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
        for(int i = 0; i < ls.size()-1; i++){
            Sommet sm1 = ls.get(i);
            Sommet sm2 = ls.get(i+1);

            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke((float) (4/zoom)));

            paintSpecificLink(sm1, sm2, g);
        }
    }
    public void drawSpecialPath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        System.out.println("tmp1 = " + tmp1.getName() + " tmp2 = " + tmp2.getName());
        ArrayList<Sommet> ls =  pf.dijkstra(tmp1, tmp2);
        for(int i = 0; i < ls.size()-1; i++){
            Sommet sm1 = ls.get(i);
            Sommet sm2 = ls.get(i+1);

            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke((float) (4/zoom)));

            paintSpecificLink(sm1, sm2, g);
        }

        ArrayList<Sommet> ls2 =  pf.dijkstra(tmp2, tmp3);
        for(int i = 0; i < ls2.size()-1; i++) {
            Sommet sm1 = ls2.get(i);
            Sommet sm2 = ls2.get(i + 1);

            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke((float) (4 / zoom)));

            paintSpecificLink(sm1, sm2, g);
        }
        ls.addAll(ls2);
    }


    public void paintEverything(Graphics g){
        paintAllEllipses(g);
        paintAllLinks(g);
    }
    public void paintSpecial(Graphics g){
        paintTypedEllipses(g);
        paintTypedLinks(g);
        specialDraw = null;
    }
    public void paintAllLinks(Graphics g){
        for(NeighborsList s : tab){
            for(Cell c : s.getNeighbors()){
                paintLink(s.getSommet(), c.getValue(), roadColor.get(c.getRoute().getType()), g, (int) (3/sqrt(zoom)));
            }
        }
    }
    public void paintTypedLinks(Graphics g){
        for(NeighborsList s : tab){
            if(s.getSommet().getType() == specialDraw){
                for(Cell c : s.getNeighbors()){
                    if(c.getValue().getType() == specialDraw){
                        paintLink(s.getSommet(), c.getValue(), roadColor.get(c.getRoute().getType()), g, (int) (3/sqrt(zoom)));
                    }
                }
            }
        }
    }
    public void paintAllEllipses(Graphics g){
        for(int i = 0; i < this.tab.size(); i++){
            paintEllipse(g, this.tab.getNode(i));
            drawName(this.tab.getNode(i), g);
        }
    }
    public void paintTypedEllipses(Graphics g){
        for(int i = 0; i < this.tab.size(); i++){
            if(this.tab.get(i).getSommet().getType() == specialDraw){
                paintEllipse(g, this.tab.getNode(i));
                drawName(this.tab.getNode(i), g);
            }
        }
    }
    public void paintSpecificLink(Sommet s1, Sommet s2, Graphics g){
        paintLink(s1, s2, Color.red, g, 5);
    }

    public void paintEllipse(Graphics g, Sommet s){
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle;
        if(zoom-1 > 0)
            circle = new Ellipse2D.Double(
                    s.getX()+sqrt((100*(zoom-1))), // x
                    s.getY()+sqrt((100*(zoom-1))), // y
                    50/zoom, 50/zoom);
        else
            circle = new Ellipse2D.Double(
                    s.getX()-sqrt((-100*(zoom-1))), // x
                    s.getY()-sqrt((-100*(zoom-1))), // y
                    50/zoom, 50/zoom);
        g2d.setColor(nodeColor.get(s.getType()));
        g2d.fill(circle);
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
            tab.getNode(i).setX((int) ((tab.getNode(i).getLocX()-minX)*factX)+50);
            tab.getNode(i).setY((int) ((tab.getNode(i).getLocY()-minY)*factY)+50);
        }
    }


    public void zoomIn(){

        if(zoomCounter < 15) {
            zoom = zoom*1.1;
            zoomCounter++;
            repaint();
            width = (int) (width*1.1);
            height = (int) (height*1.1);
            setSize(width, height);
            setPreferredSize(new Dimension(width, height));
            actionChoice.getZoomOut().setEnabled(true);
        }
        else {
            actionChoice.getZoomIn().setEnabled(false);
        }
        // Set location to the center of the screen
        repaint();
    }
    public void zoomOut(){

        if(zoomCounter > -15) {
            zoom = zoom / 1.1;
            zoomCounter--;
            repaint();
            width = (int) (width / 1.1);
            height = (int) (height / 1.1);
            setSize(width, height);
            setPreferredSize(new Dimension(width, height));
            actionChoice.getZoomIn().setEnabled(true);
        }
        else {
            actionChoice.getZoomOut().setEnabled(false);
        }
        // Set location to the center of the screen
        repaint();
    }
    
    public boolean checkSmArea(int x, int y, Sommet sm){

        return sm.getX() * zoom >= x - 50 * zoom && sm.getY() * zoom > y - 50 * zoom && sm.getX() * zoom < x && sm.getY() * zoom < y;
    }

    public void showNeighbours(Sommet sm, int ttl, Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        ttl--;
        ArrayList<Cell> neighbours = tab.get(tab.getNodeIndex(sm)).getNeighbors();
        for (Cell neighbour : neighbours) {
            g2d.setStroke(new BasicStroke((float) (4 / zoom)));
            g2d.setColor(Color.red);
            g2d.drawLine(
                    sm.getX() + 25, // x1
                    sm.getY() + 25, // y1
                    neighbour.getValue().getX() + 25, // x2
                    neighbour.getValue().getY() + 25 // y2
            );
            Ellipse2D.Double circle;
            if(zoom-1 > 0)
                circle = new Ellipse2D.Double(
                        neighbour.getValue().getX()+sqrt((100*(zoom-1))), // x
                        neighbour.getValue().getY()+sqrt((100*(zoom-1))), // y
                        50/zoom, 50/zoom);
            else
                circle = new Ellipse2D.Double(
                        neighbour.getValue().getX()-sqrt((-100*(zoom-1))), // x
                        neighbour.getValue().getY()-sqrt((-100*(zoom-1))), // y
                        50/zoom, 50/zoom);
            g2d.fill(circle);
            paintLink(neighbour.getValue(), sm, Color.red, g, 4);
            if (ttl > 0) {
                showNeighbours(neighbour.getValue(), ttl, g); // Recursive call
            }
        }

    }

    public void setColorMap(){
        nodeColor.put(NodeType.VILLE, new Color(20, 200, 100));
        nodeColor.put(NodeType.RESTAURANT, new Color(20, 150, 150));
        nodeColor.put(NodeType.SERVICE, new Color(20, 100, 200));
        nodeColor.put(NodeType.LOISIR, new Color(20, 0, 250));

        roadColor.put(RoadType.AUTOROUTE, new Color(100, 20, 100));
        roadColor.put(RoadType.NATIONALE, new Color(200, 20, 150));
        roadColor.put(RoadType.DEPARTMENTALE, new Color(150, 20, 200));
    }

    public void callSpecialDraw(NodeType type) {
        specialDraw = type;
        repaint();
    }
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        clicked = null;
        

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.getNode(i))){
                clicked = this.tab.getNode(i);
            }
        }
        if(clicked != null) {
            if (actionChoice.getIndicationType() == 0) {
                drawNeighbours = true;
                drawPath = false;
                actionChoice.hideIndication();
                repaint();
            } else if (actionChoice.getIndicationType() == 1) {
                if (tmp1 == null) {
                    tmp1 = clicked;
                } else {
                    tmp2 = clicked;
                    actionChoice.hideIndication();
                    drawPath = true;
                }
                drawNeighbours = false;
                repaint();
            } else if (actionChoice.getIndicationType() == 2) {
                if (tmp1 == null) {
                    tmp1 = clicked;
                } else {
                    tmp2 = clicked;
                    actionChoice.hideIndication();
                    compare = true;
                }

                repaint();
            } else if (actionChoice.getIndicationType() == 3) {
                if (clicked != null) {
                    // Option pane for delete node
                    int n = JOptionPane.showConfirmDialog(null,
                            "Voulez-vous vraiment supprimer " + clicked.getName() +
                                    " ?", "Suppression",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        main.removeNode(clicked);
                    }
                    actionChoice.hideIndication();
                }
            } else if(clicked != null) {
                //smViewer.infoAboutSm();
            }
        }
        if (clicked == null) {
            nothingTouched = true;
        }
        else {
            nothingTouched = false;
        }
        this.repaint();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

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

    public Sommet getTmp1(){
        return tmp1;
    }
    public Sommet getTmp2(){
        return tmp2;
    }
    public Sommet getTmp3(){
        return tmp3;
    }
    public void setTmp1(Sommet tmp1){
        this.tmp1 = tmp1;
    }
    public void setTmp2(Sommet tmp2){
        this.tmp2 = tmp2;
    }
    public void setTmp3(Sommet tmp3){
        this.tmp3 = tmp3;
    }
    public void setDrawSpecialPath(boolean drawSpecialPath){
        this.drawSpecialPath = drawSpecialPath;
    }
    public String getActionStr(){
        if(drawNeighbours)
            return "Voisins";
        else if(drawPath)
            return "Distance";
        else if(compare)
            return "Comparaison";
        return null;
    }
    public int getAction(){
        if(drawNeighbours)
            return 0;
        else if(drawPath)
            return 1;
        else if(compare)
            return 2;
        return -1;
    }
    public void setDrawNeighboursModified(int TTL){
        drawNeighboursModified = TTL;
    }
    public void setNothingTouched(boolean nothingTouched){
        this.nothingTouched = nothingTouched;
    }
    public void setDragging(boolean dragging){
        this.dragging = dragging;
    }
    public void setDragStart(Point dragStart){
        this.dragStart = dragStart;
    }

    public void move(Point move) {
        setLocation(getX() + move.x, getY() + move.y);
    }
}
