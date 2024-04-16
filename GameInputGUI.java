package machine_learning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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



        // Read data from CSV file
        data = readData();

        // Calculate statistics for the Naive Bayes classifier
        Stats classifier = new Stats();
        classifier.calculateStatistics(data);
        Map<String, Double> statistics = classifier.calculateStatistics(data);

        // Extract statistics from the map
        double totalReviews = statistics.get("totalReviews");
        double recCount = statistics.get("recCount");
        double notRecCount = statistics.get("notRecCount");
        double percentRec = statistics.get("percentRec");
        double percentNotRec = statistics.get("percentNotRec");
        double avgHoursRec = statistics.get("avgHoursRec");
        double avgHoursNotRec = statistics.get("avgHoursNotRec");
        double avgPricePerHourRec = statistics.get("avgPricePerHourRec");
        double avgPricePerHourNotRec = statistics.get("avgPricePerHourNotRec");
        double percentWinRec = statistics.get("percentWinRec");
        double percentMacRec = statistics.get("percentMacRec");
        double percentLinuxRec = statistics.get("percentLinuxRec");
        double percentWinNotRec = statistics.get("percentWinNotRec");
        double percentMacNotRec = statistics.get("percentMacNotRec");
        double percentLinuxNotRec = statistics.get("percentLinuxNotRec");
    }

    static List<Instance> readData() {
        List<Instance> instances = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Training.csv"))) {
            String line;
            // Skip header line
            br.readLine();
            // Read each line of the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line by commas to get the values
                String[] values = line.split(",");
                // Extract relevant features
                double hoursPlayed = Double.parseDouble(values[4]);
                double price = Double.parseDouble(values[5]);
                double hourToPrice = (hoursPlayed == 0) ? 0 : hoursPlayed / price;
                boolean windows = Integer.parseInt(values[7]) == 1;
                boolean mac = Integer.parseInt(values[8]) == 1;
                boolean linux = Integer.parseInt(values[9]) == 1;
                boolean recommended = Integer.parseInt(values[10]) == 1;

                // Create a machine_learning.Instance object
                Instance instance = new Instance(hoursPlayed, hourToPrice, windows, mac, linux, recommended);
                // Add the instance to the list
                instances.add(instance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return instances;
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

    public static class StatisticsDisplay extends JFrame {

        public StatisticsDisplay(Map<String, Double> statistics) {
            setTitle("Statistics Display");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(0, 1));

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            textArea.append("Total Reviews: " + "\t" + statistics.get("totalReviews") + "\n");

            textArea.append("Recommendation Count: " + statistics.get("recCount") + "\n");
            textArea.append("Not Recommended Count: " + "\t" + statistics.get("notRecCount") + "\n");

            textArea.append("Percent Recommended: " + "\t" + String.format("%.3f", statistics.get("percentRec")) + "%" + "\n");
            textArea.append("Percent Not Recommended: " + "\t" + String.format("%.3f", statistics.get("percentNotRec")) + "%" + "\n");

            textArea.append("\n"+"Recommended Reviews Statistics: " +"\n");
            textArea.append("Average Hours Recommended: " + "\t" + String.format("%.3f", statistics.get("avgHoursRec")) + "\n");
            textArea.append("Average Price per Hour Recommended: " + "\t" + String.format("%.3f", statistics.get("avgPricePerHourRec")) + "\n");
            textArea.append("Percent Recommended on Windows: " + "\t" + String.format("%.3f", statistics.get("percentWinRec")) + "%" + "\n");
            textArea.append("Percent Recommended on Mac: " + "\t" + String.format("%.3f", statistics.get("percentMacRec")) + "%" + "\n");
            textArea.append("Percent Recommended on Linux: " + "\t" + String.format("%.3f", statistics.get("percentLinuxRec")) + "%" + "\n");



            textArea.append("\n"+"Not Recommended Reviews Statistics: " +"\n");
            textArea.append("Average Hours Not Recommended: " + "\t" + String.format("%.3f", statistics.get("avgHoursNotRec")) + "\n");
            textArea.append("Average Price per Hour Not Recommended: " + "\t" + String.format("%.3f", statistics.get("avgPricePerHourNotRec")) + "\n");
            textArea.append("Percent Not Recommended on Windows: " + "\t" + String.format("%.3f", statistics.get("percentWinNotRec")) + "%" + "\n");
            textArea.append("Percent Not Recommended on Mac: " + "\t" + String.format("%.3f", statistics.get("percentMacNotRec")) + "%" + "\n");
            textArea.append("Percent Not Recommended on Linux: " + "\t" + String.format("%.3f", statistics.get("percentLinuxNotRec")) + "%" + "\n");



            add(scrollPane);

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}