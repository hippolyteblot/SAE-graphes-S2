import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawCircle extends JFrame implements MouseListener {

    ArrayList<Liste> tab;
    private int width;
    private int height;
    Color backgroundColor = new Color(30,30,30);
    Timer timer;
    JTextField yourInpuFieldt;
    JTextField yourInpuFieldt2;

    Sommet tmp1;
    Sommet tmp2;

    PathSearcher ps;

    public DrawCircle(ArrayList<Liste> tab, int width, int height, PathSearcher ps){

        this.width = width;
        this.height = height;
        this.tab = tab;
        this.ps = ps;

        yourInpuFieldt = new JTextField(16);
        yourInpuFieldt2 = new JTextField(16);

        setTitle("Graphe");
        setSize(this.width, this.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addResizeEvent();
        addMouseListener(this);

        setVisible(true);

        //this.getGraphics().setColor(backgroundColor);
        //this.getGraphics().fillRect(0, 0, width, height);
        /*timer = new Timer();
        timer.scheduleAtFixedRate(new TimerUpdate(), (long) 1, (long) 1);*/
        
    }

    public void update() {
        update(this.getGraphics());
    }

    @Override
    public void paint(Graphics g) {
        setLayout(new BorderLayout());
        setVisible(true);

        

        int a = 0;
        int b = 0;
        
        JPanel p = new JPanel();
        p.setLayout(null);
        setLocationRelativeTo(null);
        p.setSize(width, height);
        p.setLocation(new Point(-8,-31));
        p.setVisible(true);
        add(p);
        

        Graphics g2 = p.getGraphics();

        Graphics2D g2d2 = (Graphics2D) g2;
        

        g2d2.setStroke(new BasicStroke(2));
        setPos();

        for(int i = 0; i < this.tab.size(); i++){
            if((b*150+150) > this.width){
                a++;
                b = 0;
            }
            /*
            if(false){
                JLabel j2 = new JLabel(this.tab.get(i).getOrigin().getValue().getName());
                j2.setBounds(new Rectangle(50+150*b,50+150*a, 50, 50));
                j2.setBorder(new LineBorder(Color.RED, 4));
                add(j2);
                
            }
            */

            g2d2.setColor(this.getSommetColor(this.tab.get(i).getOrigin()));   
            Ellipse2D.Double circle = new Ellipse2D.Double(50+150*b, 50+150*a, 50, 50);
            g2d2.fill(circle);
            plainCircle(g2d2);


            paintLink(g2, this.tab.get(i), i, false, 0);
            
            g2d2.drawOval(50+150*b, 50+150*a, 50, 50);

            b++;
            
        }
        

        //p.setOpaque(false);
        //p.setVisible(true);
        
        //setVisible(true);
        
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

    public void paintLink(Graphics g, Liste ls, int indice2, boolean exception, int TTL){

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
                g2d.drawOval(cl.getValue().getX()-2, 
                    cl.getValue().getY()-2, 50+4, 50+4);
                if(TTL > 0)
                    paintLink(g, this.tab.get(indice), indice, exception, TTL-1);
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

    public void paintSpecificLink(Sommet sm1, Sommet sm2){

        Graphics g = getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        int indice2 = ps.indexSm2(sm1);

        for(int i = 0; i < tab.get(indice2).lenghtList(); i++){
            if(tab.get(indice2).getACell(i).getValue().getName().equals(sm2.getName())){
                g2d.setStroke(new BasicStroke(4));
                g2d.setColor(Color.blue); 
                Cell goodOne = tab.get(ps.indexSm2(sm2)).getOrigin();
                g.drawLine(
                    tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                    tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                    goodOne.getValue().getX()+25, // x2
                    goodOne.getValue().getY()+25 // y2
                    );
                System.out.println(tab.get(indice2).getACell(i).getValue().getY());
                System.out.println("Lien entre " + sm1.getName() + " et " + sm2.getName());
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
                
                g2d.fillRect(0, 0, width, height); // TODO : Colorer uniquement les zones des sommets
                g2d.setStroke(new BasicStroke(5));
                g2d.setColor(Color.cyan);
                g2d.drawOval(this.tab.get(i).getOrigin().getValue().getX()-2, 
                    this.tab.get(i).getOrigin().getValue().getY()-2, 50+4, 50+4);
                paintLink(this.getGraphics(), this.tab.get(i), i, true, 0);
            }
        }
        update(this.getGraphics());
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        Graphics2D g2d = (Graphics2D) this.getGraphics();
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        paint(getGraphics());

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.get(i).getOrigin().getValue())){
                g2d.setColor(Color.blue);
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
        Graphics2D g2d = (Graphics2D) this.getGraphics();

        for(int i = 0; i < this.tab.size(); i++){
            if(checkSmArea(x, y, this.tab.get(i).getOrigin().getValue())){
                g2d.setColor(Color.blue);
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
            g2d.setColor(Color.blue);
            g2d.setStroke(new BasicStroke(4));
            paintSpecificLink(sm1, sm2);
            System.out.print(" -> " + ls.get(i));
        }
        System.out.println();
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