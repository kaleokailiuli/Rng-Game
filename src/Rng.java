import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RNG {
    private static final String[] ITEM_NAMES = {
        "Common Sword", "Rare Shield", "Epic Potion", "Legendary Armor",
        "Mythic Sword", "Secret Shield", "Godly Armor"
    };
    private static final String[] RARITIES = {
        "common", "rare", "epic", "legendary", "mythic", "secret", "godly"
    };
    private static final double[] RARITY_WEIGHTS = {
        0.7, 0.2, 0.08, 0.02, 0.005, 0.001, 0.0001
    };
    private static final Map<String, String> ITEM_DESCRIPTIONS = new HashMap<>();
    private static final Map<String, Integer> ITEM_PRICES = new HashMap<>();
    private static final Map<String, Color> RARITY_COLORS = new HashMap<>();

    private static Inventory inventory = new Inventory();
    private static Random random = new Random();
    private static int coins = 0;  // Track the number of coins

    private static JLabel resultLabel;
    private static JTextArea inventoryTextArea;
    private static JPanel inventoryPanel;
    private static JLabel coinsLabel;  // To display the number of coins

    static {
        // Initialize item descriptions
        ITEM_DESCRIPTIONS.put("Common Sword", "A basic sword with minimal damage.");
        ITEM_DESCRIPTIONS.put("Rare Shield", "A sturdy shield with good durability.");
        ITEM_DESCRIPTIONS.put("Epic Potion", "A powerful potion that restores a lot of health.");
        ITEM_DESCRIPTIONS.put("Legendary Armor", "A legendary piece of armor with high defense.");
        ITEM_DESCRIPTIONS.put("Mythic Sword", "A mystical sword imbued with ancient power.");
        ITEM_DESCRIPTIONS.put("Secret Shield", "A shield forged in secrecy with unique properties.");
        ITEM_DESCRIPTIONS.put("Godly Armor", "Armor blessed by the gods, offering unparalleled protection.");

        // Initialize rarity colors
        RARITY_COLORS.put("common", Color.WHITE);
        RARITY_COLORS.put("rare", Color.BLUE);
        RARITY_COLORS.put("epic", Color.MAGENTA);
        RARITY_COLORS.put("legendary", Color.ORANGE);
        RARITY_COLORS.put("mythic", Color.CYAN);
        RARITY_COLORS.put("secret", Color.PINK);
        RARITY_COLORS.put("godly", Color.YELLOW);

        // Set item prices for selling
        ITEM_PRICES.put("Common Sword", 10);
        ITEM_PRICES.put("Rare Shield", 50);
        ITEM_PRICES.put("Epic Potion", 100);
        ITEM_PRICES.put("Legendary Armor", 200);
        ITEM_PRICES.put("Mythic Sword", 500);
        ITEM_PRICES.put("Secret Shield", 1000);
        ITEM_PRICES.put("Godly Armor", 5000);
    }

    public static Item rollItem() {
        double roll = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < ITEM_NAMES.length; i++) {
            cumulativeProbability += RARITY_WEIGHTS[i];
            if (roll < cumulativeProbability) {
                return new Item(ITEM_NAMES[i], RARITIES[i]);
            }
        }
        return null;
    }

    public static void rollAndAddToInventory() {
        Item item = rollItem();
        if (item != null) {
            inventory.addItem(item);

            // Set the color of the result text based on item rarity
            Color rarityColor = RARITY_COLORS.get(item.getRarity());
            resultLabel.setForeground(rarityColor);  // Set the color of the text

            // Set a larger font size for the rolled item message
            resultLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Larger font size
            resultLabel.setText("You rolled: " + item.getName() + " (" + item.getRarity() + ")");
        } else {
            resultLabel.setText("No item rolled.");
        }
    }

    public static void updateInventoryDisplay() {
        inventoryTextArea.setText(inventory.getInventoryString());
    }

    // New method to handle selling items from inventory
    public static void sellItem(String itemName) {
        if (inventory.hasItem(itemName)) {
            int price = ITEM_PRICES.getOrDefault(itemName, 0);
            coins += price;
            inventory.removeItem(itemName);
            updateInventoryDisplay();  // Refresh the inventory display

            // Update the coin label to reflect the new balance
            coinsLabel.setText("Coins: " + coins);
        }
    }

    public static void showItemIndex() {
        JFrame indexFrame = new JFrame("Item Index");
        indexFrame.setSize(800, 400);
        indexFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        indexFrame.setLayout(new BorderLayout());
        indexFrame.getContentPane().setBackground(Color.BLACK);

        // Left side: Item list
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.BLACK);

        JLabel descriptionLabel = new JLabel("Click on an item to see its description.");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Larger font size
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT); // Left align text
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] itemNames = {
            "Common Sword", "Rare Shield", "Epic Potion", "Legendary Armor",
            "Mythic Sword", "Secret Shield", "Godly Armor"
        };

        for (String itemName : itemNames) {
            JLabel itemLabel = new JLabel(itemName);
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            itemLabel.setForeground(Color.WHITE);
            itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (inventory.hasItem(itemName)) {
                itemLabel.setFont(new Font("Arial", Font.BOLD, 16));
                itemLabel.setForeground(Color.GREEN);
            }

            itemLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    descriptionLabel.setText("<html><div style='text-align: left;'>" + ITEM_DESCRIPTIONS.getOrDefault(itemName, "No description available.") + "</div></html>");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    itemLabel.setForeground(Color.YELLOW); // Highlight on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    itemLabel.setForeground(inventory.hasItem(itemName) ? Color.GREEN : Color.WHITE); // Reset color
                }
            });

            itemLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // If the item is clicked, prompt to sell it
                    int confirmation = JOptionPane.showConfirmDialog(indexFrame, 
                            "Do you want to sell " + itemName + " for " + ITEM_PRICES.get(itemName) + " coins?", 
                            "Sell Item", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        sellItem(itemName);
                    }
                }
            });

            itemPanel.add(Box.createVerticalStrut(10));
            itemPanel.add(itemLabel);
        }

        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.BLACK);

        // Right side: Item description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setBackground(Color.BLACK);
        descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);

        // SplitPane for left and right sections
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, descriptionPanel);
        splitPane.setDividerLocation(500); // Adjust divider position
        splitPane.setDividerSize(5);
        splitPane.setEnabled(false); // Disable resizing the divider

        indexFrame.add(splitPane, BorderLayout.CENTER);
        indexFrame.setLocationRelativeTo(null);
        indexFrame.setVisible(true);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("RNG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("RNG Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.setBackground(Color.BLACK);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(Color.BLACK);

        JButton rollButton = new JButton("Roll for an Item");
        rollButton.setFont(new Font("Arial", Font.BOLD, 16));
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollAndAddToInventory();
            }
        });

        JButton openInventoryButton = new JButton("Open Inventory");
        openInventoryButton.setFont(new Font("Arial", Font.BOLD, 16));
        openInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.setVisible(true);
                updateInventoryDisplay();
            }
        });

        JButton closeInventoryButton = new JButton("Close Inventory");
        closeInventoryButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.setVisible(false);
            }
        });

        JButton indexButton = new JButton("Item Index");
        indexButton.setFont(new Font("Arial", Font.BOLD, 16));
        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showItemIndex();
            }
        });

        buttonPanel.add(rollButton);
        buttonPanel.add(openInventoryButton);
        buttonPanel.add(indexButton);
        buttonPanel.add(closeInventoryButton);

        resultLabel = new JLabel("Click 'Roll for an Item' to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultLabel.setForeground(Color.WHITE);

        // Display for coins in the top-left corner
        coinsLabel = new JLabel("Coins: " + coins, SwingConstants.LEFT);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coinsLabel.setForeground(Color.WHITE);
        coinsLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        mainPanel.add(resultLabel);
        mainPanel.add(buttonPanel);

        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BorderLayout());
        inventoryPanel.setBackground(Color.BLACK);
        inventoryPanel.setVisible(false);

        inventoryTextArea = new JTextArea();
        inventoryTextArea.setEditable(false);
        inventoryTextArea.setBackground(Color.BLACK);
        inventoryTextArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(coinsLabel, BorderLayout.WEST); // Add coins label to top-left
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(inventoryPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
