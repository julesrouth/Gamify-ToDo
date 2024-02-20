public class Item {
    private String name;
    private ItemStat stats;
    private String attributes;

    public Item(String name, ItemStat stats, String attributes) {
        this.name = name;
        this.stats = stats;
        this.attributes = attributes;
    }

    public Item(String name, ItemStat stats) {
        this(name, stats, "");
    }
    @Override
    public String toString() {
        return name;
    }

    public ItemStat getStats() {
        return stats;
    }

    public int getPrice() {
        return 0;
    }
    public String getAttributes() {
        return attributes;
    } 

    public String getName() {
        return name;
    }
    public static void main(String[] args) {
        System.out.println("Testing Item");
        Item item = new Item("Sword", new ItemStat(10, 0, 0, 0, 0, 0, ""), "");
        System.out.println(item);
        System.out.println(item.getStats());
        System.out.println(item.getAttributes());
    }
}
