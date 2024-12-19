import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class InventoryGUI {

    // Define rarity rankings for sorting
    private static final Map<String, Integer> rarityRank = new HashMap<>();
    static {
        rarityRank.put("Secret Item", 1);
        rarityRank.put("Mythic Item", 2);
        rarityRank.put("Legendary Item", 3);
        rarityRank.put("Epic Item", 4);
        rarityRank.put("Rare Item", 5);
        rarityRank.put("Uncommon Item", 6);
        rarityRank.put("Common Item", 7);
    }

    public static void showInventory(Map<String, Integer> inventory) {
        // Sort inventory by rarity
        List<Map.Entry<String, Integer>> sortedInventory = new ArrayList<>(inventory.entrySet());
        sortedInventory.sort((entry1, entry2) -> {
            int rank1 = rarityRank.get(entry1.getKey());
            int rank2 = rarityRank.get(entry2.getKey());
            return Integer.compare(rank1, rank2);  // Sort based on rarity rank
        });

        // Create a frame for the inventory display
        JFrame inventoryFrame = new JFrame("Inventory");
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inventoryFrame.setSize(400, 400);
        inventoryFrame.setLayout(new BoxLayout(inventoryFrame.getContentPane(), BoxLayout.Y_AXIS));

        // Create a label for each item in the sorted inventory
        for (Map.Entry<String, Integer> entry : sortedInventory) {
            String itemName = entry.getKey();
            Integer quantity = entry.getValue();
            JLabel itemLabel = new JLabel(itemName + " (" + quantity + ")");
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            itemLabel.setForeground(getItemColor(itemName));
            inventoryFrame.add(itemLabel);
        }

        // Show the inventory window
        inventoryFrame.setVisible(true);
    }

    // Method to return the color associated with the item
    private static Color getItemColor(String itemName) {
        switch (itemName) {
            case "Common Item":
                return Color.GREEN;
            case "Uncommon Item":
                return Color.CYAN;
            case "Rare Item":
                return Color.BLUE;
            case "Epic Item":
                return Color.MAGENTA;
            case "Legendary Item":
                return Color.ORANGE;
            case "Mythic Item":
                return Color.YELLOW;
            case "Secret Item":
                return Color.PINK;
            default:
                return Color.WHITE;  // Default color
        }
    }
}
