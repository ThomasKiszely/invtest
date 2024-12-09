import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseRepository repository = new DatabaseRepository();
        List<Item> itemsMain = new ArrayList<>();
        Inventory inventory = new Inventory(0, 0, itemsMain);
        Scanner input = new Scanner(System.in);

        System.out.println("Hello, World!");
//        inventory = inventory.initiateInventory(1);
//        System.out.println(inventory.getItems() + "\nslots used: " +  inventory.getSlotCurrent() +
//                "\nCurrent weight is: " + inventory.getWeightCurrent());
//        Inventory inv = new Inventory();
//        List<Item> items = inv.itemsList();
//        for (Item item : items) {
//            weight = item.getWeight();
//            currentWeight += weight;
//
 //        }
//        System.out.println("Your current weight is " + currentWeight);
        while (true) {
            System.out.println("Welcome to Legend of Zel... GameCraft\nChoose your poison\n1 to create item" +
                    "\n2 to see a list of items\n3 to update an item\n4 to delete an item\n5 to choose inventory and show its items" +
                    "\n6 to add an item to inventory\n7 To remove item from inventory\n8 to show items i inventory\n9 to exit" +
                    "\n10 to show slot and weight");
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
                        System.out.println("Indtast id for 'Inventory', der skal vises");
                        int id = input.nextInt();
                        inventory = inventory.initiateInventory(id);
                        for (Item item : inventory.getItems()) {
                            System.out.println(item);
                        }
                        System.out.println("\nslots used: " +  inventory.getSlotCurrent() +
                                " out of " + inventory.getSlotCurrentMax() +
                                "\n \nCurrent weight is: " + inventory.getWeightCurrent() +
                                " out of " + inventory.getWeightCurrentMax() + "\n");
                    }
                    case 6 -> {
                        System.out.println("You chose 6");
                        System.out.println("Indtast id for 'Inventory', der skal tilføjes til");
                        int invid = input.nextInt();
                        System.out.println("Indtast id for 'item', der skal tilføjes");
                        int itemId = input.nextInt();
                        String a2i = inventory.addItemToInventory(invid, itemId);
                        System.out.println(a2i);
                        //itemsMain.add(repository.getOneItem(itemId));
                        System.out.println("Følgende er nu i inventory:\n");
                        for (Item item : inventory.getItems()) {
                            System.out.println(item.getName());
                        }
                    }
                    case 7 -> {
                        System.out.println("You chose 7");
                        System.out.println("Indtast id for 'Inventory', der skal fjernes fra");
                        int invid = input.nextInt();
                        System.out.println("Indtast id for 'item', der skal fjernes");
                        int itemId = input.nextInt();
                        String removeFromInventory = inventory.removeItemFromInventory(invid, itemId);
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
                    case 8 -> {
                        for (Item item : inventory.getItems()) {
                            System.out.println(item);
                        }
                    }
                    case 9 -> {
                        System.out.println("Exiting...");
                        return;
                    }

                    case 10 -> {
                        System.out.println("You chose 10");
//                        System.out.println("Enter id for inventory");
//                        int id = input.nextInt();
                        System.out.println(inventory.getSlotCurrent());
                        System.out.println(inventory.getWeightCurrent());
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