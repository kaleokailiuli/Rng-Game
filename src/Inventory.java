import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Item> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    // Add item to the inventory
    public void addItem(Item item) {
        if (items.containsKey(item.getName())) {
            Item existingItem = items.get(item.getName());
            existingItem.incrementQuantity();
        } else {
            items.put(item.getName(), item);
        }
    }

    // Get the string representation of the inventory
    public String getInventoryString() {
        StringBuilder inventoryString = new StringBuilder();
        for (Item item : items.values()) {
            inventoryString.append(item.getName())
                .append(" [")
                .append(item.getQuantity())
                .append("] (")
                .append(item.getRarity())
                .append(")\n");
        }
        return inventoryString.toString();
    }

    // Get the quantity of a specific item
    public int getItemQuantity(String itemName) {
        Item item = items.get(itemName);
        return (item != null) ? item.getQuantity() : 0;
    }
}
