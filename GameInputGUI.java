package machine_learning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class GameInputGUI extends JFrame implements ActionListener {
    private final JTextField hoursPlayedField;
    private final JTextField price;
    private double hourstopriceInput;
    private final JCheckBox windowsCheckBox;
    private final JCheckBox macCheckBox;
    private final JCheckBox linuxCheckBox;

    private final Stats stats;

    public GameInputGUI() {
        setTitle("Game Input");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Initialize Stats object
        stats = new Stats();

        // Read data from CSV file and calculate statistics
        List<Instance> data = Main.readData();
        stats.calculateStatistics(data);

        JLabel hoursPlayedLabel = new JLabel("Hours Played:");
        hoursPlayedField = new JTextField();

        JLabel hourToPriceRatioLabel = new JLabel("Price:");
        price = new JTextField();

        JLabel osLabel = new JLabel("Operating Systems:");
        windowsCheckBox = new JCheckBox("Windows");
        macCheckBox = new JCheckBox("Mac");
        linuxCheckBox = new JCheckBox("Linux");

        JButton predictButton = new JButton("Predict");
        predictButton.addActionListener(this);

        add(hoursPlayedLabel);
        add(hoursPlayedField);
        add(hourToPriceRatioLabel);
        add(price);
        add(osLabel);
        add(windowsCheckBox);
        add(new JLabel());
        add(macCheckBox);
        add(new JLabel());
        add(linuxCheckBox);
        add(new JLabel());
        add(predictButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Get input values
            double hoursPlayedInput = Math.log(Double.parseDouble(hoursPlayedField.getText()));
            double priceInput = Double.parseDouble(price.getText());
            boolean isWindowsInput = windowsCheckBox.isSelected();
            boolean isMacInput = macCheckBox.isSelected();
            boolean isLinuxInput = linuxCheckBox.isSelected();

            hourstopriceInput = Math.log(hoursPlayedInput/priceInput);

            // Create an Instance object for prediction
            Instance instance = new Instance(Math.log(hoursPlayedInput), hourstopriceInput, isWindowsInput, isMacInput, isLinuxInput, false);

            // Predict
            boolean prediction = stats.predict(instance);

            // Show prediction
            JOptionPane.showMessageDialog(this, "Prediction: " + prediction);
        } catch (NumberFormatException ex) {
            // Handle invalid input
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for hours played and hour to price ratio.");
        }
    }

    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            GameInputGUI gameInputGUI = new GameInputGUI();
            gameInputGUI.setVisible(true);
        });
    }
}