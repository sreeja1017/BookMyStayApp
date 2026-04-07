import java.util.*;

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

class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
        System.out.println("Booking confirmed and added to history: " + reservation);
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }


    public void displayAllBookings() {
        System.out.println("\n=== Booking History ===");
        List<Reservation> all = history.getAllReservations();
        if (all.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Reservation r : all) {
            System.out.println(r);
        }
    }

    public void generateSummaryReport() {
        System.out.println("\n=== Booking Summary Report ===");
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : history.getAllReservations()) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        if (summary.isEmpty()) {
            System.out.println("No bookings to summarize.");
            return;
        }

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Bookings: " + entry.getValue());
        }
    }
}

public class BookingHistoryReport {
    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService(bookingHistory);

        Reservation r1 = new Reservation("Alice", "Deluxe", 2);
        Reservation r2 = new Reservation("Bob", "Suite", 3);
        Reservation r3 = new Reservation("Charlie", "Standard", 1);
        Reservation r4 = new Reservation("Diana", "Deluxe", 4);

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);
        bookingHistory.addReservation(r4);

        reportService.displayAllBookings();

        reportService.generateSummaryReport();
    }
}