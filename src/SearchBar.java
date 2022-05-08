import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class SearchBar extends JPanel {

    private final JComboBox<String> typesBox;
    private final JTextField radiusField;
    private final JTextField searchBar;
    private final JSlider numberOfResults;
    private final String[] types;
    private final static TreeMap<String, String[]> typesMap = new TreeMap<>();

    public SearchBar(DataGetter dataGetter, Main main) throws FileNotFoundException {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Error setting the look and feel");
        }

        JButton valider = new JButton("Valider");
        valider.setFont(new Font("Arial", Font.PLAIN, 26));
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataGetter.setLocationSelected(searchBar.getText().replace(" ", "+"));
                    System.out.println("here " + typesBox.getSelectedItem().toString() + " -> " + typesMap.get(typesBox.getSelectedItem().toString())[0]);
                    dataGetter.setTypeSelected(typesMap.get(typesBox.getSelectedItem().toString())[0]);
                    dataGetter.setRadiusSelected(radiusField.getText());
                    dataGetter.setNumberOfResultsSelected(numberOfResults.getValue());
                    if(!dataGetter.getLocationSelected().equals("") && !dataGetter.getRadiusSelected().equals("")) {
                        dataGetter.locationSelected();

                    }
                    System.out.println(main);
                    main.reinit();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        searchBar = new JTextField();
        searchBar.setFont(new Font("Arial", Font.PLAIN, 26));
        searchBar.setPreferredSize(new Dimension(300, 40));
        JLabel label = new JLabel("Recherchez un lieu : ");
        JLabel nameLabel = new JLabel("Nom : ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        JLabel typeLabel = new JLabel("Type : ");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        label.setFont(new Font("Arial", Font.PLAIN, 26));
        types = getDataFromFile("src/types.txt");
        typesBox = new JComboBox<>(types);
        typesBox.setFont(new Font("Arial", Font.PLAIN, 26));
        typesBox.setPreferredSize(new Dimension(300, 40));
        radiusField = new JTextField();
        radiusField.setFont(new Font("Arial", Font.PLAIN, 26));
        radiusField.setPreferredSize(new Dimension(100, 40));
        JLabel radiusLabel = new JLabel("Rayon : ");
        radiusLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        JLabel kmLabel = new JLabel("metres");
        kmLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        JLabel resultsLabel = new JLabel("Nombre : ");
        resultsLabel.setFont(new Font("Arial", Font.PLAIN, 26));


        numberOfResults = new JSlider(JSlider.HORIZONTAL ,0, 20, 0);
        numberOfResults.setFont(new Font("Arial", Font.PLAIN, 26));
        numberOfResults.setPreferredSize(new Dimension(300, 50));
        numberOfResults.setMajorTickSpacing(5);
        numberOfResults.setMinorTickSpacing(1);
        numberOfResults.setPaintTicks(true);
        numberOfResults.setPaintLabels(true);
        numberOfResults.setSnapToTicks(true);


        JPanel panel = new JPanel();
        panel.add(label);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createVerticalGlue());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(panel);
        JPanel panelName = new JPanel();
        panelName.setLayout(new FlowLayout());
        panelName.add(nameLabel);
        panelName.add(searchBar);
        panelName.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panelName);

        JPanel panelType = new JPanel();
        panelType.setLayout(new FlowLayout());
        panelType.add(typeLabel);
        panelType.add(typesBox);
        panelType.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panelType);

        JPanel panelRadius = new JPanel();
        panelRadius.setLayout(new FlowLayout());
        panelRadius.add(radiusLabel);
        panelRadius.add(radiusField);
        panelRadius.add(kmLabel);
        panelRadius.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panelRadius);

        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new FlowLayout());
        panelSlider.add(resultsLabel);
        panelSlider.add(numberOfResults);
        panelSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panelSlider);


        JPanel panelValider = new JPanel();
        panelValider.setLayout(new FlowLayout());
        panelValider.add(valider);
        panelValider.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panelValider);
        add(Box.createVerticalGlue());



    }

    public String[] getDataFromFile(String fileName) throws FileNotFoundException {
        ArrayList<String> data = new ArrayList<>();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        String line;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] tabLine = line.split(",");
            String[] typeTab = {tabLine[0], tabLine[2]};

            typesMap.put(tabLine[1], typeTab);
            System.out.println(tabLine[1] + " : " + typeTab[0] + " - " + typeTab[1]);
        }
        data.addAll(typesMap.keySet());
        scanner.close();
        return data.toArray(new String[0]);
    }

    public String getTypeFound(String name) {
        System.out.println(name);
        return typesMap.get(typesBox.getSelectedItem().toString())[1];
    }

    public String compare(String type1, String type2) {
        return type1.compareTo(type2) < 0 ? type1 : type2;
    }
}