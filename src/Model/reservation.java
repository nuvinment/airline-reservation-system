package Model;

public class reservation {
    // Changed from referenceCode (String) to reservationId (int)
    private int reservationId; // Unique identifier for the reservation
    private int customerId; // ID of the customer who made the reservation
    private String passengerName; // Name of the passenger
    private String fromLocation; // Departure location
    private String toLocation; // Destination location
    private String departureDate; // Date of departure
    private String flightClass; // Class of the flight (e.g., Economy, Business)
    private String flightId; // ID of the flight
    private double totalPrice; // Total price of the reservation

    // Getter and setter for reservationId
    public int getReservationId() {
        return reservationId;
    }
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    // Getter and setter for customerId
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // Getter and setter for passengerName
    public String getPassengerName() {
        return passengerName;
    }
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    // Getter and setter for fromLocation
    public String getFromLocation() {
        return fromLocation;
    }
    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    // Getter and setter for toLocation
    public String getToLocation() {
        return toLocation;
    }
    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    // Getter and setter for departureDate
    public String getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    // Getter and setter for flightClass
    public String getFlightClass() {
        return flightClass;
    }
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    // Getter and setter for flightId
    public String getFlightId() {
        return flightId;
    }
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    // Getter and setter for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
