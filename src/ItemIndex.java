import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemIndex {
    public static void showItemIndex(Inventory inventory) {
        // Create the frame for the item index
        JFrame indexFrame = new JFrame("Item Index");
        indexFrame.setSize(500, 400); // Increased size
        indexFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        indexFrame.setLayout(new BorderLayout());
        indexFrame.getContentPane().setBackground(Color.BLACK); // Set background to black

        // Panel to hold item labels
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.BLACK); // Black background for the panel

        String[] itemNames = {
            "Common Sword", "Rare Shield", "Epic Potion", "Legendary Armor",
            "Mythic Sword", "Secret Shield", "Godly Armor"
        };

        // Add labels for each item
        for (String itemName : itemNames) {
            JLabel itemLabel = new JLabel(itemName);
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            itemLabel.setForeground(Color.WHITE); // Default text color is white
            itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Highlight items in green if the player has them
            if (inventory.hasItem(itemName)) {
                itemLabel.setFont(new Font("Arial", Font.BOLD, 16));
                itemLabel.setForeground(Color.GREEN);
            }

            // Action listener for showing item descriptions
            itemLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    String description = RNG.getItemDescription(itemName); // Use the getter method
                    JOptionPane.showMessageDialog(indexFrame, description, itemName + " Description", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            itemPanel.add(Box.createVerticalStrut(10)); // Add spacing between items
            itemPanel.add(itemLabel);
        }

        // Add a scroll pane to the item panel
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.BLACK);

        // Add the scroll pane to the frame
        indexFrame.add(scrollPane, BorderLayout.CENTER);

        // Center the window on the screen and make it visible
        indexFrame.setLocationRelativeTo(null);
        indexFrame.setVisible(true);
    }
}
