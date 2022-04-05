import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

public class DrawCircle extends JFrame {

    ArrayList<Liste> tab;
    private int width;
    private int height;

    private Graphics g;
    private Graphics2D g2d;

    Color backgroundColor = new Color(30,30,30);
    Timer timer;
    JTextField yourInpuFieldt;
    JTextField yourInpuFieldt2;

    Sommet tmp1;
    Sommet tmp2;

    PathSearcher ps;
    JPanel contentPane = new JPanel();
    Map map;
    SmViewer smViewer;
    ActionChoice actionChoice;

    public DrawCircle(ArrayList<Liste> tab, int width, int height, PathSearcher ps){

        this.width = width;
        this.height = height;
        this.tab = tab;
        this.ps = ps;
        map = new Map(1400, 800, tab);
        smViewer = new SmViewer(width-1400, height, tab);
        actionChoice = new ActionChoice(width-(width-1400), height-800, map);

        
        
        //contentPane.add(map);
        //add(contentPane);
        //contentPane.setVisible(true);
        //setContentPane(contentPane);

        //smViewer.setLocation(1400, 0);
        
        setSize(this.width, this.height);
        setPosSm();
        setTitle("Graphe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addResizeEvent();

        contentPane.setSize(width, height);
        contentPane.setLayout(new BorderLayout());

        contentPane.add(map, BorderLayout.CENTER);
        
        contentPane.add(smViewer, BorderLayout.EAST);

        contentPane.add(actionChoice, BorderLayout.SOUTH);
        //contentPane.add(map);
        //contentPane.add(smViewer);

        add(contentPane);

        setVisible(true);
    }

    public void update() {
        update(this.getGraphics());
    }
/*
    @Override
    public void paint(Graphics g) {
    }*/

    public void realPaint(){

        this.g = contentPane.getGraphics();
        this.g2d = (Graphics2D) this.g;
        
        setLayout(new FlowLayout());
        setVisible(true);
        
        contentPane.setLayout(null);
        contentPane.setSize(width, height);
        contentPane.setVisible(true);
        //setLocationRelativeTo(null);
        

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
    }

    public void setPosSm(){

        Double minX = Double.POSITIVE_INFINITY;
        Double maxX = Double.NEGATIVE_INFINITY;

        Double minY = Double.POSITIVE_INFINITY;
        Double maxY = Double.NEGATIVE_INFINITY;
        
        Double tmp;

        for(int i = 0; i < tab.size(); i++){
            if((tmp = tab.get(i).getOrigin().getValue().getLocX()) < minX){
                minX = tmp;
            }
            if((tmp = tab.get(i).getOrigin().getValue().getLocX()) > maxX){
                maxX = tmp;
            }
            if((tmp = tab.get(i).getOrigin().getValue().getLocX()) < minY){
                minY = tmp;
            }
            if((tmp = tab.get(i).getOrigin().getValue().getLocX()) > maxY){
                maxY = tmp;
            }
        }
        maxX *= 1.1;
        maxY *= 1.2;
        for(int i = 0; i < tab.size(); i++){
            System.out.println(tab.get(i).getOrigin().getValue().getLocY()/maxY);
            tab.get(i).getOrigin().getValue().setLocX((tab.get(i).getOrigin().getValue().getLocX()/maxX)*1400);
            tab.get(i).getOrigin().getValue().setLocY((tab.get(i).getOrigin().getValue().getLocY()/maxY)*600);
        }
        for(int i = 0; i < tab.size(); i++){
            tab.get(i).getOrigin().getValue().setX((int)(tab.get(i).getOrigin().getValue().getLocX()));
            tab.get(i).getOrigin().getValue().setY((int)(tab.get(i).getOrigin().getValue().getLocY()));
        }
    }

    public void paintLink(Liste ls, int indice2, boolean exception, int TTL){

        int length = ls.lenghtList();
        Cell actuel = ls.getOrigin().getSuivant();
        

        for(int i = 1; i < length; i++){
            int indice = findSm(actuel.getValue().getName());

            Cell cl = this.tab.get(indice).getOrigin();
            if(exception){
                g2d.setStroke(new BasicStroke(7));
                g2d.setColor(Color.cyan);
                g2d.drawLine(
                    this.tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                    this.tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                    cl.getValue().getX()+25, // x2
                    cl.getValue().getY()+25 // y2
                    );
                g2d.drawOval(cl.getValue().getX()-2, 
                    cl.getValue().getY()-2, 50+4, 50+4);
                if(TTL > 0)
                    paintLink(this.tab.get(indice), indice, exception, TTL-1);
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(this.getRoadColor(actuel)); 
            g2d.drawLine(
                this.tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                this.tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                cl.getValue().getX()+25, // x2
                cl.getValue().getY()+25 // y2
                );
            actuel = actuel.getSuivant();
        }
        g2d.setStroke(new BasicStroke(3));
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

    public void setPos(Sommet sm, int x, int y){

        sm.setX(x);
        sm.setY(y);
    }

    public Color nextColorR(){
        return new Color(Color.black.getRed()+50, Color.black.getGreen(), Color.black.getBlue());
    }
    public Color nextColorG(){
        return new Color(Color.black.getRed(), Color.black.getGreen()+50, Color.black.getBlue());
    }
    public Color nextColorB(){
        return new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue()+50);
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

    public void addResizeEvent(){
        addComponentListener((ComponentListener) new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                update();
                Rectangle r = getBounds();
                setWidth(r.width);
                setHeight(r.height);
                setSize(r.width, r.height);
                paint(getGraphics());
            }
        });
    }
    public boolean checkSmArea(int x, int y, Sommet sm){

        if(sm.getX() >= x-50 &&sm.getY() > y-50 && sm.getX() < x && sm.getY() < y){
            return true;
        }
        return false;
    }

    public void moveAllPosition(){

        /*
        AffineTransform at = new AffineTransform();
        at.scale(1.5,1.5);
        g2d.transform(at);
        paint(g);
        */
    }



    public ArrayList<Liste> getTab() {
        return this.tab;
    }

    public void setTab(ArrayList<Liste> tab) {
        this.tab = tab;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}