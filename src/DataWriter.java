import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class DataWriter {

    private String fileName;
    private FileWriter fileWriter;
    private StringBuilder stringBuilder;

    public DataWriter(NodeList nodeList){
        stringBuilder = new StringBuilder();
        initWindow(nodeList);
    }

    public DataWriter(String fileName, String filePath) {
        this.fileName = fileName;
        stringBuilder = new StringBuilder();
    }

    public void initWindow(NodeList nodeList) {
        JFrame frame = new JFrame("Sauvegarde");
        frame.setSize(500, 100);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.setLayout(new FlowLayout());
        JLabel label = new JLabel("Nom du fichier : ");
        JTextField textField = new JTextField(10);
        JLabel extension = new JLabel(".csv");
        JButton button = new JButton("Sauvegarder");
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileName = textField.getText()+".csv";
                frame.dispose();
                writeAll(nodeList);
            }
        });


        label.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        extension.setFont(new Font("Arial", Font.PLAIN, 20));

        panel.add(label);
        panel.add(textField);
        panel.add(extension);

        frame.add(panel);
        frame.add(button);
        frame.pack();

    }

    public void writeAll(NodeList nodeList) {
        try {
            fileWriter = new FileWriter("Data\\" + fileName);
            for(Sommet sommet : nodeList.getAllNodes()) {
                stringBuilder.append(sommet.getCharType() + "," + sommet.getName() + "," + sommet.getLocX() + "," + sommet.getLocY() + ":;");
                writeNeighbours(nodeList.get(nodeList.getNodeIndex(sommet)));
                stringBuilder.append(";;");
                stringBuilder.append("\n");
            }
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sauvegarde terminée");
    }

    public void writeNeighbours(NeighborsList neighborsList) {
        for(Cell cell : neighborsList.getNeighbors()) {
            stringBuilder.append(cell.getRoute().getCharType() + "," + cell.getRoute().getKm() + "::" +
                    cell.getValue().getCharType() + "," + cell.getValue().getName() + ";");
        }
    }
}
