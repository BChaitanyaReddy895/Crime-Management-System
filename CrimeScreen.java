import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CrimeScreen {

    public static void showAddCrimeScreen(JFrame frame) {
        // Create a custom panel with background image
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new GridBagLayout()); 

        // Create labels and fields
        JLabel crimeTypeLabel = new JLabel("Crime Type:");
        JTextField crimeTypeField = new JTextField(20);
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField(20);
        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(20);
        JLabel officerLabel = new JLabel("Officer In Charge:");
        JTextField officerField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back to Main Menu"); // Back to Menu button

        
        Font labelFont = new Font("Arial", Font.BOLD, 18); // Increase label font size
        Font fieldFont = new Font("Arial", Font.PLAIN, 18); // Increase field font size
        Font buttonFont = new Font("Arial", Font.BOLD, 22); // Increase button text size

        crimeTypeLabel.setFont(labelFont);
        descriptionLabel.setFont(labelFont);
        locationLabel.setFont(labelFont);
        dateLabel.setFont(labelFont);
        officerLabel.setFont(labelFont);

        crimeTypeField.setFont(fieldFont);
        descriptionField.setFont(fieldFont);
        locationField.setFont(fieldFont);
        dateField.setFont(fieldFont);
        officerField.setFont(fieldFont);

        
        Dimension fieldSize = new Dimension(300, 50); 
        crimeTypeField.setPreferredSize(fieldSize);
        descriptionField.setPreferredSize(fieldSize);
        locationField.setPreferredSize(fieldSize);
        dateField.setPreferredSize(fieldSize);
        officerField.setPreferredSize(fieldSize);

        submitButton.setFont(buttonFont);
        submitButton.setPreferredSize(new Dimension(200, 60)); 

        // Set Back Button properties
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(new Dimension(200, 60)); 

        
        submitButton.addActionListener(e -> {
            String crimeType = crimeTypeField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            String date = dateField.getText();
            String officer = officerField.getText();
            try {
                addCrime(crimeType, description, location, date, officer);
                JOptionPane.showMessageDialog(frame, "Crime record added successfully.");
                MainMenu.showMainMenu(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        
        backButton.addActionListener(e -> MainMenu.showMainMenu(frame)); // Navigate back to the main menu

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; // Center horizontally
        gbc.insets = new Insets(10, 20, 10, 20); // Padding between components

        // Add components to main panel
        gbc.gridy = 0;
        mainPanel.add(crimeTypeLabel, gbc);
        gbc.gridy++;
        mainPanel.add(crimeTypeField, gbc);

        gbc.gridy++;
        mainPanel.add(descriptionLabel, gbc);
        gbc.gridy++;
        mainPanel.add(descriptionField, gbc);

        gbc.gridy++;
        mainPanel.add(locationLabel, gbc);
        gbc.gridy++;
        mainPanel.add(locationField, gbc);

        gbc.gridy++;
        mainPanel.add(dateLabel, gbc);
        gbc.gridy++;
        mainPanel.add(dateField, gbc);

        gbc.gridy++;
        mainPanel.add(officerLabel, gbc);
        gbc.gridy++;
        mainPanel.add(officerField, gbc);

        gbc.gridy++;
        mainPanel.add(submitButton, gbc);

        
        gbc.gridy++;
        mainPanel.add(backButton, gbc);

        // Set content pane and revalidate
        frame.setContentPane(mainPanel);
        frame.revalidate();
    }

    private static void addCrime(String crimeType, String description, String location, String date, String officer) throws SQLException {
        String query = "INSERT INTO Crimes (crime_type, description, location, date, officer_in_charge) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = CrimeRecordsManagementSystem.conn.prepareStatement(query)) {
            pstmt.setString(1, crimeType);
            pstmt.setString(2, description);
            pstmt.setString(3, location);
            pstmt.setDate(4, Date.valueOf(date));
            pstmt.setString(5, officer);
            pstmt.executeUpdate();
        }
    }

    // Custom JPanel for background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                
                backgroundImage = new ImageIcon("crimetype.jpg").getImage(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Scale image to fit the panel
            }
        }
    }
}
