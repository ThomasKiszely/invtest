import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory {
    private int slotCurrent;
    private int slotCurrentMax = 50;
    private final int slotMax = 192;
    private int weightCurrent;
    private int weightCurrentMax = 32;
    private final int weightMax = 50;
    public List<Item> items = new ArrayList<>();

    DatabaseRepository repository = new DatabaseRepository();

    public Inventory() {
    }

    public Inventory(int slotCurrent, int weightCurrent, List<Item> items) {
        this.slotCurrent = slotCurrent;
        this.weightCurrent = weightCurrent;
        this.items = items;
    }

    public void setSlotCurrent(int slotCurrent) {
        this.slotCurrent = slotCurrent;
    }

    public void setWeightCurrent(int weightCurrent) {
        this.weightCurrent = weightCurrent;
    }

    public int getSlotCurrent() {
        return slotCurrent;
    }

    public int getWeightCurrent() {
        return weightCurrent;
    }

    public int getSlotCurrentMax() {
        return slotCurrentMax;
    }

    public void setSlotCurrentMax(int slotCurrentMax) {
        this.slotCurrentMax = slotCurrentMax;
    }

    public int getWeightCurrentMax() {
        return weightCurrentMax;
    }

    public void setWeightCurrentMax(int weightCurrentMax) {
        this.weightCurrentMax = weightCurrentMax;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Inventory initiateInventory(int id) {
        int inventoryId = id;
        int weight = 0;
        int slot = 0;
        List<Item> items = repository.initiateInventory(inventoryId);
        if (items != null) {
            for (Item item : items) {
                weight += item.getWeight();
                slot++;
                //System.out.println(item.toString());

            }
            setSlotCurrent(slot);
            setWeightCurrent(weight);
            return new Inventory(slot, weight, items);
//        return ("weight is " + weight + " and used slots are " + slot);
        }
        return null;
    }

    public String addItemToInventory(int invId, int itemid) {
        Item item = repository.getOneItem(itemid);
        System.out.println(item.getType());
        if (item.getWeight() + weightCurrent < weightMax) {
            if (slotCurrent < slotMax) {
                String added = repository.addItemToInventory(invId, itemid);
                items.add(item);
                slotCurrent ++;
                weightCurrent += item.getWeight();
                System.out.println(item);
                return added;
            }
        } else {
            return "Inventory Full";
        }

        return null;
    }

    public String removeItemFromInventory(int invId, int itemid) {
        Item item = repository.getOneItem(itemid);
        String removed = repository.removeItemFromInventory(invId, itemid);
        System.out.println(item);
        if (removed != null) {
            System.out.println(item);
            for (int i = 0; i < items.size(); i++) {
                Item obj = items.get(i);
                if (obj.getId() == itemid) {
                    items.remove(i);
                    slotCurrent -= item.getWeight();
                    weightCurrent -= item.getWeight();
                    return "Item removed from inventory";
                }
            }

//            items.remove(items.indexOf(item));
//            System.out.println(item.getName());


        } else {
            return "Item cannot be removed";
        }
        return null;
    }


//    List <Item> items = new ArrayList<>();
//    Item item1 = new Armor(20, "Dark helmet", "Helmet", 5, "StoopidHelmet", 4);
//    Item item2 = new Armor(25, "chestplate", "chest", 10, "Heyheyheyhey", 6);
//    Item item3 = new Armor(35, "leggings");
//    Item item4 = new Armor(45, "boots");
//    public List<Item> itemsList() {
//        items.add(item1);
//        items.add(item2);
//        items.add(item3);
//        items.add(item4);
//        for (Item item : items) {
//            System.out.println(item.getWeight());
//        }
//        return items;
//    }


}





