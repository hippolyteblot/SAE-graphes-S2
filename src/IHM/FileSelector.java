package IHM;



import Launch.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileSelector extends JFrame {

    JFileChooser chooser = new JFileChooser();
    String fileName = "";

    public FileSelector(Main main){
        setTitle("Selectionner votre fichier");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Selectionner votre fichier");
        chooser.setApproveButtonText("Selectionner");
        chooser.setApproveButtonToolTipText("Selectionner le fichier");
        chooser.setCurrentDirectory(new java.io.File(".\\Data"));

        chooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    fileName = chooser.getSelectedFile().getAbsolutePath();
                    writeFileToDataFile();
                    try {
                        main.reinit();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        add(chooser);
    }

    public void writeFileToDataFile() {
        File file = new File(fileName);
        try {
            FileReader fr = new FileReader(file);
            String data = "";
            int c;
            while ((c = fr.read()) != -1) {
                data += (char) c;
            }
            fr.close();

            file = new File(fileName);
            FileWriter fw = new FileWriter("src\\data.csv");
            fw.write(data);
            fw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
