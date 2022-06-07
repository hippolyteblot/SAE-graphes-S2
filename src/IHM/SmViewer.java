package IHM;

import DataStructure.*;
import ProcessingEngine.PathBuilder;
import ProcessingEngine.PathFinder;
import Launch.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SmViewer extends JPanel {
    
    int width;
    int height;
    NodeList tab;
    JLabel distanceLabel;
    Map map;
    PathFinder pf;
    Sommet tmp;

    JPanel infoPanel;
    JLabel intro;

    JLabel name;
    JLabel type;
    JLabel nbNeighLabel;
    JLabel typeLabel;
    ActionChoice actionChoice;
    Main main;


    public SmViewer(int width, int height, NodeList tab, Map map, PathFinder pf, Main main, ActionChoice actionChoice) {

        this.tab = tab;
        this.actionChoice = actionChoice;
        this.main = main;
        this.width = width;
        this.height = height;
        this.map = map;
        this.pf = pf;
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        setVisible(true);
    }

    public void setActionChoice(ActionChoice actionChoice) {
        this.actionChoice = actionChoice;
    }
    public void infoAboutSm() {
        this.removeAll();

        name = new JLabel("Nom : " + map.getClicked().getName());
        type = new JLabel("Type : " + map.getClicked().getStringType());
        int nbNeigh = tab.get(tab.getNodeIndex(map.getClicked())).size();
        nbNeighLabel = new JLabel("Nombre de voisins : " + nbNeigh);

        name.setFont(new Font("arial", Font.PLAIN, 22));
        type.setFont(new Font("arial", Font.PLAIN, 22));

        add(name);
        add(type);
        repaint();

    }

    public void infoAboutNeigh(int TTL) {
        this.removeAll();
        intro = new JLabel("Obtenir les voisins :");
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel listIntro = new JLabel("Liste des voisins de " + map.getClicked().getName()
                + " : ");
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
        button.addActionListener(e -> {
            map.setDrawNeighboursModified(slider.getValue());
            map.repaint();
        });
        button.setFont(new Font("arial", Font.PLAIN, 22));
        add(button);
        updateUI();
        System.out.println("czufbqiou");
    }
    public void infoAboutPath() {
        this.removeAll();
        infoPanel = new JPanel();
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        intro = new JLabel("Plus court chemin :");
        intro.setFont(new Font("arial", Font.PLAIN, 22));
        Sommet s = map.getTmp2();
        if (map.getTmp3() != null) {
            s = map.getTmp3();
        }
        JLabel listIntro2 = new JLabel("Aller de " + map.getTmp1() + " à " + s);
        listIntro2.setFont(new Font("arial", Font.PLAIN, 22));
        infoPanel.add(intro);
        infoPanel.add(listIntro2);
        Sommet s1 = map.getTmp1();
        Sommet s2 = map.getTmp2();
        double distance = pf.getDistance(s1, s2);
        distance = Math.round(distance * 100.0) / 100.0;
        distanceLabel = new JLabel("Distance : " + distance + " km");
        distanceLabel.setFont(new Font("arial", Font.PLAIN, 22));
        JLabel way = new JLabel("Chemin :");
        infoPanel.add(distanceLabel);
        String pathList = pf.dijkstra(s1, s2).toString();
        pathList = pathList.replace("[", "<html>-");
        pathList = pathList.replace("]", "</html>");
        pathList = pathList.replace(",", "<br>-");
        pathList = pathList.replace(" ", "");
        JLabel labelPathList = new JLabel(pathList);
        way.setFont(new Font("arial", Font.PLAIN, 22));
        labelPathList.setFont(new Font("arial", Font.PLAIN, 20));
        labelPathList.setOpaque(true);
        labelPathList.setBorder(BorderFactory.createLineBorder(Color.black));
        infoPanel.add(way);
        infoPanel.add(labelPathList);
        add(infoPanel);
        add(Box.createGlue());
        JPanel optionPanel = new JPanel();
        optionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        JLabel option = new JLabel("Option :");
        option.setFont(new Font("arial", Font.PLAIN, 22));
        option.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(option);
        JPanel specifiLocationPanel = new JPanel();
        JLabel specifiLocation = new JLabel("Spécifiez un lieu par lequel passer :");
        specifiLocation.setFont(new Font("arial", Font.PLAIN, 22));
        specifiLocationPanel.add(specifiLocation);
        JComboBox<String> comboBox = new JComboBox<>(
                tab.getAllNodes().toString().replace("[", "").replace("]", "")
                        .replace(" ", "").split(","));
        comboBox.setFont(new Font("arial", Font.PLAIN, 20));
        comboBox.setSelectedItem(null);
        comboBox.setPreferredSize(new Dimension(150, 30));
        specifiLocationPanel.add(comboBox);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(specifiLocationPanel);
        JPanel unknownLocationPanel = new JPanel();
        JLabel unknownLocation = new JLabel("Spécifiez un type de lieu par lequel passer :");
        unknownLocation.setFont(new Font("arial", Font.PLAIN, 20));
        unknownLocationPanel.add(unknownLocation);
        JComboBox<String> comboBox2 = new JComboBox<>(new String[]{"Ville", "Restaurant", "Service", "Loisir"});
        comboBox2.setFont(new Font("arial", Font.PLAIN, 20));
        unknownLocationPanel.add(comboBox2);
        comboBox2.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(unknownLocationPanel);
        JButton pathBtn = getPathButton(s1, s2, comboBox, comboBox2);
        pathBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(pathBtn);
        add(optionPanel);
        // add static glue
        add(Box.createGlue());

        updateUI();
    }
    public void infoAboutCompare() {
        this.removeAll();
        intro = new JLabel("Comparaison de deux lieux :");
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Compare " + map.getTmp1().getName() + " and " + map.getTmp2().getName());
        JLabel mostOpen = new JLabel("Le plus ouvert : " +
                pf.mostOpen(map.getTmp1(), map.getTmp2(), NodeType.VILLE));
        JLabel mostGourmet = new JLabel("Le plus gastronomique : " +
                pf.mostOpen(map.getTmp1(), map.getTmp2(), NodeType.RESTAURANT));
        JLabel mostCultural = new JLabel("Le plus culturel : " +
                pf.mostOpen(map.getTmp1(), map.getTmp2(), NodeType.LOISIR));
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

        updateUI();
    }

    public void infoAboutAddNode() {
        this.removeAll();
        intro = new JLabel("Ajouter un lieu");
        intro.setFont(new Font("arial", Font.PLAIN, 22));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(intro);
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
        JComboBox<String> typeBox = new JComboBox<>();
        typeBox.addItem("Restaurant");
        typeBox.addItem("Culturel");
        typeBox.addItem("Service");
        typeBox.addItem("Ville");
        typeBox.setFont(new Font("arial", Font.PLAIN, 22));
        typePanel.add(typeBox);
        add(typePanel);
        add(getAddButton(nameField, latitudeField, longitudeField, typeBox));
        add(Box.createRigidArea(new Dimension(0, 300)));
        updateUI();
    }
    public void infoAboutRoads() {
        this.removeAll();
        intro = new JLabel("Gérer les routes");
        intro.setFont(new Font("arial", Font.PLAIN, 22));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(intro);
        add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel toolsPanel = new JPanel();
        JButton addRoute = new JButton("Ajouter une route");
        addRoute.setFont(new Font("arial", Font.PLAIN, 22));
        addRoute.addActionListener(e -> infoAboutAddRoad());
        toolsPanel.add(addRoute);
        JButton removeRoute = new JButton("Supprimer une route");
        removeRoute.setFont(new Font("arial", Font.PLAIN, 22));
        removeRoute.addActionListener(e -> infoAboutAction(6, 0));
        toolsPanel.add(removeRoute);
        JButton removeAllRoute = new JButton("Supprimer toutes les routes");
        removeAllRoute.setFont(new Font("arial", Font.PLAIN, 22));
        removeAllRoute.addActionListener(e -> main.removeRoads());
        toolsPanel.add(removeAllRoute);
        JButton syncRoute = new JButton("Synchroniser les routes");
        syncRoute.setFont(new Font("arial", Font.PLAIN, 22));
        syncRoute.addActionListener(e -> {
            PathBuilder pathBuilder = new PathBuilder(tab, main.getLinkFactor());
            pathBuilder.syncronize();
            main.applyNewList(pathBuilder.getTab());
            map.repaint();
        });
        toolsPanel.add(syncRoute);
        JButton buildRoute = new JButton("Construire les routes");
        buildRoute.addActionListener(e -> main.rebuild(tab));
        buildRoute.setFont(new Font("arial", Font.PLAIN, 22));
        toolsPanel.add(buildRoute);
        add(toolsPanel);

        updateUI();
    }
    public void infoAboutAddRoad() {
        this.removeAll();

        intro = new JLabel("Ajouter une route");
        intro.setFont(new Font("arial", Font.PLAIN, 22));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(intro);
        add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel roadType = new JPanel();
        JPanel nodeSelectedPanel = new JPanel();
        JComboBox<String> nodeList1 = getNodesComboBox();
        nodeSelectedPanel.add(nodeList1);
        JComboBox<String> nodeList2 = getNodesComboBox();
        nodeSelectedPanel.add(nodeList2);
        add(nodeSelectedPanel);
        JLabel roadTypeLabel = new JLabel("Type de route :");
        roadTypeLabel.setFont(new Font("arial", Font.PLAIN, 20));
        roadType.add(roadTypeLabel);
        JComboBox<String> roadTypeBox = new JComboBox<>(new String[]{"Autoroute", "Nationale", "Départementale"});
        roadTypeBox.setFont(new Font("arial", Font.PLAIN, 20));
        roadType.add(roadTypeBox);
        add(roadType);
        JPanel distancePanel = new JPanel();
        JLabel distanceLabel = new JLabel("Distance :");
        distanceLabel.setFont(new Font("arial", Font.PLAIN, 20));
        distancePanel.add(distanceLabel);
        JTextField distanceField = new JTextField();
        distanceField.setFont(new Font("arial", Font.PLAIN, 20));
        distancePanel.add(distanceField);
        distanceField.setPreferredSize(new Dimension(100, 30));
        JLabel kmLabel = new JLabel("km");
        kmLabel.setFont(new Font("arial", Font.PLAIN, 20));
        distancePanel.add(kmLabel);
        add(distancePanel);
        JButton addRoad = new JButton("Ajouter");
        addRoad.setFont(new Font("arial", Font.PLAIN, 22));
        addRoad.addActionListener(e -> {
            if (!Objects.equals(nodeList1.getSelectedItem(), nodeList2.getSelectedItem()) &&
                    !distanceField.getText().equals("")) {
                RoadType type1 = switch (roadTypeBox.getSelectedIndex()) {
                    case 0 -> RoadType.AUTOROUTE;
                    case 1 -> RoadType.NATIONALE;
                    case 2 -> RoadType.DEPARTMENTALE;
                    default -> null;
                };
                PathBuilder pathBuilder = new PathBuilder(tab, main.getLinkFactor());
                pathBuilder.addRoad(tab.getNodeByName(Objects.requireNonNull(nodeList1.getSelectedItem()).
                                toString()),
                        tab.getNodeByName(Objects.requireNonNull(nodeList2.getSelectedItem()).toString()),
                        type1,
                        Double.parseDouble(distanceField.getText())); // TODO : take double here
                main.applyNewList(pathBuilder.getTab());
            }
        });
        add(addRoad);

        updateUI();
    }
    public void infoAboutAction(int type, int TTL){
        this.removeAll();
        switch (type) {

            case 6 -> {
                intro = new JLabel("Supprimer une route");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                intro.setHorizontalAlignment(SwingConstants.CENTER);
                add(intro);
                JPanel nodeListPanel = new JPanel();
                JComboBox<String> nodeList3 = getNodesComboBox();
                nodeListPanel.add(nodeList3);
                JComboBox<String> nodeList4 = getNodesComboBox();
                nodeListPanel.add(nodeList4);
                add(nodeListPanel);
                JButton deleteRoad = new JButton("Supprimer");
                deleteRoad.setFont(new Font("arial", Font.PLAIN, 22));
                deleteRoad.addActionListener(e -> {
                    PathBuilder pathBuilder = new PathBuilder(tab, main.getLinkFactor());
                    if (pathBuilder.deleteRoad(tab.getNodeByName(Objects.requireNonNull(nodeList3.getSelectedItem()).
                                    toString()),
                            tab.getNodeByName(Objects.requireNonNull(nodeList4.getSelectedItem()).toString()))) {
                        main.applyNewList(pathBuilder.getTab());
                        JOptionPane.showMessageDialog(null, "Route supprimée");
                    } else {
                        JOptionPane.showMessageDialog(null, "Pas de route à supprimer " +
                                "entre ces deux lieux");
                    }
                });
                add(deleteRoad);
            }
            case 7 -> {
                intro = new JLabel("Visualiser un lieu");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                intro.setHorizontalAlignment(SwingConstants.CENTER);
                add(intro);
                JLabel nodeName = new JLabel("Nom du lieu : " + map.getClicked().getName());
                nodeName.setFont(new Font("arial", Font.PLAIN, 20));
                add(nodeName);
                JLabel nodeType = new JLabel("Type de lieu : " + map.getClicked().getStringType());
                nodeType.setFont(new Font("arial", Font.PLAIN, 20));
                add(nodeType);
                JLabel nodeLat = new JLabel("Latitude : " + map.getClicked().getLocX());
                nodeLat.setFont(new Font("arial", Font.PLAIN, 20));
                add(nodeLat);
                JLabel nodeLon = new JLabel("Longitude : " + map.getClicked().getLocY());
                nodeLon.setFont(new Font("arial", Font.PLAIN, 20));
                add(nodeLon);
            }
            case 8 -> {
                intro = new JLabel("Gestion des lieux");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                intro.setHorizontalAlignment(SwingConstants.CENTER);
                add(intro);
                JButton addNode = new JButton("Ajouter un lieu");
                addNode.setFont(new Font("arial", Font.PLAIN, 22));
                addNode.addActionListener(e -> {
                    infoAboutAddNode();
                });
                add(addNode);
                JButton deleteNode = new JButton("Supprimer un lieu");
                deleteNode.setFont(new Font("arial", Font.PLAIN, 22));
                deleteNode.addActionListener(e -> {
                    actionChoice.showIndication(3);
                });
                add(deleteNode);
                JButton modifyNode = new JButton("Modifier un lieu");
                modifyNode.setFont(new Font("arial", Font.PLAIN, 22));
                modifyNode.addActionListener(e -> {
                    infoAboutAction(9, 2);
                });
                add(modifyNode);
            }
            case 9 -> {
                intro = new JLabel("Modifier un lieu");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                intro.setHorizontalAlignment(SwingConstants.CENTER);
                add(intro);
                JPanel nodeListPanel = new JPanel();
                JComboBox<String> nodeList5 = getNodesComboBox();
                nodeListPanel.add(nodeList5);
                add(nodeListPanel);
                JButton modifyNode = new JButton("Modifier");
                modifyNode.setFont(new Font("arial", Font.PLAIN, 22));
                modifyNode.addActionListener(e -> {
                    tmp = tab.getNodeByName(Objects.requireNonNull(nodeList5.getSelectedItem()).toString());
                    infoAboutAction(10, 0); // TODO : Bug null pointer
                });
                add(modifyNode);

            }
            case 10 -> {
                intro = new JLabel("Propriétés du lieu");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                intro.setHorizontalAlignment(SwingConstants.CENTER);
                add(intro);
                JPanel namePanel = new JPanel();
                JLabel nameLabel = new JLabel("Nom : ");
                nameLabel.setFont(new Font("arial", Font.PLAIN, 20));
                namePanel.add(nameLabel);
                JTextField nameField = new JTextField();
                nameField.setFont(new Font("arial", Font.PLAIN, 20));
                nameField.setText(tmp.getName());
                namePanel.add(nameField);
                add(namePanel);
                JPanel typePanel = new JPanel();
                JLabel typeLabel = new JLabel("Type : ");
                typeLabel.setFont(new Font("arial", Font.PLAIN, 20));
                typePanel.add(typeLabel);
                JComboBox<String> typeBox = new JComboBox<>();
                typeBox.setFont(new Font("arial", Font.PLAIN, 20));
                typeBox.addItem("ville");
                typeBox.addItem("restaurant");
                typeBox.addItem("service");
                typeBox.addItem("loisir");
                typeBox.setSelectedItem(tmp.getStringType());
                typePanel.add(typeBox);
                add(typePanel);
                JPanel latPanel = new JPanel();
                JLabel latLabel = new JLabel("Latitude : ");
                latLabel.setFont(new Font("arial", Font.PLAIN, 20));
                latPanel.add(latLabel);
                JTextField latField = new JTextField();
                latField.setFont(new Font("arial", Font.PLAIN, 20));
                latField.setText(String.valueOf(tmp.getLocX()));
                latPanel.add(latField);
                add(latPanel);
                JPanel lonPanel = new JPanel();
                JLabel lonLabel = new JLabel("Longitude : ");
                lonLabel.setFont(new Font("arial", Font.PLAIN, 20));
                lonPanel.add(lonLabel);
                JTextField lonField = new JTextField();
                lonField.setFont(new Font("arial", Font.PLAIN, 20));
                lonField.setText(String.valueOf(tmp.getLocY()));
                lonPanel.add(lonField);
                add(lonPanel);
                JButton modifyNode = new JButton("Modifier");
                modifyNode.setFont(new Font("arial", Font.PLAIN, 22));
                AtomicReference<Sommet> created = new AtomicReference<>(new Sommet());
                modifyNode.addActionListener(e -> {
                    if (nameField.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Le nom ne peut pas être vide");
                    } else if (latField.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "La latitude ne peut pas être vide");
                    } else if (lonField.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "La longitude ne peut pas être vide");
                    } else {
                        if (Objects.equals(typeBox.getSelectedItem(), "ville")) {
                            created.set(new Sommet(nameField.getText(), NodeType.VILLE,
                                    Double.parseDouble(latField.getText()), Double.parseDouble(lonField.getText())));
                        } else if (typeBox.getSelectedItem().equals("restaurant")) {
                            created.set(new Sommet(nameField.getText(), NodeType.RESTAURANT,
                                    Double.parseDouble(latField.getText()), Double.parseDouble(lonField.getText())));
                        } else if (typeBox.getSelectedItem().equals("service")) {
                            created.set(new Sommet(nameField.getText(), NodeType.SERVICE,
                                    Double.parseDouble(latField.getText()), Double.parseDouble(lonField.getText())));
                        } else if (typeBox.getSelectedItem().equals("loisir")) {
                            created.set(new Sommet(nameField.getText(), NodeType.LOISIR,
                                    Double.parseDouble(latField.getText()), Double.parseDouble(lonField.getText())));
                        }
                        tab.removeListByOrigin(tmp);
                        tab.add(new NodeList(new Cell(created.get())));

                        main.applyNewList(tab);
                        JOptionPane.showMessageDialog(null, "Lieu modifié");
                    }
                });
                add(modifyNode);
            }
            case 11 -> {
                intro = new JLabel("Visualiser le graphe");
                intro.setFont(new Font("arial", Font.PLAIN, 22));
                intro.setHorizontalAlignment(SwingConstants.CENTER);
                add(intro);
                JButton printAll = new JButton("Visualiser tous les lieux");
                printAll.setFont(new Font("arial", Font.PLAIN, 22));
                printAll.addActionListener(e -> {
                    map.repaint();
                });
                add(printAll);
                JButton printCity = new JButton("Visualiser les villes");
                printCity.setFont(new Font("arial", Font.PLAIN, 22));
                printCity.addActionListener(e -> {
                    map.callSpecialDraw(NodeType.VILLE);
                });
                add(printCity);
                JButton printRestaurant = new JButton("Visualiser les restaurants");
                printRestaurant.setFont(new Font("arial", Font.PLAIN, 22));
                printRestaurant.addActionListener(e -> {
                    map.callSpecialDraw(NodeType.RESTAURANT);
                });
                add(printRestaurant);
                JButton printService = new JButton("Visualiser les services");
                printService.setFont(new Font("arial", Font.PLAIN, 22));
                printService.addActionListener(e -> {
                    map.callSpecialDraw(NodeType.SERVICE);
                });
                add(printService);
                JButton printLoisir = new JButton("Visualiser les loisirs");
                printLoisir.setFont(new Font("arial", Font.PLAIN, 22));
                printLoisir.addActionListener(e -> {
                    map.callSpecialDraw(NodeType.LOISIR);
                });
                add(printLoisir);
            }
        }
        updateUI();
    }
    public JSlider getSliderPanel(){
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
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String lat = latitudeField.getText();
            String lon = longitudeField.getText();
            String type = Objects.requireNonNull(typeBox.getSelectedItem()).toString();
            if (name.equals("") || lat.equals("") || lon.equals("")) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
            }
            else {
                Sommet newSm = null;
                try {
                    double latD = Double.parseDouble(lat);
                    double lonD = Double.parseDouble(lon);
                    switch (type) {
                        case "Restaurant" -> {
                            NodeType smType = NodeType.RESTAURANT;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        case "Culturel" -> {
                            NodeType smType = NodeType.LOISIR;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        case "Service" -> {
                            NodeType smType = NodeType.SERVICE;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        case "Ville" -> {
                            NodeType smType = NodeType.VILLE;
                            newSm = new Sommet(name, smType, latD, lonD);
                        }
                        default -> JOptionPane.showMessageDialog(null, "Veuillez choisir un type");
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer des coordonnées valides");
                }
                if (newSm != null) {
                    main.addSommet(newSm);
                    JOptionPane.showMessageDialog(null, "DataStructure.Sommet ajouté");
                }
            }
        });

        addButton.setFont(new Font("arial", Font.PLAIN, 22));
        return addButton;
    }
    public JButton getPathButton(Sommet s1, Sommet s2, JComboBox<String> comboBox, JComboBox<String> comboBox2){
        JButton button2 = new JButton("Trouver un chemin");
        button2.addActionListener(e -> {
            if (comboBox.getSelectedItem() != null) {
                map.setTmp1(s1);
                map.setTmp3(s2);
                map.setTmp2(tab.getNodeByName(comboBox.getSelectedItem().toString()));
                System.out.println("." + comboBox.getSelectedItem().toString() + ".");
                map.setDrawSpecialPath(true);
                map.repaint();
            } else if (comboBox2.getSelectedItem() != null) {
                map.setTmp1(s1);
                map.setTmp3(s2);
                NodeType smType = null;
                if (comboBox2.getSelectedItem().toString().equals("Ville")) {
                    smType = NodeType.VILLE;
                } else if (comboBox2.getSelectedItem().toString().equals("Restaurant")) {
                    smType = NodeType.RESTAURANT;
                } else if (comboBox2.getSelectedItem().toString().equals("Service")) {
                    smType = NodeType.SERVICE;
                } else if (comboBox2.getSelectedItem().toString().equals("Loisir")) {
                    smType = NodeType.LOISIR;
                }
                map.setTmp2(pf.getCloserNode(s1, s2, smType));
                map.setDrawSpecialPath(true);
                map.repaint();
            }
        });
        button2.setFont(new Font("arial", Font.PLAIN, 22));
        return button2;
    }

    public JComboBox<String> getNodesComboBox(){
        JComboBox<String> comboBox = new JComboBox<>();
        for (Sommet node : tab.getAllNodes()) {
            comboBox.addItem(node.getName());
        }
        comboBox.setFont(new Font("arial", Font.PLAIN, 20));
        return comboBox;
    }
}
