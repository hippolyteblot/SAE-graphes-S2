

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class SmViewer extends JPanel {
    
    int width;
    int height;
    NodeList tab;
    JLabel distanceLabel;
    Map map;
    PathFinder pf;

    JTextField distance = new JTextField("test");
    JLabel jl2 = new JLabel("Distance :");
    JPanel inputArea = new JPanel();
    JPanel SmInfo = new JPanel();
    JPanel ActionInfo = new JPanel();
    JLabel intro;

    JLabel name;
    JLabel type;
    JLabel nbNeighLabel;
    JLabel imageLabel;
    JLabel typeLabel;
    Main main;



    public SmViewer() {

    }

    public SmViewer(int width, int height, NodeList tab, Map map, PathFinder pf, Main main) throws IOException {

        this.tab = tab;
        this.main = main;
        this.width = width;
        this.height = height;
        this.map = map;
        this.pf = pf;
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setVisible(true);
    }

    public void infoAboutSm() throws IOException {
        this.removeAll();

        System.out.println("Nom : : " + map.getClicked().getName());
        name = new JLabel("Nom : " + map.getClicked().getName());
        type = new JLabel("Type : " + map.getClicked().getStringType());
        int nbNeigh = tab.get(tab.getNodeIndex(map.getClicked())).size();
        nbNeighLabel = new JLabel("Nombre de voisins : " + String.valueOf(nbNeigh));

        name.setFont(new Font("arial", Font.PLAIN, 22));
        type.setFont(new Font("arial", Font.PLAIN, 22));

        add(name);
        add(type);
        //add(imageLabel);
        repaint();

    }
    public void infoAboutAction(int type, int TTL){
        this.removeAll();
        switch (type) {
            case 0:
                intro = new JLabel("Obtenir les voisins :");
                JLabel listIntro = new JLabel("Liste des voisins de " + map.getClicked().getName()
                        + " : ");
                System.out.println(TTL);
                String list = pf.getNeighbors(map.getClicked(), TTL).toString();
                list = list.replace("[", "<html>-");
                list = list.replace("]", "</html>");
                list = list.replace(",", "<br>-");
                list = list.replace(" ", "");

                JLabel listNeigh = new JLabel(list);


                listIntro.setFont(new Font("arial", Font.PLAIN, 22));
                listNeigh.setFont(new Font("arial", Font.PLAIN, 20));
                listNeigh.setBackground(Color.white);
                listNeigh.setOpaque(true);
                listNeigh.setBorder(BorderFactory.createLineBorder(Color.black));
                add(listIntro);
                add(listNeigh);

                JLabel changeTTl = new JLabel("Obtenir des voisins à n-distance");
                changeTTl.setFont(new Font("arial", Font.PLAIN, 22));
                add(changeTTl);

                JButton button = new JButton("Obtenir");
                JSlider slider = getSliderPanel();
                add(slider);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        map.setDrawNeighboursModified(slider.getValue());
                        map.repaint();
                    }
                });
                add(button);
                break;
            case 1:
                intro = new JLabel("Plus court chemin :");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                JLabel listIntro2 = new JLabel("Aller de " + map.getTmp1() + " à " + map.getTmp2());
                listIntro2.setFont(new Font("arial", Font.PLAIN, 22));
                add(intro);
                add(listIntro2);
                Sommet s1 = map.getTmp1();
                Sommet s2 = map.getTmp2();
                int distance = pf.getDistance(s1, s2);
                distanceLabel = new JLabel("Distance : " + distance + " km");
                distanceLabel.setFont(new Font("arial", Font.PLAIN, 22));
                add(distanceLabel);
                JLabel option = new JLabel("Option :");
                option.setFont(new Font("arial", Font.PLAIN, 22));
                add(option);
                JPanel specifiLocationPanel = new JPanel();
                JLabel specifiLocation = new JLabel("Spécifiez un lieu par lequel passer :");
                specifiLocation.setFont(new Font("arial", Font.PLAIN, 22));
                specifiLocationPanel.add(specifiLocation);
                JComboBox<String> comboBox = new JComboBox<>(
                        tab.getAllNodes().toString().replace("[", "").replace("]", "")
                                .replace(" ", "").split(","));
                comboBox.setFont(new Font("arial", Font.PLAIN, 22));
                comboBox.setSelectedItem(null);
                specifiLocationPanel.add(comboBox);
                add(specifiLocationPanel);
                JPanel unknownLocationPanel = new JPanel();
                JLabel unknownLocation = new JLabel("Spécifiez un type de lieu par lequel passer :");
                unknownLocation.setFont(new Font("arial", Font.PLAIN, 22));
                unknownLocationPanel.add(unknownLocation);
                JComboBox<String> comboBox2 = new JComboBox<>(new String[]{"Ville", "Restaurant", "Service", "Loisir"});
                comboBox2.setFont(new Font("arial", Font.PLAIN, 22));
                unknownLocationPanel.add(comboBox2);
                add(unknownLocationPanel);

                add(getPathButton(s1, s2, comboBox, comboBox2));

                break;
            case 2:
                intro = new JLabel("Comparaison de deux lieux :");
                JLabel label = new JLabel("Compare " + map.getTmp1().getName() + " and " + map.getTmp2().getName());
                JLabel mostOpen = new JLabel("Most open : " +
                        pf.mostOpen(map.getTmp1(), map.getTmp2(), NodeList.NodeType.VILLE));
                JLabel mostGourmet = new JLabel("The most gourmet : " +
                        pf.mostOpen(map.getTmp1(), map.getTmp2(), NodeList.NodeType.RESTAURANT));
                JLabel mostCultural = new JLabel("The most cultural : " +
                        pf.mostOpen(map.getTmp1(), map.getTmp2(), NodeList.NodeType.LOISIR));
                distanceLabel = new JLabel("Distance : " + pf.getDistance(map.getTmp1(), map.getTmp2()) + " km");

                add(label);
                add(Box.createRigidArea(new Dimension(0, 10)));
                add(label);
                add(Box.createRigidArea(new Dimension(0, 30)));

                add(mostOpen);
                add(Box.createRigidArea(new Dimension(0, 10)));
                add(mostGourmet);
                add(Box.createRigidArea(new Dimension(0, 10)));
                add(mostCultural);
                add(Box.createRigidArea(new Dimension(0, 10)));
                add(distanceLabel);
                add(Box.createRigidArea(new Dimension(0, 10)));
                Font font = new Font("Arial", Font.PLAIN, 22);
                Font littleFont = new Font("Arial", Font.PLAIN, 20);
                label.setFont(font);
                mostOpen.setFont(littleFont);
                mostGourmet.setFont(littleFont);
                mostCultural.setFont(littleFont);
                distanceLabel.setFont(littleFont);
                break;
            case 3:
                JLabel addSm = new JLabel("Ajouter un lieu");
                addSm.setFont(new Font("arial", Font.PLAIN, 22));
                add(addSm);
                JPanel namePanel = new JPanel();
                JLabel name = new JLabel("Nom : ");
                name.setFont(new Font("arial", Font.PLAIN, 22));
                namePanel.add(name);
                JTextField nameField = new JTextField(20);
                nameField.setFont(new Font("arial", Font.PLAIN, 22));
                namePanel.add(nameField);
                add(namePanel);
                JPanel latPanel = new JPanel();
                JLabel latitude = new JLabel("Latitude : ");
                latPanel.add(latitude);
                JTextField latitudeField = new JTextField(20);
                latPanel.add(latitudeField);
                add(latPanel);
                JPanel lonPanel = new JPanel();
                JLabel longitude = new JLabel("Longitude : ");
                lonPanel.add(longitude);
                JTextField longitudeField = new JTextField(20);
                latitude.setFont(new Font("arial", Font.PLAIN, 22));
                longitude.setFont(new Font("arial", Font.PLAIN, 22));
                latitudeField.setFont(new Font("arial", Font.PLAIN, 22));
                longitudeField.setFont(new Font("arial", Font.PLAIN, 22));

                lonPanel.add(longitudeField);
                add(lonPanel);
                JPanel typePanel = new JPanel();
                typeLabel = new JLabel("Type : ");
                typeLabel.setFont(new Font("arial", Font.PLAIN, 22));
                typePanel.add(typeLabel);
                JComboBox<String> typeBox = new JComboBox<String>();
                typeBox.addItem("Restaurant");
                typeBox.addItem("Culturel");
                typeBox.addItem("Service");
                typeBox.addItem("Ville");
                typeBox.setFont(new Font("arial", Font.PLAIN, 22));
                typePanel.add(typeBox);
                add(typePanel);
                add(getAddButton(nameField, latitudeField, longitudeField, typeBox));
                break;
            case 4:
        }
        updateUI();
    }
    public JSlider getSliderPanel(){
        JPanel sliderPanel = new JPanel();
        JSlider slider = new JSlider(1, 5);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setValue(1);
        return slider;
    }
    public JButton getAddButton(JTextField nameField, JTextField latitudeField, JTextField longitudeField, JComboBox<String> typeBox){
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String lat = latitudeField.getText();
                String lon = longitudeField.getText();
                String type = typeBox.getSelectedItem().toString();
                if (name.equals("") || lat.equals("") || lon.equals("")) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
                }
                else {
                    Sommet newSm = null;
                    try {
                        double latD = Double.parseDouble(lat);
                        double lonD = Double.parseDouble(lon);
                        if (type.equals("Restaurant")) {
                            NodeList.NodeType smType = NodeList.NodeType.RESTAURANT;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        else if (type.equals("Culturel")) {
                            NodeList.NodeType smType = NodeList.NodeType.LOISIR;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        else if (type.equals("Service")) {
                            NodeList.NodeType smType = NodeList.NodeType.SERVICE;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        else if (type.equals("Ville")) {
                            NodeList.NodeType smType = NodeList.NodeType.VILLE;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Veuillez choisir un type");
                        }
                    }
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer des coordonnées valides");
                    }
                    if (newSm != null) {
                        main.addSommet(newSm);
                        JOptionPane.showMessageDialog(null, "Sommet ajouté");
                    }
                }
            }
        });

        addButton.setFont(new Font("arial", Font.PLAIN, 22));
        return addButton;
    }
    public JButton getPathButton(Sommet s1, Sommet s2, JComboBox<String> comboBox, JComboBox<String> comboBox2){
        JButton button2 = new JButton("Trouver un chemin");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedItem() != null) {
                    map.setTmp1(s1);
                    System.out.println(map.getTmp1().getName());
                    map.setTmp3(s2);
                    map.setTmp2(tab.getNodeByName(comboBox.getSelectedItem().toString()));
                    map.setDrawSpecialPath(true);
                    map.repaint();
                } else if (comboBox2.getSelectedItem() != null) {
                    map.setTmp1(s1);
                    map.setTmp3(s2);
                    NodeList.NodeType smType = null;
                    if (comboBox2.getSelectedItem().toString().equals("Ville")) {
                        smType = NodeList.NodeType.VILLE;
                    } else if (comboBox2.getSelectedItem().toString().equals("Restaurant")) {
                        smType = NodeList.NodeType.RESTAURANT;
                    } else if (comboBox2.getSelectedItem().toString().equals("Service")) {
                        smType = NodeList.NodeType.SERVICE;
                    } else if (comboBox2.getSelectedItem().toString().equals("Loisir")) {
                        smType = NodeList.NodeType.LOISIR;
                    }
                    map.setTmp2(pf.getCloserNode(s1, s2, smType));
                    map.setDrawSpecialPath(true);
                    map.repaint();

                }
            }
        });
        button2.setFont(new Font("arial", Font.PLAIN, 22));
        return button2;
    }
}
