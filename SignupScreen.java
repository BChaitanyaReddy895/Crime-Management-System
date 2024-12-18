import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignupScreen {

    public static void showSignupScreen(JFrame frame) {
        // Main Panel to center content
        JPanel mainPanel = new BackgroundPanel(); 
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setLayout(layout);

        // Inner panel for form fields
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); 
        signupPanel.setBackground(new Color(245, 250, 255)); 

        // Labels and Fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 40));
        nameField.setMaximumSize(new Dimension(300, 40));
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mobileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField mobileField = new JTextField();
        mobileField.setPreferredSize(new Dimension(300, 40));
        mobileField.setMaximumSize(new Dimension(300, 40));
        mobileField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(300, 40));
        confirmPasswordField.setMaximumSize(new Dimension(300, 40));
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 18));

        // Signup button
        JButton signupButton = new JButton("Signup");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setPreferredSize(new Dimension(300, 50));
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        signupButton.setBackground(new Color(255, 165, 0)); // Orange color
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);

        // Button Action
        signupButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String mobile = mobileField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            try {
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match. Signup failed.");
                } else {
                    signup(name, username, mobile, password);
                    JOptionPane.showMessageDialog(frame, "Signup successful! You can now login.");
                    LoginScreen.showLoginScreen(frame);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(nameLabel);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(nameField);

        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(usernameLabel);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(usernameField);

        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(mobileLabel);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(mobileField);

        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(passwordLabel);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(passwordField);

        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(confirmPasswordLabel);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(confirmPasswordField);

        signupPanel.add(Box.createVerticalStrut(20));
        signupPanel.add(signupButton);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(signupPanel, gbc);

        // Set main panel as content pane
        frame.setContentPane(mainPanel);
        frame.revalidate();
    }

    private static void signup(String name, String username, String mobile, String password) throws SQLException {
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'Police')";
        try (PreparedStatement pstmt = CrimeRecordsManagementSystem.conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        }
    }

    // Custom JPanel to display background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                
                backgroundImage = new ImageIcon("signup.jpg").getImage(); 
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
