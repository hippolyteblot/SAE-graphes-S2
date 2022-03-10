import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Color;

public class DrawCircle extends JFrame {

    ArrayList<Liste> tab;
    private int width;
    private int height;
    private JLabel x;

    public DrawCircle(ArrayList<Liste> tab, int width, int height){

        this.width = width;
        this.height = height;
        this.tab = tab;
        this.x = new JLabel("test");


        setTitle("Graphe");
        setSize(this.width, this.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {


        Graphics2D g2d = (Graphics2D) g;
        int a = 0;
        int b = 0;
        setPos();
        JPanel p = new JPanel();
        p.add(this.x);
        this.add(p);


        for(int i = 0; i < this.tab.size(); i++){
            if((b*150+150) > this.width){
                a++;
                b = 0;
            }
            g2d.setColor(Color.black);
            g2d.drawOval(50+150*b, 50+150*a, 50, 50);

            b++;
            paintLink(g, this.tab.get(i), i);
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

        for(int i = 1; i < length; i++){
            int indice = findSm(actuel.getValue().getName());

            Cell cl = this.tab.get(indice).getOrigin();
            
            g.drawLine(
                this.tab.get(indice2).getOrigin().getValue().getX()+25, // x1
                this.tab.get(indice2).getOrigin().getValue().getY()+25, // y1
                cl.getValue().getX()+25, // x2
                cl.getValue().getY()+25 // y2
                );
            actuel = actuel.getSuivant();
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
}