import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private static Inventory inventory = new Inventory();
    private static Random random = new Random();

    private static JLabel resultLabel;
    private static JTextArea inventoryTextArea;
    private static JPanel inventoryPanel;

    static {
        // Initialize item descriptions
        ITEM_DESCRIPTIONS.put("Common Sword", "A basic sword with minimal damage.");
        ITEM_DESCRIPTIONS.put("Rare Shield", "A sturdy shield with good durability.");
        ITEM_DESCRIPTIONS.put("Epic Potion", "A powerful potion that restores a lot of health.");
        ITEM_DESCRIPTIONS.put("Legendary Armor", "A legendary piece of armor with high defense.");
        ITEM_DESCRIPTIONS.put("Mythic Sword", "A mystical sword imbued with ancient power.");
        ITEM_DESCRIPTIONS.put("Secret Shield", "A shield forged in secrecy with unique properties.");
        ITEM_DESCRIPTIONS.put("Godly Armor", "Armor blessed by the gods, offering unparalleled protection.");
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
            resultLabel.setText("You rolled: " + item.getName() + " (" + item.getRarity() + ")");
        } else {
            resultLabel.setText("No item rolled.");
        }
    }

    public static void updateInventoryDisplay() {
        inventoryTextArea.setText(inventory.getInventoryString());
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
