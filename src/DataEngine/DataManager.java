package DataEngine;

import IHM.FileSelector;
import IHM.FrameManager;
import Launch.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataManager extends JFrame {


    JButton importData = new JButton("Importer des données");
    JButton deleteData = new JButton("Supprimer les données");
    JButton specificData = new JButton("Données spécifiques");
    JButton saveData = new JButton("Sauvegarder les données");
    FrameManager frameManager;
    Main main;

    public DataManager(FrameManager frameManager, Main main) throws FileNotFoundException {

        this.main = main;
        this.frameManager = frameManager;
        setTitle("Data Manager");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        importData.setFont(new Font("Arial", Font.PLAIN, 26));
        deleteData.setFont(new Font("Arial", Font.PLAIN, 26));
        specificData.setFont(new Font("Arial", Font.PLAIN, 26));
        saveData.setFont(new Font("Arial", Font.PLAIN, 26));

        add(importData);
        add(deleteData);
        add(specificData);
        add(saveData);
        addBtnEvent();
        setVisible(true);

    }

    public void addBtnEvent() {
        importData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                DataGetter dg = null;
                try {
                    dg = new DataGetter(main);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                JFrame frame = new JFrame("Importer des données");
                frame.setSize(600, 400);
                frame.add(dg.getSearchBar());
                frame.setVisible(true);
            }
        });
        deleteData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                DataGetter dg = null;
                try {
                    dg = new DataGetter(main);
                    dg.eraseData();
                    main.reinit();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        specificData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileSelector(main);
            }
        });

        saveData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataWriter(main.getTab());
            }
        });
    }
}
