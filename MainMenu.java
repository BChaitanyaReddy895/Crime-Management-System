import javax.swing.*;
import java.awt.*;

public class MainMenu {

    public static void showMainMenu(JFrame frame) {
        // Create a custom panel with background image
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new GridBagLayout()); 

        // Create buttons
        JButton addCrimeButton = new JButton("Add Crime");
        JButton viewCrimesButton = new JButton("View Crimes");
        JButton addFIRButton = new JButton("Add FIR");
        JButton viewFIRsButton = new JButton("View FIRs");
        JButton logoutButton = new JButton("Logout");

        
        Font buttonFont = new Font("Arial", Font.BOLD, 22); // Increased text size

        addCrimeButton.setFont(buttonFont);
        viewCrimesButton.setFont(buttonFont);
        addFIRButton.setFont(buttonFont);
        viewFIRsButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

       
        addCrimeButton.setPreferredSize(new Dimension(350, 60)); // Increased size
        viewCrimesButton.setPreferredSize(new Dimension(350, 60)); // Increased size
        addFIRButton.setPreferredSize(new Dimension(350, 60)); // Increased size
        viewFIRsButton.setPreferredSize(new Dimension(350, 60)); // Increased size
        logoutButton.setPreferredSize(new Dimension(350, 60)); // Increased size

        // Add action listeners to buttons
        addCrimeButton.addActionListener(e -> CrimeScreen.showAddCrimeScreen(frame));
        viewCrimesButton.addActionListener(e -> ViewCrimesScreen.showViewCrimesScreen(frame));
        addFIRButton.addActionListener(e -> AddFIRScreen.showAddFIRScreen(frame));
        viewFIRsButton.addActionListener(e -> ViewFIRsScreen.showViewFIRsScreen(frame));

        logoutButton.addActionListener(e -> LoginScreen.showLoginScreen(frame));

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; 
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 10, 15, 10); 

        // Add buttons to the panel
        mainPanel.add(addCrimeButton, gbc);
        gbc.gridy++;
        mainPanel.add(viewCrimesButton, gbc);
        gbc.gridy++;
        mainPanel.add(addFIRButton, gbc);
        gbc.gridy++;
        mainPanel.add(viewFIRsButton, gbc);
        gbc.gridy++;
        mainPanel.add(logoutButton, gbc);

        // Set the content pane and revalidate
        frame.setContentPane(mainPanel);
        frame.revalidate();
    }

    // Custom JPanel for background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                
                backgroundImage = new ImageIcon("mainmenu.jpg").getImage(); 
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
