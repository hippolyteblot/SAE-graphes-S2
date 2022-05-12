import javax.swing.*;
import java.awt.*;

public class CompareWindow extends JFrame {

    private JPanel contentPane = new JPanel();
    private PathFinder pathFinder;
    private Map map;
    private NodeList tab;

    public CompareWindow(Map map, NodeList tab) {
        super("Compare");
        this.map = map;
        this.tab = tab;
        this.pathFinder = new PathFinder(tab);
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        buildComponents();
        pack();
        setVisible(true);
    }

    public void buildComponents(){
        JLabel label = new JLabel("Compare " + map.getTmp1().getName() + " and " + map.getTmp2().getName());
        JLabel mostOpen = new JLabel("Most open : " + pathFinder.mostOpen(map.getTmp1(), map.getTmp2(),
                NodeList.NodeType.VILLE));
        JLabel mostGourmet = new JLabel("The most gourmet : " + pathFinder.mostOpen(map.getTmp1(), map.getTmp2(),
                NodeList.NodeType.RESTAURANT));
        JLabel mostCultural = new JLabel("The most cultural : " + pathFinder.mostOpen(map.getTmp1(), map.getTmp2(),
                NodeList.NodeType.LOISIR));
        JLabel distance = new JLabel("Distance : " + pathFinder.getDistance(map.getTmp1(), map.getTmp2()) + " km");

        contentPane.add(label);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(label);
        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));

        contentPane.add(mostOpen);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(mostGourmet);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(mostCultural);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(distance);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        Font font = new Font("Arial", Font.PLAIN, 20);
        Font littleFont = new Font("Arial", Font.PLAIN, 16);
        label.setFont(font);
        mostOpen.setFont(littleFont);
        mostGourmet.setFont(littleFont);
        mostCultural.setFont(littleFont);
        distance.setFont(littleFont);
    }
}
