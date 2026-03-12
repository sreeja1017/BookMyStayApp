
abstract class Room {

    String roomType;
    int beds;
    double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }
}


class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 80.0);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 120.0);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 250.0);
    }
}


public class Booking {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Room Availability ");
        System.out.println(" Version 2.0 ");
        System.out.println("=================================");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        System.out.println("\nSingle Room:");
        single.displayDetails();
        System.out.println("Available : " + singleAvailable);

        System.out.println("\nDouble Room:");
        doubleRoom.displayDetails();
        System.out.println("Available : " + doubleAvailable);

        System.out.println("\nSuite Room:");
        suite.displayDetails();
        System.out.println("Available : " + suiteAvailable);
    }
}

