package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String passengerName; // Full name of the passenger
    private String fromLocation;   // Departure location
    private String toLocation;     // Arrival location
    private String flightClass;     // Class of the flight (e.g., Economy, Business)
    private String flightName;      // Name of the flight
    private String flightDate;      // Flight date as a String
    private LocalDate rawFlightDate; // Optional: Keep LocalDate if needed
    private String departureTime;   // Departure time as a String
    private String flightId;        // Unique identifier for the flight
    private int passengerCount;     // Number of passengers
    private int customerId;         // Unique identifier for the customer
    private double totalPrice;      // Total price for the booking

    // Date formatter for consistent date formatting
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Time formatter for consistent time formatting
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("HH:mm");

    // Default constructor
    public Booking() {
    }

    // Getters and Setters
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public LocalDate getRawFlightDate() {
        return rawFlightDate;
    }

    public void setFlightDate(LocalDate date) {
        this.rawFlightDate = date;
        this.flightDate = date.format(DATE_FORMATTER);
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String time) {
        this.departureTime = time;
    }

    public void setDepartureTime(LocalTime time) {
        this.departureTime = time.format(TIME_FORMATTER);
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Booking setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
