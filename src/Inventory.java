import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(Item item) {
        items.put(item.getName(), items.getOrDefault(item.getName(), 0) + 1);
    }

    public boolean hasItem(String itemName) {
        return items.containsKey(itemName);
    }

    public String getInventoryString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey())
              .append(" [")
              .append(entry.getValue())
              .append("]\n");
        }
        return sb.toString();
    }
}
