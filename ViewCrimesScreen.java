import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class ViewCrimesScreen {
    public static void showViewCrimesScreen(JFrame frame) {
        JPanel panel = new JPanel() {
            private Image backgroundImage = new ImageIcon("ViewCrimes.jpg").getImage(); 

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); 
                }
            }
        };

        panel.setLayout(new BorderLayout());

        
        JLabel titleLabel = new JLabel("Stored Crimes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);  

        // Create table and model
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Crime ID");
        model.addColumn("Crime Type");
        model.addColumn("Description");
        model.addColumn("Location");
        model.addColumn("Date");
        model.addColumn("Status");
        model.addColumn("Officer In Charge");

        // Fetch data from database
        try {
            String query = "SELECT * FROM Crimes";  
            try (Statement stmt = CrimeRecordsManagementSystem.conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("crime_type"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getDate("date"),
                        rs.getString("status"),
                        rs.getString("officer_in_charge")
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading crimes.");
        }

        
        JScrollPane scrollPane = new JScrollPane(table);

        
        TableColumn statusColumn = table.getColumnModel().getColumn(5);
        statusColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Open", "Closed"})));

        // Add action listener to handle status change
        table.getModel().addTableModelListener(e -> {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();

            if (column == 5) { 
                String newStatus = (String) table.getValueAt(row, column);
                int crimeId = (int) table.getValueAt(row, 0); 

                
                try {
                    String updateQuery = "UPDATE Crimes SET status = ? WHERE id = ?";
                    try (PreparedStatement pstmt = CrimeRecordsManagementSystem.conn.prepareStatement(updateQuery)) {
                        pstmt.setString(1, newStatus);
                        pstmt.setInt(2, crimeId);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating status.");
                }
            }
        });

       
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(titleLabel);
        tablePanel.add(Box.createVerticalStrut(10));  
        tablePanel.add(scrollPane);

       
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(200, 40)); 
        backButton.addActionListener(e -> MainMenu.showMainMenu(frame));

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());  
        buttonPanel.add(backButton);  
        buttonPanel.add(Box.createVerticalGlue());  

        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the content pane of the frame to the panel
        frame.setContentPane(panel);
        frame.revalidate();
    }
}
