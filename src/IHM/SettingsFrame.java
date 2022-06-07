package IHM;

import Launch.*;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {

    private final JPanel contentPane = new JPanel();
    private final Main main;

    SettingsFrame(Main main) {

        this.main = main;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setTitle("Réglages");
        buildFrame();

        pack();
        setVisible(true);
    }

    private void buildFrame() {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        JLabel intro = new JLabel("Menu des paramètres");
        intro.setFont(new Font("Arial", Font.BOLD, 24));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(intro);
        JPanel dimensionsPanel = new JPanel();
        JLabel dimensionsLabel = new JLabel("Dimensions :");
        dimensionsLabel.setFont(new Font("Arial", Font.BOLD, 22));
        dimensionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel widthLabel = new JLabel("Largeur :");
        JLabel heightLabel = new JLabel("Hauteur :");
        widthLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        heightLabel.setFont(new Font("Arial", Font.PLAIN, 22));

        JTextField widthField = new JTextField();
        widthField.setText(String.valueOf(main.getSettings().getWidth()));
        widthField.setFont(new Font("Arial", Font.PLAIN, 22));
        widthField.setPreferredSize(new Dimension(80, 30));
        JTextField heightField = new JTextField();
        heightField.setText(String.valueOf(main.getSettings().getHeight()));
        heightField.setFont(new Font("Arial", Font.PLAIN, 22));
        heightField.setPreferredSize(new Dimension(80, 30));
        contentPane.add(dimensionsLabel);
        dimensionsPanel.add(widthLabel);
        dimensionsPanel.add(widthField);
        dimensionsPanel.add(heightLabel);
        dimensionsPanel.add(heightField);
        contentPane.add(dimensionsPanel);

        JPanel fullScreenPanel = new JPanel();
        JLabel fullScreenLabel = new JLabel("Plein écran :");
        JCheckBox fullScreenCheckBox = new JCheckBox();

        fullScreenCheckBox.setPreferredSize(new Dimension(50, 50));
        fullScreenCheckBox.setSelected(main.getSettings().isFullscreen());
        fullScreenPanel.add(fullScreenLabel);
        fullScreenPanel.add(fullScreenCheckBox);
        fullScreenLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        contentPane.add(fullScreenPanel);

        JPanel linkFactorPanel = new JPanel();
        JLabel linkFactorLabel = new JLabel("Facteur de liaison :");
        JTextField linkFactorField = new JTextField();
        linkFactorPanel.add(linkFactorLabel);
        linkFactorPanel.add(linkFactorField);
        linkFactorLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        linkFactorField.setFont(new Font("Arial", Font.PLAIN, 22));
        linkFactorField.setPreferredSize(new Dimension(60, 30));
        linkFactorField.setText(main.getSettings().getLinkFactor() + "");
        contentPane.add(linkFactorPanel);

        JButton applyButton = new JButton("Appliquer");
        applyButton.setFont(new Font("Arial", Font.PLAIN, 22));
        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(applyButton);

        applyButton.addActionListener(e -> {
            if(assertInput(widthField.getText(), heightField.getText(), linkFactorField.getText())) {

                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                double linkFactor = Double.parseDouble(linkFactorField.getText());
                main.changeSettings(width, height, fullScreenCheckBox.isSelected(),
                        linkFactor);
            }
            else
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides", "Erreur", JOptionPane.ERROR_MESSAGE);
        });

        setContentPane(contentPane);
    }

    private boolean assertInput(String width, String height, String linkFactor) {
        try {
            int widthInt = Integer.parseInt(width);
            int heightInt = Integer.parseInt(height);
            double linkFactorDouble = Double.parseDouble(linkFactor);
            if (widthInt < 0 || heightInt < 0 || linkFactorDouble < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
