import java.awt.*;

public class Item {
    private String name;
    private Color color;
    private int minRange;
    private int maxRange;

    // Constructor
    public Item(String name, Color color, int minRange, int maxRange) {
        this.name = name;
        this.color = color;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
