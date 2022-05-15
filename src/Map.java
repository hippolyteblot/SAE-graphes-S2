
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


public class Map extends JPanel implements MouseListener {

    HashMap<Road.RoadType, Color> roadColor = new HashMap<Road.RoadType, Color>();
    HashMap<NodeList.NodeType, Color> nodeColor = new HashMap<NodeList.NodeType, Color>();

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
    FrameManager fm;
    int drawNeighboursModified = 0;
    Main main;


    public Map(int width, int height, NodeList tab, FrameManager fm, Main main) {

        this.tab = tab;
        this.main = main;
        this.width = width;
        this.fm = fm;

        // Dark theme
        //setBackground(new Color(62, 61, 61));

        this.width = width;
        this.height = height;

        setColorMap();

        setPosSm();

        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        addMouseListener(this);
        setVisible(true);

        pf = new PathFinder(tab);
    }

    public void drawName(Sommet sm, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x = sm.getX();
        int y = sm.getY();
        g2d.setColor(Color.BLACK);
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

        paintEverything(g);

        if(drawSpecialPath){
            System.out.println("drawSpecialPath " + tmp1.getName() + " - " + tmp2.getName());
            drawSpecialPath(g);
            smViewer.infoAboutAction(1, 0);
            tmp1 = null;
            tmp2 = null;
            tmp3 = null;
            drawSpecialPath = false;
        }

        if(drawNeighboursModified > 0){
            drawNeighbours = true;
            showNeighbours(clicked, drawNeighboursModified, g);
            smViewer.infoAboutAction(0, drawNeighboursModified);
            drawNeighboursModified = 0;
            drawNeighbours = false;
        }
        if(clicked != null && drawNeighbours){
            showNeighbours(clicked, 1, g);

            actionChoice.setIndicationType(-1);
            smViewer.infoAboutAction(0, 1);
            drawNeighbours = false;
        } else if (drawPath) {
            drawPath(g);

            actionChoice.setIndicationType(-1);
            smViewer.infoAboutAction(1, 0);
            tmp1 = null;
            tmp2 = null;
            drawPath = false;
        } else if (compare) {

            actionChoice.setIndicationType(-1);
            smViewer.infoAboutAction(2, 1);
            tmp1 = null;
            tmp2 = null;
            compare = false;
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
    public void drawSpecialPath(Graphics g) {
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

        ArrayList<Sommet> ls2 =  pf.dijkstra(tmp2, tmp3);
        System.out.println();
        for(int i = 0; i < ls2.size()-1; i++){
            Sommet sm1 = ls2.get(i);
            Sommet sm2 = ls2.get(i+1);

            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(4));

            paintSpecificLink(sm1, sm2, g);
            if(i == 0)
                System.out.print(ls2.get(i));
            else
                System.out.print(" -> " + ls2.get(i));
        }
        System.out.println(" -> " + tmp2.getName());
        ls.addAll(ls2);
    }


    public void paintEverything(Graphics g){
        paintAllEllipses(g);
        paintAllLinks(g);
    }
    public void paintAllLinks(Graphics g){
        for(NeighborsList s : tab){
            for(Cell c : s.getNeighbors()){
                paintLink(s.getSommet(), c.getValue(), roadColor.get(c.getRoute().getType()), g, 3);
            }
        }
    }
    public void paintAllEllipses(Graphics g){
        for(int i = 0; i < this.tab.size(); i++){
            paintEllipse(g, this.tab.getNode(i));
            drawName(this.tab.getNode(i), g);
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
        g2d.setColor(nodeColor.get(s.getType()));
        g2d.fill(circle);
    }
    public void findEquivalence(){
        for(int i = 0; i < this.tab.size(); i++){
            for(int j = 0; j < this.tab.get(i).size(); j++){
                for(int k = 1; k < this.tab.size(); k++){
                    if (tab.getNode(k).getName().equals(tab.get(i).get(j).getValue().getName())){
                        tab.get(i).get(j).setValue(tab.getNode(k));
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


    /*
    public Color getRoadColor(Cell cell){
        return switch (cell.getRoute().getType()) {
            case AUTOROUTE -> new Color(100, 20, 100);
            case NATIONALE -> new Color(200, 20, 150);
            case DEPARTMENTALE -> new Color(150, 20, 200);
            default -> new Color(0, 0, 0);
        };
    }

    public Color getSommetColor(Sommet sm){
        new Color(0, 0, 0);
        return switch (sm.getType()) {
            case VILLE -> new Color(20, 200, 100);
            case RESTAURANT -> new Color(20, 150, 150);
            default -> new Color(20, 100, 200);
        };
    }
     */

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

    public void setColorMap(){
        nodeColor.put(NodeList.NodeType.VILLE, new Color(20, 200, 100));
        nodeColor.put(NodeList.NodeType.RESTAURANT, new Color(20, 150, 150));
        nodeColor.put(NodeList.NodeType.SERVICE, new Color(20, 100, 200));
        nodeColor.put(NodeList.NodeType.LOISIR, new Color(20, 100, 200));

        roadColor.put(Road.RoadType.AUTOROUTE, new Color(100, 20, 100));
        roadColor.put(Road.RoadType.NATIONALE, new Color(200, 20, 150));
        roadColor.put(Road.RoadType.DEPARTMENTALE, new Color(150, 20, 200));
    }


    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        clicked = null;
        

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
            }
        }
        this.repaint();
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
}
