public class Item {
    private String name;
    private String rarity;
    private int quantity;

    public Item(String name, String rarity) {
        this.name = name;
        this.rarity = rarity;
        this.quantity = 1; // Every item starts with a quantity of 1
    }

    // Getters and setters for name, rarity, and quantity
    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }
}
