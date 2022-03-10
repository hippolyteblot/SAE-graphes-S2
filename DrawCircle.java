import javax.swing.*;

import java.util.Timer;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

public class DrawCircle extends JFrame implements MouseListener {

    ArrayList<Liste> tab;
    private int width;
    private int height;
    Color color = new Color(0,0,0);
    Timer timer;

    public DrawCircle(ArrayList<Liste> tab, int width, int height){

        this.width = width;
        this.height = height;
        this.tab = tab;

        setTitle("Graphe");
        setSize(this.width, this.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addResizeEvent();
        addMouseListener(this);

        setVisible(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerUpdate(), (long) 1, (long) 1);

        
    }

    public class TimerUpdate extends TimerTask {

        public void run() {
            update();
        }
    }

    public void update() {
        update(this.getGraphics());
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        int a = 0;
        int b = 0;
        

        g2d.setStroke(new BasicStroke(2));
        setPos();

        for(int i = 0; i < this.tab.size(); i++){
            if((b*150+150) > this.width){
                a++;
                b = 0;
            }
            g2d.setColor(Color.black);
            plainCircle(g2d);



            paintLink(g, this.tab.get(i), i, false);

            g2d.setColor(this.getSommetColor(this.tab.get(i).getOrigin()));   
            Ellipse2D.Double circle = new Ellipse2D.Double(50+150*b, 50+150*a, 50, 50);
            g2d.fill(circle);
            g2d.drawOval(50+150*b, 50+150*a, 50, 50);
            b++;
        }
    }

    public void plainCircle(Graphics2D g2d){

        int a = 0;
        int b = 0;

        for(int i = 0; i < this.tab.size(); i++){
            if((b*150+150) > this.width){
                a++;
                b = 0;
            }

            g2d.setColor(this.getSommetColor(this.tab.get(i).getOrigin()));   
            Ellipse2D.Double circle = new Ellipse2D.Double(50+150*b, 50+150*a, 50, 50);
            g2d.fill(circle);
            b++;
        }
    }
    public void setPos(){

        int a = 0;
        int b = 0;
        for(int i = 0; i < this.tab.size(); i++){
            if((b*150+150) > this.width){
                a++;
                b = 0;
            }
            setPos(this.tab.get(i).getOrigin().getValue(), 50+150*b, 50+150*a);
            b++;
        }
    }

    public void paintLink(Graphics g, Liste ls, int indice2, boolean exception){

        int length = ls.lenghtList();
        Cell actuel = ls.getOrigin().getSuivant();

        Graphics2D g2d = (Graphics2D) g;

        for(int i = 1; i < length; i++){
            int indice = findSm(actuel.getValue().getName());

            Cell cl = this.tab.get(indice).getOrigin();
            if(exception){
                g2d.setStroke(new BasicStroke(7));
                g2d.setColor(Color.cyan);
                g.drawLine(
                    this.tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                    this.tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                    cl.getValue().getX()+25, // x2
                    cl.getValue().getY()+25 // y2
                    );
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(this.getRoadColor(actuel)); 
            g.drawLine(
                this.tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                this.tab.get(indice2).getOrigin().getValue().getY()+25, // y1
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

    public void setPos(Sommet sm, int x, int y){

        sm.setX(x);
        sm.setY(y);
    }

    public Color nextColorR(){
        return new Color(this.color.getRed()+50, this.color.getGreen(), this.color.getBlue());
    }
    public Color nextColorG(){
        return new Color(this.color.getRed(), this.color.getGreen()+50, this.color.getBlue());
    }
    public Color nextColorB(){
        return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue()+50);
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

    // Click
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {


        int x = e.getX();
        int y = e.getY();
        Graphics2D g2d = (Graphics2D) this.getGraphics();


        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.get(i).getOrigin().getValue())){
                g2d.setColor(Color.white);
                g2d.fillRect(0, 0, width, height);
                g2d.setStroke(new BasicStroke(5));
                g2d.setColor(Color.cyan);
                g2d.drawOval(this.tab.get(i).getOrigin().getValue().getX()-2, 
                    this.tab.get(i).getOrigin().getValue().getY()-2, 50+4, 50+4);
                paintLink(this.getGraphics(), this.tab.get(i), i, true);
                paint(this.getGraphics());
            }
        }
        
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
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