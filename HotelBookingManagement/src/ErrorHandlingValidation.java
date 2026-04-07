import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
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
        return "Reservation [Guest=" + guestName +
                ", RoomType=" + roomType +
                ", Nights=" + nights + "]";
    }
}

class BookingValidator {

    private static final Set<String> VALID_ROOM_TYPES = Set.of("Standard", "Deluxe", "Suite");

    public static void validateReservation(Reservation r) throws InvalidBookingException {
        if (r.getGuestName() == null || r.getGuestName().isBlank()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!VALID_ROOM_TYPES.contains(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }
        if (r.getNights() <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0.");
        }
    }
}

class BookingSystem {

    private List<Reservation> confirmedBookings = new ArrayList<>();
    private Map<String, Integer> roomInventory = new HashMap<>();

    public BookingSystem() {
        // Initialize inventory for simplicity
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

    public void bookReservation(Reservation r) {
        try {

            BookingValidator.validateReservation(r);

            int available = roomInventory.getOrDefault(r.getRoomType(), 0);
            if (available < 1) {
                throw new InvalidBookingException("No rooms available for type: " + r.getRoomType());
            }

            confirmedBookings.add(r);
            roomInventory.put(r.getRoomType(), available - 1);

            System.out.println("Booking confirmed: " + r);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public void displayConfirmedBookings() {
        System.out.println("\n=== Confirmed Bookings ===");
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        for (Reservation r : confirmedBookings) {
            System.out.println(r);
        }
    }

    public void displayInventory() {
        System.out.println("\n=== Remaining Room Inventory ===");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

public class ErrorHandlingValidation {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        Reservation r1 = new Reservation("Alice", "Deluxe", 2);   // valid
        Reservation r2 = new Reservation("Bob", "Penthouse", 3);  // invalid room type
        Reservation r3 = new Reservation("Charlie", "Suite", 0);  // invalid nights
        Reservation r4 = new Reservation("", "Standard", 1);      // empty guest name
        Reservation r5 = new Reservation("Diana", "Suite", 2);    // should fail if Suite inventory exhausted

        system.bookReservation(r1);
        system.bookReservation(r2);
        system.bookReservation(r3);
        system.bookReservation(r4);
        system.bookReservation(r5);

        system.displayConfirmedBookings();
        system.displayInventory();
    }
}