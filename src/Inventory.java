import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Inventory {
    public static void showInventory(List<Item> inventory) {
        // Create a new frame for the inventory
        JFrame inventoryFrame = new JFrame("Inventory");
        inventoryFrame.setSize(400, 400);  // Set size for the inventory window
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close only the inventory window

        // Create a panel to hold the list of items
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Vertical layout for the list
        panel.setBackground(Color.BLACK);

        // Add a label for the inventory title
        JLabel inventoryLabel = new JLabel("Your Inventory:");
        inventoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        inventoryLabel.setForeground(Color.WHITE);
        panel.add(inventoryLabel);

        // Add each item in the inventory to the panel
        for (Item item : inventory) {
            JLabel itemLabel = new JLabel(item.getName());
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            itemLabel.setForeground(item.getColor());  // Set color based on item
            panel.add(itemLabel);
        }

        // Add the panel to the inventory window and make it visible
        inventoryFrame.add(new JScrollPane(panel));  // Wrap in scroll pane for scrolling
        inventoryFrame.setLocationRelativeTo(null);  // Center the inventory window
        inventoryFrame.setVisible(true);
    }
}
