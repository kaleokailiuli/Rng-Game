import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RNG {
    private static final String[] ITEM_NAMES = {"Common Sword", "Rare Shield", "Epic Potion", "Legendary Armor"};
    private static final String[] RARITIES = {"common", "rare", "epic", "legendary"};
    private static final double[] RARITY_WEIGHTS = {0.7, 0.2, 0.08, 0.02}; // Common: 70%, Rare: 20%, Epic: 8%, Legendary: 2%

    private static Inventory inventory = new Inventory();
    private static Random random = new Random();

    private static JLabel resultLabel;
    private static JTextArea inventoryTextArea;
    private static JPanel inventoryPanel; // Panel to hold the inventory display
    private static Item lastRolledItem; // Store the last rolled item

    // Roll an item based on the rarity probabilities
    public static Item rollItem() {
        double roll = random.nextDouble(); // Generates a double between 0 and 1
        double cumulativeProbability = 0.0;

        // Determine which rarity the roll corresponds to
        for (int i = 0; i < ITEM_NAMES.length; i++) {
            cumulativeProbability += RARITY_WEIGHTS[i];
            if (roll < cumulativeProbability) {
                return new Item(ITEM_NAMES[i], RARITIES[i]);
            }
        }
        return null; // In case something goes wrong
    }

    // Add rolled item to the inventory
    public static void rollAndAddToInventory() {
        Item item = rollItem();
        if (item != null) {
            inventory.addItem(item); // Add the item to the inventory
            lastRolledItem = item; // Store the last rolled item
            // Update the result label to show the item name and rarity
            resultLabel.setText("You rolled: " + item.getName() + " (" + item.getRarity() + ")");
        } else {
            resultLabel.setText("No item rolled.");
        }
    }

    // Update the inventory display
    public static void updateInventoryDisplay() {
        inventoryTextArea.setText(inventory.getInventoryString());
    }

    // Set up the GUI
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("RNG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Create UI components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        resultLabel = new JLabel("Click the button to roll an item");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton rollButton = new JButton("Roll for an Item");
        rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollAndAddToInventory(); // Roll the item and update the result label
            }
        });

        JButton openInventoryButton = new JButton("Open Inventory");
        openInventoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the inventory panel when the button is clicked
                inventoryPanel.setVisible(true);
                updateInventoryDisplay(); // Update inventory display
            }
        });

        // Close Inventory Button
        JButton closeInventoryButton = new JButton("Close Inventory");
        closeInventoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the inventory panel when the button is clicked
                inventoryPanel.setVisible(false);
            }
        });

        // Panel for displaying inventory
        inventoryTextArea = new JTextArea();
        inventoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));

        // Panel for inventory, initially hidden
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BorderLayout());
        inventoryPanel.setVisible(false); // Start with inventory hidden
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);
        inventoryPanel.add(closeInventoryButton, BorderLayout.SOUTH); // Add close button to inventory panel

        // Add components to the main panel
        panel.add(resultLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(rollButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(openInventoryButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inventoryPanel);

        // Add the main panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Main method to start the game
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
