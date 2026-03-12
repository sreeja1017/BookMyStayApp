import java.util.HashMap;
import java.util.Map;

/* Inventory Manager */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        // Initialize availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    /* Retrieve availability */
    public int getAvailability(String roomType) {

        return inventory.getOrDefault(roomType, 0);
    }

    /* Update availability */
    public void updateAvailability(String roomType, int count) {

        inventory.put(roomType, count);
    }

    /* Display inventory */
    public void displayInventory() {

        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

/* Application Entry */
public class Booking {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Inventory Management ");
        System.out.println(" Version 3.0 ");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating availability for Single Room...");

        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();
    }
}
