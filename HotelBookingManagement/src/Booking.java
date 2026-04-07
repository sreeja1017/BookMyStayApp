import java.util.LinkedList;
import java.util.Queue;

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

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added to queue: " + reservation);
    }

    public Reservation peekNextRequest() {
        return queue.peek();
    }

    public Reservation processNextRequest() {
        return queue.poll();
    }

    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.println("\nCurrent Booking Request Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}
public class Booking{
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating incoming booking requests
        Reservation r1 = new Reservation("Alice", "Deluxe", 2);
        Reservation r2 = new Reservation("Bob", "Suite", 3);
        Reservation r3 = new Reservation("Charlie", "Standard", 1);

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        bookingQueue.displayQueue();

        System.out.println("\nNext request to process: " +
                bookingQueue.peekNextRequest());

        System.out.println("\nProcessing request: " +
                bookingQueue.processNextRequest());

        bookingQueue.displayQueue();
    }
}