import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class AddFIRScreen {

    public static void showAddFIRScreen(JFrame frame) {
        // Main Panel to center content
        JPanel mainPanel = new BackgroundPanel(); 
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setLayout(layout);

        // Inner panel for form fields
        JPanel addFIRPanel = new JPanel();
        addFIRPanel.setLayout(new BoxLayout(addFIRPanel, BoxLayout.Y_AXIS));
        addFIRPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        addFIRPanel.setBackground(new Color(245, 250, 255));

        
        JLabel titleLabel = new JLabel("ADD FIR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));  
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        titleLabel.setForeground(Color.BLACK);  

        // Labels and Fields
        JLabel crimeIdLabel = new JLabel("Crime ID:");
        crimeIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        crimeIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField crimeIdField = new JTextField();
        crimeIdField.setPreferredSize(new Dimension(300, 40));
        crimeIdField.setMaximumSize(new Dimension(300, 40));
        crimeIdField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel complainantNameLabel = new JLabel("Complainant Name:");
        complainantNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        complainantNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField complainantNameField = new JTextField();
        complainantNameField.setPreferredSize(new Dimension(300, 40));
        complainantNameField.setMaximumSize(new Dimension(300, 40));
        complainantNameField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel complainantContactLabel = new JLabel("Complainant Contact:");
        complainantContactLabel.setFont(new Font("Arial", Font.BOLD, 18));
        complainantContactLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField complainantContactField = new JTextField();
        complainantContactField.setPreferredSize(new Dimension(300, 40));
        complainantContactField.setMaximumSize(new Dimension(300, 40));
        complainantContactField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel filingDateLabel = new JLabel("Filing Date (YYYY-MM-DD):");
        filingDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        filingDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField filingDateField = new JTextField(LocalDate.now().toString()); 
        filingDateField.setPreferredSize(new Dimension(300, 40));
        filingDateField.setMaximumSize(new Dimension(300, 40));
        filingDateField.setFont(new Font("Arial", Font.PLAIN, 18));

        
        JButton submitButton = new JButton("Submit FIR");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setPreferredSize(new Dimension(300, 50));
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(new Color(255, 165, 0)); 
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        // Back to Menu button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(300, 50));
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(0, 123, 255)); 
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        // Button Actions
        submitButton.addActionListener(e -> {
            try {
                // Insert the FIR data into the database
                String query = "INSERT INTO FIRs (crime_id, complainant_name, complainant_contact, filing_date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = CrimeRecordsManagementSystem.conn.prepareStatement(query)) {
                    pstmt.setInt(1, Integer.parseInt(crimeIdField.getText()));
                    pstmt.setString(2, complainantNameField.getText());
                    pstmt.setString(3, complainantContactField.getText());
                    pstmt.setDate(4, Date.valueOf(filingDateField.getText()));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "FIR Added Successfully!");
                    MainMenu.showMainMenu(frame);
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error adding FIR. Please check your inputs.");
            }
        });

        // Back button action
        backButton.addActionListener(e -> MainMenu.showMainMenu(frame)); 

        // Adding components with spacing
        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(titleLabel);  
        addFIRPanel.add(Box.createVerticalStrut(20)); 
        addFIRPanel.add(crimeIdLabel);
        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(crimeIdField);

        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(complainantNameLabel);
        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(complainantNameField);

        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(complainantContactLabel);
        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(complainantContactField);

        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(filingDateLabel);
        addFIRPanel.add(Box.createVerticalStrut(10));
        addFIRPanel.add(filingDateField);

        addFIRPanel.add(Box.createVerticalStrut(20));
        addFIRPanel.add(submitButton);

        
        addFIRPanel.add(Box.createVerticalStrut(10)); 
        addFIRPanel.add(backButton);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(addFIRPanel, gbc);

        // Set main panel as content pane
        frame.setContentPane(mainPanel);
        frame.revalidate();
    }

    // Custom JPanel to display background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                
                backgroundImage = new ImageIcon("AddFirs.jpg").getImage(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
