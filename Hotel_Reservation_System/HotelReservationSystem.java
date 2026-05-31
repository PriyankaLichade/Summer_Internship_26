import java.util.*;
import java.io.*;

class Room {

    int roomNumber;
    String category;
    double price;
    boolean isBooked;

    Room(int roomNumber, String category, double price) {

        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isBooked = false;
    }

    void displayRoom() {

        System.out.println(
                "Room No: " + roomNumber +
                " | Category: " + category +
                " | Price: ₹" + price +
                " | Status: " +
                (isBooked ? "Booked" : "Available"));
    }
}


class Reservation {

    String customerName;
    int roomNumber;
    String category;
    double amountPaid;

    Reservation(String customerName,
                int roomNumber,
                String category,
                double amountPaid) {

        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amountPaid = amountPaid;
    }

    void displayReservation() {

        System.out.println(
                "Customer: " + customerName +
                " | Room No: " + roomNumber +
                " | Category: " + category +
                " | Paid: ₹" + amountPaid);
    }
}

class Payment {

    static boolean makePayment(double amount) {

        System.out.println(
                "Payment of ₹" + amount +
                " successful.");

        return true;
    }
}

class Hotel {

    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Reservation> reservations =
            new ArrayList<>();

    Hotel() {

        // Standard Rooms
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));

        // Deluxe Rooms
        rooms.add(new Room(201, "Deluxe", 4000));
        rooms.add(new Room(202, "Deluxe", 4000));

        // Suite Rooms
        rooms.add(new Room(301, "Suite", 7000));
    }

    void searchRooms() {

        System.out.println(
                "\n===== AVAILABLE ROOMS =====");

        for (Room room : rooms) {

            if (!room.isBooked) {
                room.displayRoom();
            }
        }
    }

    void bookRoom(Scanner sc) {

        System.out.print("Enter Customer Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room room : rooms) {

            if (room.roomNumber == roomNo) {

                if (room.isBooked) {

                    System.out.println(
                            "Room already booked.");
                    return;
                }

                boolean paymentStatus =
                        Payment.makePayment(room.price);

                if (paymentStatus) {

                    room.isBooked = true;

                    Reservation reservation =
                            new Reservation(
                                    name,
                                    room.roomNumber,
                                    room.category,
                                    room.price);

                    reservations.add(reservation);

                    saveBooking(reservation);

                    System.out.println(
                            "Room booked successfully!");
                }

                return;
            }
        }

        System.out.println("Room not found.");
    }

    void cancelReservation(Scanner sc) {

        System.out.print(
                "Enter Room Number to Cancel: ");

        int roomNo = sc.nextInt();

        Iterator<Reservation> iterator =
                reservations.iterator();

        while (iterator.hasNext()) {

            Reservation reservation =
                    iterator.next();

            if (reservation.roomNumber == roomNo) {

                iterator.remove();

                for (Room room : rooms) {

                    if (room.roomNumber == roomNo) {

                        room.isBooked = false;
                    }
                }

                System.out.println(
                        "Reservation cancelled.");
                return;
            }
        }

        System.out.println(
                "Reservation not found.");
    }

    // View Reservations
    void viewReservations() {

        System.out.println(
                "\n===== BOOKING DETAILS =====");

        if (reservations.isEmpty()) {

            System.out.println(
                    "No reservations found.");
            return;
        }

        for (Reservation reservation :
                reservations) {

            reservation.displayReservation();
        }
    }

    // Save Bookings to File
    void saveBooking(Reservation reservation) {

        try {

            FileWriter writer =
                    new FileWriter(
                            "bookings.txt", true);

            writer.write(
                    reservation.customerName + "," +
                    reservation.roomNumber + "," +
                    reservation.category + "," +
                    reservation.amountPaid + "\n");

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Error saving booking.");
        }
    }
}

public class HotelReservationSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Hotel hotel = new Hotel();

        int choice;

        do {

            System.out.println(
                    "\n===== HOTEL RESERVATION SYSTEM =====");

            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    hotel.searchRooms();
                    break;

                case 2:

                    hotel.bookRoom(sc);
                    break;

                case 3:

                    hotel.cancelReservation(sc);
                    break;

                case 4:

                    hotel.viewReservations();
                    break;

                case 5:

                    System.out.println(
                            "Thank You!");
                    break;

                default:

                    System.out.println(
                            "Invalid Choice");
            }

        } while (choice != 5);

        sc.close();
    }
}