import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Color;

public class DrawCircle extends JFrame {

    ArrayList<Liste> tab;
    private int width;
    private int height;

    public DrawCircle(ArrayList<Liste> tab, int width, int height){

        this.width = width;
        this.height = height;
        this.tab = tab;

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

        for(int i = 0; i < this.tab.size(); i++){
            if((b*150+150) > this.width){
                System.out.print("heyy" + a);
                a++;
                b = 0;
            }
            g2d.setColor(Color.black);
            g2d.drawOval(50+150*b, 50+150*a, 50, 50);
            b++;
            System.out.println("count i = " + this.tab.size());
        }
    }
    public void paintLink(Graphics g, Liste ls){

        int length = ls.lenghtList();
        for(int i = 0; i < length; i++){
            
        }
    }
}