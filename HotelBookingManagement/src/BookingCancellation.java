import java.util.*;

class BookingRecord {
    private String bookingId; // unique identifier
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

class BookingService {

    private Map<String, BookingRecord> confirmedBookings = new LinkedHashMap<>();
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>(); // Tracks recently released booking IDs

    public BookingService() {
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

    public void cancelBooking(String bookingId) {
        if (!confirmedBookings.containsKey(bookingId)) {
            System.out.println("Cancellation failed: Booking ID " + bookingId + " does not exist or already cancelled.");
            return;
        }

        BookingRecord record = confirmedBookings.remove(bookingId);

        roomInventory.put(record.getRoomType(), roomInventory.getOrDefault(record.getRoomType(), 0) + 1);

        rollbackStack.push(record.getBookingId());

        System.out.println("Booking cancelled: " + record);
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

    public void displayRollbackStack() {
        System.out.println("\n=== Recently Released Booking IDs (Rollback Stack) ===");
        if (rollbackStack.isEmpty()) {
            System.out.println("No recent cancellations.");
            return;
        }
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}


public class BookingCancellation {
    public static void main(String[] args) {

        BookingService service = new BookingService();

        BookingRecord b1 = new BookingRecord("B001", "Alice", "Deluxe", 2);
        BookingRecord b2 = new BookingRecord("B002", "Bob", "Suite", 3);
        BookingRecord b3 = new BookingRecord("B003", "Charlie", "Standard", 1);

        service.confirmBooking(b1);
        service.confirmBooking(b2);
        service.confirmBooking(b3);

        service.displayConfirmedBookings();
        service.displayInventory();

        System.out.println("\n--- Processing Cancellations ---");
        service.cancelBooking("B002"); // valid cancellation
        service.cancelBooking("B999"); // invalid cancellation (does not exist)
        service.cancelBooking("B002"); // duplicate cancellation attempt

        service.displayConfirmedBookings();
        service.displayInventory();
        service.displayRollbackStack();
    }
}