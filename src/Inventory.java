import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory {
    private int id;
    private int slotCurrent;
    private int slotCurrentMax = 32;
    private final int slotMax = 192;
    private int weightCurrent;
    private final int weightMax = 50;
    public List<Item> items = new ArrayList<>();

    DatabaseRepository repository = new DatabaseRepository();

    public Inventory() {
    }

    public Inventory(int id, int slotCurrent, int weightCurrent, List<Item> items) {
        this.id = id;
        this.slotCurrent = slotCurrent;
        this.weightCurrent = weightCurrent;
        this.items = items;
    }

    public int getId() {
        return id;
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

    public int getWeightMax() {
        return weightMax;
    }

    public int getSlotCurrentMax() {
        return slotCurrentMax;
    }

    public void setSlotCurrentMax(int slotCurrentMax) {
        this.slotCurrentMax = slotCurrentMax;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getSlotMax() {
        return slotMax;
    }

    public Inventory initiateInventory(int id) {
        int inventoryId = id;
        int weight = 0;
        int slot = repository.initiateSlots(id);
        List<Item> items = repository.initiateInventory(inventoryId);
        if (items.isEmpty()) {
            return new Inventory(inventoryId, slot, weight, items);
        } else {
            for (Item item : items) {
                weight += item.getWeight();
            }
            setSlotCurrent(slot);
            setWeightCurrent(weight);
            return new Inventory(inventoryId, slot, weight, items);
        }
    }

    public String addItemToInventory(int invId, int itemid) {
        Item item = repository.getOneItem(itemid);
        System.out.println(item.getType());
        if (checkWeight(item, weightCurrent, weightMax)) {
            if (checkItemStack(items, item)) { // Kalder CheckItemStack korrekt
                System.out.println(item.getName());
                if (slotCurrent <= slotCurrentMax) {
                    String added = repository.addItemToInventory(invId, itemid);
                    items.add(item);
                    //slotCurrent++;
                    weightCurrent += item.getWeight();
                    System.out.println("Item added: " + item);
                    return added;
                } else {
                    return "No available slots.";
                }
            } else if (checkAvailableSlot(slotCurrent, slotCurrentMax)) {
                String added = repository.addItemToInventory(invId, itemid);
                items.add(item);
                slotCurrent++;
                String slotSet = repository.setSlot(slotCurrent, invId);
                System.out.println("Slot set: " + slotSet);
                weightCurrent += item.getWeight();
                System.out.println("Item added: " + item);
                return added;
            } else {
                return "No available slots.";
            }
        } else {
            return "Inventory Full";
        }
    }

    public String removeItemFromInventory(int invId, int itemid) {
        Item item = repository.getOneItem(itemid);
        String removed = repository.removeItemFromInventory(invId, itemid);
        System.out.println(item);
        if (removed != null) {
            System.out.println(item + " to be removed");
            for (int i = 0; i < items.size(); i++) {
                Item obj = items.get(i);
                if (obj.getId() == itemid) {
                    items.remove(i);
                    slotCurrent --;
                    if (checkItemStack(items, item)) {
                        slotCurrent++;
                    }
                    weightCurrent -= item.getWeight();
                    String slotSet = repository.setSlot(slotCurrent, invId);
                    System.out.println("Slot set: " + slotSet);
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
    public boolean checkItemStack(List<Item> items, Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item obj = items.get(i);
            if (obj.getId() == item.getId()) {
//        for (Item currentItem : items) {
//            System.out.println("Checking item: " + currentItem + " against " + item);
//            if (currentItem.equals(item)) { // Sammenlign objekter
//                System.out.println("Success: Item found in stack - " + currentItem.getName());
                System.out.println("Success");
                if (item.getType().equals("Consumable")) {
                   //slotCurrent--;
                    return true;
                }
            }
        }
        System.out.println("Item not found in stack.");

        return false;
    }
    public boolean checkWeight(Item item, int weightCurrent, int weightMax) {
        if (item.getWeight() + weightCurrent <= weightMax) {
            return true;
        }
        return false;
    }
    public boolean checkAvailableSlot(int slotCurrent, int slotCurrentMax){
        if ((slotCurrent < slotCurrentMax)) {
            return true;
        }
        return false;
    }
    public String incrementMaxSlot(int slotCurrentMax, int slotMax) {
        int slotNewCurrentMax = slotCurrentMax;
        if (slotCurrentMax <= slotMax - 10) {
            slotNewCurrentMax = (slotCurrentMax + 10);
            setSlotCurrentMax(slotNewCurrentMax);
            String newSlotSize = repository.setSlotSize(slotNewCurrentMax, getId());
            System.out.println(newSlotSize);
            return "Slot size is now " + slotNewCurrentMax;
        }
        else{
            return "Slot size couldn't be changed";
        }
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





