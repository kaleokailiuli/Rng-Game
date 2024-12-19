import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Rng {
    // Map to store item names and their quantities
    private static Map<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {
        // Set up the frame
        JFrame frame = new JFrame("RNG Button Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);  // Increased size
        frame.setLayout(new GridBagLayout());  // Using GridBagLayout for better control over placement

        // Set background color to black
        frame.getContentPane().setBackground(Color.BLACK);

        // Create GridBagConstraints to place the components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);  // Padding around components

        // Create a label to display the random number
        JLabel label = new JLabel("Click the Button to spin");
        label.setFont(new Font("Arial", Font.BOLD, 30));  // Set font size for better readability
        label.setForeground(Color.WHITE);  // Set text color to white for visibility on black background
        constraints.gridx = 0;  // Set the column of the label
        constraints.gridy = 0;  // Set the row of the label
        constraints.gridwidth = 2;  // Make label span two columns
        constraints.anchor = GridBagConstraints.CENTER;  // Center the label horizontally
        frame.add(label, constraints);

        // Create a button to roll items
        JButton rngButton = new JButton("Roll Item");
        rngButton.setFont(new Font("Arial", Font.PLAIN, 20));  // Adjusted font size for the button
        rngButton.setPreferredSize(new Dimension(200, 60));  // Set a preferred size for the button
        constraints.gridx = 0;  // Set the column for the button
        constraints.gridy = 1;  // Set the row for the button (below the label)
        constraints.gridwidth = 2;  // Make button span two columns (centering it)
        constraints.anchor = GridBagConstraints.CENTER;  // Center the button horizontally
        frame.add(rngButton, constraints);

        // Create a button for viewing the inventory
        JButton inventoryButton = new JButton("View Inventory");
        inventoryButton.setFont(new Font("Arial", Font.PLAIN, 20));
        inventoryButton.setPreferredSize(new Dimension(200, 60));
        constraints.gridy = 2;  // Place this below the Roll Item button
        frame.add(inventoryButton, constraints);

        // Set up the random number generator
        Random random = new Random();

        // Create an array of Item objects with updated ranges including Mythic and Secret items
        Item[] items = {
            new Item("Common Item", Color.GREEN, 0, 6000),        // 60%
            new Item("Uncommon Item", Color.CYAN, 6001, 8500),    // 25%
            new Item("Rare Item", Color.BLUE, 8501, 9300),         // 8%
            new Item("Epic Item", Color.MAGENTA, 9301, 9800),      // 5%
            new Item("Legendary Item", Color.ORANGE, 9801, 9950), // 1.5%
            new Item("Mythic Item", Color.YELLOW, 9951, 9995),    // 0.5%
            new Item("Secret Item", Color.PINK, 9996, 10000)      // 0.1%
        };

        // Add button action listener to roll items
        rngButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate a random number between 1 and 10000
                int randomNumber = random.nextInt(10000) + 1;  // Random number between 1 and 10000
                Item rolledItem = getItemBasedOnNumber(randomNumber, items);

                // Update the label to show the item and its rarity in the format "Item Name (Rarity)"
                label.setText("<html><b>" + rolledItem.getName() + " (" + getRarityName(rolledItem) + ")</b></html>");
                label.setForeground(rolledItem.getColor());  // Set text color based on item

                // Add the rolled item to the inventory list or update its quantity
                updateInventory(rolledItem.getName());
            }
        });

        // Add action listener to inventory button to view the inventory
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new window to show the inventory using InventoryGUI
                InventoryGUI.showInventory(inventory);
            }
        });

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to determine the item based on the random number
    private static Item getItemBasedOnNumber(int number, Item[] items) {
        for (Item item : items) {
            if (number >= item.getMinRange() && number <= item.getMaxRange()) {
                return item;  // Return the matching item
            }
        }
        return null;  // In case no item is found, though unlikely
    }

    // Method to update inventory (increment item quantity if already exists)
    private static void updateInventory(String itemName) {
        if (inventory.containsKey(itemName)) {
            // If item exists in the inventory, increase its quantity
            inventory.put(itemName, inventory.get(itemName) + 1);
        } else {
            // If item doesn't exist, add it to the inventory with quantity 1
            inventory.put(itemName, 1);
        }
    }

    // Method to get the rarity name based on item type
    private static String getRarityName(Item item) {
        String name = item.getName();
        switch (name) {
            case "Common Item":
                return "Common";
            case "Uncommon Item":
                return "Uncommon";
            case "Rare Item":
                return "Rare";
            case "Epic Item":
                return "Epic";
            case "Legendary Item":
                return "Legendary";
            case "Mythic Item":
                return "Mythic";
            case "Secret Item":
                return "Secret";
            default:
                return "Unknown";
        }
    }
}
