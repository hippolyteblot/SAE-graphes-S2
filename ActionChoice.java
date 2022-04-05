import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionChoice extends JPanel {
    
    int width;
    int height;
    Graphics2D g2d;

    JButton neighbors = new JButton("Neighbors");
    JButton shortestPath = new JButton("Shortest path");
    JButton compare = new JButton("Compare");
    JButton addLocation = new JButton("Add location");
    JButton removeLocation = new JButton("Remove location");
    JButton addRoad = new JButton("Add road");
    JButton removeRoad = new JButton("Remove road");

    Map map;

    public ActionChoice() {

    }

    public ActionChoice(int width, int height, Map map) {

        this.width = width;
        this.height = height;
        this.map = map;

        setLayout(new GridLayout(2, 4));

        add(neighbors);
        add(shortestPath);
        add(compare);
        add(addLocation);
        add(removeLocation);
        add(addRoad);
        add(removeRoad);

        addBtnEvent();

        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    
    @Override
    protected void paintComponent(Graphics g) {

    }

    public void addBtnEvent(){

        neighbors.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                map.preparePainting();
                System.out.print("test");
            }
        });
        shortestPath.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                map.preparePainting();
                System.out.print("test");
            }
        });
    }
}