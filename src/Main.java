import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseRepository repository = new DatabaseRepository();
        List<Item> itemsMain = new ArrayList<>();
        Inventory inventory = new Inventory(0,0, 0, itemsMain);
        Scanner input = new Scanner(System.in);

        System.out.println("---Initializing---");
        inventory = inventory.initiateInventory(1);

        while (true) {
            System.out.println("Welcome to Legend of Zel... GameCraft\nChoose your poison\n1 to create item" +
                    "\n2 to see a list of items\n3 to update an item\n4 to delete an item\n5 to make a new inventory" +
                    "\n6 to choose inventory and show its items" +
                    "\n7 to add an item to inventory\n8 to remove item from inventory\n9 to show items in inventory" +
                    "\n10 to increment slot size by 10\n11 to write out a text file of your inventory\n12 to exit" +
                    "\n");
            try {
                int choice = input.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println("You chose 1");
                        System.out.println("Enter id");
                        int id = input.nextInt();
                        input.nextLine();
                        System.out.println("Indtast navn");
                        String name = input.nextLine();
                        System.out.println("Indtast type");
                        String type = input.nextLine();
                        System.out.println("Indtast vægt");
                        int itemWeight = input.nextInt();
                        System.out.println("Indtast beskrivelse");
                        input.nextLine();
                        String itemDescription = input.nextLine();
                        System.out.println("Indtast effekt");
                        int itemEffect = input.nextInt();
                        Item item = new Item(id, name, type, itemWeight, itemDescription, itemEffect);
                        String answer = repository.addItem(item);
                        System.out.println(answer);
                    }
                    case 2 -> {
                        System.out.println("You chose 2 see a list of items");
                        List<Item> items1 = repository.getAllItems();
                        for (Item item : items1) {
                            System.out.println(item);
                        }
                    }
                    case 3 -> {
                        System.out.println("You chose 3");
                        System.out.println("Indtast id på 'Item' du vil opdatere");
                        int id = input.nextInt();
                        input.nextLine();
                        System.out.println("Indtast navn på 'Item'");
                        String name = input.nextLine();
                        System.out.println("Indtast type");
                        String type = input.nextLine();
                        System.out.println("Indtast vægt");
                        int itemWeight = input.nextInt();
                        input.nextLine();
                        System.out.println("Indtast beskrivelse");
                        String itemDescription = input.nextLine();
                        System.out.println("Indtast effekt");
                        int itemEffect = input.nextInt();
                        Item item = new Item(id, name, type, itemWeight, itemDescription, itemEffect);
                        String updated = repository.updateItem(item);
                        System.out.println(updated);
                    }
                    case 4 -> {
                        System.out.println("You chose 4");
                        System.out.println("Indtast id for 'Item', der skal slettes");
                        int id = input.nextInt();
                        String deleted = repository.deleteItem(id);
                        System.out.println(deleted);
                    }
                    case 5 -> {
                        System.out.println("You chose 5");
                        System.out.println("Indtast brugerid");
                        int id = input.nextInt();
                        int inventoryId = repository.createNewInventory(id);
                        inventory = inventory.initiateInventory(inventoryId);
                    }
                    case 6 -> {
                        System.out.println("You chose 6");
                        System.out.println("Indtast id for 'Inventory', der skal vises");
                        int id = input.nextInt();
                        if (inventory.initiateInventory(id) == null) {
                            System.out.println("No inventory found");
                        }
                        else {
                            inventory = inventory.initiateInventory(id);

                            for (Item item : inventory.getItems()) {
                                System.out.println(item);
                            }
                            System.out.println("\nslots used: " + inventory.getSlotCurrent() +
                                    " out of " + inventory.getSlotCurrentMax() +
                                    "\n \nCurrent weight is: " + inventory.getWeightCurrent() +
                                    " out of " + inventory.getWeightMax() + "\n");
                        }
                    }
                    case 7 -> {
                        System.out.println("You chose 7");
                        System.out.println("Indtast id for 'item', der skal tilføjes");
                        int itemId = input.nextInt();
                        String a2i = inventory.addItemToInventory(inventory.getId(), itemId);
                        System.out.println(a2i);
                        //itemsMain.add(repository.getOneItem(itemId));
                        System.out.println("Følgende er nu i inventory:\n");
                        for (Item item : inventory.getItems()) {
                            System.out.println(item.getName());
                        }
                        System.out.println("\nslots used: " + inventory.getSlotCurrent() +
                                " out of " + inventory.getSlotCurrentMax() +
                                "\n \nCurrent weight is: " + inventory.getWeightCurrent() +
                                " out of " + inventory.getWeightMax() + "\n");
                    }
                    case 8 -> {
                        System.out.println("You chose 8");
                        System.out.println("Indtast id for 'item', der skal fjernes");
                        int itemId = input.nextInt();
                        String removeFromInventory = inventory.removeItemFromInventory(inventory.getId(), itemId);
                        System.out.println(removeFromInventory);
//                        for (int i = 0; i < itemsMain.size(); i++) {
//                            Item obj = itemsMain.get(i);
//                            if (obj.getId() == itemId) {
//                                itemsMain.remove(i);
//                                for (Item item : inventory.getItems()) {
//                                    System.out.println(item.getName());
//                                }
//                          }
//                        }
                    }
                    case 9 -> {
                        for (Item item : inventory.getItems()) {
                            System.out.println(item);
                        }
                        System.out.println(inventory.getSlotCurrent() + " slots er brugt");
                        System.out.println(inventory.getWeightCurrent() + " er vægten oppe på");
                    }
                    case 10 -> {
                        System.out.println("You chose 10\nUpdating inventory size...");
                        String slotSize = inventory.incrementMaxSlot(inventory.getSlotCurrentMax(), inventory.getSlotMax());
                        System.out.println(slotSize);
                    }
                    case 11 -> {
                        System.out.println("You chose 11");
                        String exported = inventory.exportInventory();
                        System.out.println(exported);
                    }
                    case 12 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> {
                        System.out.println("Invalid choice try again");
                    }
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid choice try again ----" + ime);
                input.nextLine();
            }
        }
    }
}