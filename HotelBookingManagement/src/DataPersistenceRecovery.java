import java.io.*;
import java.util.*;

class BookingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookingId;
    private String guestName;
    private String roomType;
    private int nights;

    public BookingRecord(String bookingId, String guestName, String roomType, int nights) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "BookingRecord [ID=" + bookingId +
                ", Guest=" + guestName +
                ", RoomType=" + roomType +
                ", Nights=" + nights + "]";
    }
}

class BookingSystem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, BookingRecord> confirmedBookings = new LinkedHashMap<>();
    private Map<String, Integer> roomInventory = new HashMap<>();

    public BookingSystem() {
        // Initialize inventory
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }
    public void confirmBooking(BookingRecord record) {
        int available = roomInventory.getOrDefault(record.getRoomType(), 0);
        if (available < 1) {
            System.out.println("Booking failed: No rooms available for type " + record.getRoomType());
            return;
        }
        confirmedBookings.put(record.getBookingId(), record);
        roomInventory.put(record.getRoomType(), available - 1);
        System.out.println("Booking confirmed: " + record);
    }
    public void displayConfirmedBookings() {
        System.out.println("\n=== Confirmed Bookings ===");
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        for (BookingRecord record : confirmedBookings.values()) {
            System.out.println(record);
        }
    }
    public void displayInventory() {
        System.out.println("\n=== Room Inventory ===");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("\nSystem state saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public static BookingSystem loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No persistence file found. Starting with new system.");
            return new BookingSystem();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            BookingSystem system = (BookingSystem) in.readObject();
            System.out.println("System state loaded from file: " + filename);
            return system;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            System.out.println("Starting with new system.");
            return new BookingSystem();
        }
    }
}

public class DataPersistenceRecovery {
    private static final String PERSISTENCE_FILE = "booking_system.dat";

    public static void main(String[] args) {

        BookingSystem system = BookingSystem.loadFromFile(PERSISTENCE_FILE);

        BookingRecord b1 = new BookingRecord("B001", "Alice", "Deluxe", 2);
        BookingRecord b2 = new BookingRecord("B002", "Bob", "Suite", 3);

        system.confirmBooking(b1);
        system.confirmBooking(b2);

        system.displayConfirmedBookings();
        system.displayInventory();

        system.saveToFile(PERSISTENCE_FILE);
    }
}