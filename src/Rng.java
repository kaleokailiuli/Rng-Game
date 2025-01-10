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
    public static final Map<String, String> ITEM_DESCRIPTIONS = new HashMap<>();
    private static final Map<String, Color> RARITY_COLORS = new HashMap<>();

    private static Inventory inventory = new Inventory();
    private static Random random = new Random();

    private static JLabel resultLabel;
    private static JTextArea inventoryTextArea;
    private static JPanel inventoryPanel;

    // Admin panel
    private static boolean forceNextRoll = false;
    private static Item forcedItem = null;

    static {
        // item descriptions
        ITEM_DESCRIPTIONS.put("Common Sword", "A basic sword with minimal damage.");
        ITEM_DESCRIPTIONS.put("Rare Shield", "A sturdy shield with good durability.");
        ITEM_DESCRIPTIONS.put("Epic Potion", "A powerful potion that restores a lot of health.");
        ITEM_DESCRIPTIONS.put("Legendary Armor", "A legendary piece of armor with high defense.");
        ITEM_DESCRIPTIONS.put("Mythic Sword", "A mystical sword imbued with ancient power.");
        ITEM_DESCRIPTIONS.put("Secret Shield", "A shield forged in secrecy with unique properties.");
        ITEM_DESCRIPTIONS.put("Godly Armor", "Armor blessed by the gods, offering unparalleled protection.");

        // rarity colors
        RARITY_COLORS.put("common", Color.WHITE);
        RARITY_COLORS.put("rare", Color.BLUE);
        RARITY_COLORS.put("epic", Color.MAGENTA);
        RARITY_COLORS.put("legendary", Color.ORANGE);
        RARITY_COLORS.put("mythic", Color.CYAN);
        RARITY_COLORS.put("secret", Color.PINK);
        RARITY_COLORS.put("godly", Color.YELLOW);
    }

    public static Item rollItem() {
        if (forceNextRoll && forcedItem != null) {
            forceNextRoll = false;
            return forcedItem;
        }

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

            //  color of the result text based on item rarity
            Color rarityColor = RARITY_COLORS.get(item.getRarity());
            resultLabel.setForeground(rarityColor);  // color of the text

            // font size for the rolled item message
            resultLabel.setFont(new Font("Arial", Font.BOLD, 24));  //font size
            resultLabel.setText("You rolled: " + item.getName() + " (" + item.getRarity() + ")");
        } else {
            resultLabel.setText("No item rolled.");
        }
    }

    public static void updateInventoryDisplay() {
        inventoryTextArea.setText(inventory.getInventoryString());
    }

    public static void showAdminPanel() {
        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setSize(400, 200);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLayout(new BorderLayout());
        adminFrame.getContentPane().setBackground(Color.BLACK);

        JLabel adminLabel = new JLabel("Force Next Roll Item:");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adminLabel.setForeground(Color.WHITE);

        JComboBox<String> itemDropdown = new JComboBox<>(ITEM_NAMES);
        itemDropdown.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton setItemButton = new JButton("Set Forced Item");
        setItemButton.setFont(new Font("Arial", Font.BOLD, 14));
        setItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemDropdown.getSelectedItem();
                if (selectedItem != null) {
                    String rarity = null;
                    for (int i = 0; i < ITEM_NAMES.length; i++) {
                        if (ITEM_NAMES[i].equals(selectedItem)) {
                            rarity = RARITIES[i];
                            break;
                        }
                    }
                    if (rarity != null) {
                        forcedItem = new Item(selectedItem, rarity);
                        forceNextRoll = true;
                        JOptionPane.showMessageDialog(adminFrame, "Next roll will give: " + selectedItem + " (" + rarity + ")", "Admin Override Set", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBackground(Color.BLACK);
        panel.add(adminLabel);
        panel.add(itemDropdown);
        panel.add(setItemButton);

        adminFrame.add(panel, BorderLayout.CENTER);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    // method for item descriptions
    public static String getItemDescription(String itemName) {
        return ITEM_DESCRIPTIONS.get(itemName);
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
        buttonPanel.setLayout(new GridLayout(3, 2, 20, 20));
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

        JButton adminPanelButton = new JButton("Admin Panel");
        adminPanelButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdminPanel();
            }
        });

        JButton itemIndexButton = new JButton("Item Index");
        itemIndexButton.setFont(new Font("Arial", Font.BOLD, 16));
        itemIndexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemIndex.showItemIndex(inventory);
            }
        });

        buttonPanel.add(rollButton);
        buttonPanel.add(openInventoryButton);
        buttonPanel.add(adminPanelButton);
        buttonPanel.add(itemIndexButton);

        resultLabel = new JLabel("Click 'Roll for an Item' to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 24));
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
        inventoryTextArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeInventoryButton = new JButton("Close Inventory");
        closeInventoryButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.setVisible(false);
            }
        });

        inventoryPanel.add(closeInventoryButton, BorderLayout.SOUTH);

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
