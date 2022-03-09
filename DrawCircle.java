import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class DrawCircle extends JFrame {

    ArrayList<Liste> tab;
    private int width;
    private int height;
    Color color = new Color(0,0,0);

    public DrawCircle(ArrayList<Liste> tab, int width, int height){

        this.width = width;
        this.height = height;
        this.tab = tab;

        setTitle("Graphe");
        setSize(this.width, this.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addResizeEvent();
        
        setVisible(true);
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



            paintLink(g, this.tab.get(i), i);

            g2d.setColor(this.getSommetColor(this.tab.get(i).getOrigin()));   
            Ellipse2D.Double circle = new Ellipse2D.Double(50+150*b, 50+150*a, 50, 50);
            g2d.fill(circle);
            g2d.drawOval(50+150*b, 50+150*a, 50, 50);
            b++;
        }
        plainCircle(g2d);
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

    public void paintLink(Graphics g, Liste ls, int indice2){

        int length = ls.lenghtList();
        Cell actuel = ls.getOrigin().getSuivant();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));

        for(int i = 1; i < length; i++){
            int indice = findSm(actuel.getValue().getName());

            Cell cl = this.tab.get(indice).getOrigin();

            g2d.setColor(this.getRoadColor(actuel));            
            g.drawLine(
                this.tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                this.tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                cl.getValue().getX()+25, // x2
                cl.getValue().getY()+25 // y2
                );
            actuel = actuel.getSuivant();
        }
        g2d.setStroke(new BasicStroke(2));
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