import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewFIRsScreen {
    public static void showViewFIRsScreen(JFrame frame) {
        // Custom JPanel with background image
        JPanel panel = new JPanel() {
            private Image backgroundImage = new ImageIcon("ViewFirs.jpg").getImage(); 

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); 
                }
            }
        };

        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Stored FIRs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);  

        // JTable to display FIRs
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("FIR ID");
        model.addColumn("Crime ID");
        model.addColumn("Complainant Name");
        model.addColumn("Contact");
        model.addColumn("Filing Date");

        // Load FIR data from the database
        try {
            String query = "SELECT * FROM FIRs";
            try (Statement stmt = CrimeRecordsManagementSystem.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("crime_id"),
                        rs.getString("complainant_name"),
                        rs.getString("complainant_contact"),
                        rs.getDate("filing_date")
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading FIRs.");
        }

        // ScrollPane to allow scrolling of large tables
        JScrollPane scrollPane = new JScrollPane(table);

        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10)); 
        contentPanel.add(scrollPane);

        
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(200, 40));  
        backButton.addActionListener(e -> MainMenu.showMainMenu(frame));

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());  
        buttonPanel.add(backButton); 
        buttonPanel.add(Box.createVerticalGlue());  

        
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the content pane of the frame to the panel
        frame.setContentPane(panel);
        frame.revalidate();
    }
}
