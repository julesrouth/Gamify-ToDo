import java.util.ArrayList;
import java.util.List;
class Inventory {
    private List<Item> items;
    public Inventory() {
        this.items = new ArrayList<>();
    }



    public void addItem(Item item) {
        items.add(item);
    }

    public void add(Item item) {
        addItem(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public void remove(Item item){
        removeItem(item);
    }
    public List<Item> getItems() {
        return items;
    }
    public String printItems() {
        StringBuilder tempStr = new StringBuilder();
        for (Item item : items) {
            tempStr.append(item).append("\n");
        }
        return tempStr.toString();
    }
}
