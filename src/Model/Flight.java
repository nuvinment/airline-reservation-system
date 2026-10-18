package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    private String flightId;
    private String flightName;
    private String airlineName;
    private double economicPrice;
    private double businessPrice;
    private String fromLocation;
    private String toLocation;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String fStatus;
    private int totalSeats;
    private int availableSeats;
    private String flightDuration;
    private String aircraftType;
    private String gateNumber;
    private String terminal;

    // ✅ Correct Constructor
    public Flight(String flightId, String flightName, String airlineName, double economicPrice, double businessPrice,
                  String fromLocation, String toLocation, LocalDate departureDate, LocalDate returnDate,
                  LocalTime departureTime, LocalTime arrivalTime, String fStatus, int totalSeats, int availableSeats,
                  String flightDuration, String aircraftType, String gateNumber, String terminal) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.airlineName = airlineName;
        this.economicPrice = economicPrice;
        this.businessPrice = businessPrice;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fStatus = fStatus;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.flightDuration = flightDuration;
        this.aircraftType = aircraftType;
        this.gateNumber = gateNumber;
        this.terminal = terminal;
    }

    // Getters
    public String getFlightId() { return flightId; }
    public String getFlightName() { return flightName; }
    public String getAirlineName() { return airlineName; }
    public double getEconomicPrice() { return economicPrice; }
    public double getBusinessPrice() { return businessPrice; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public LocalDate getDepartureDate() { return departureDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public LocalTime getDepartureTime() { return departureTime; }
    public LocalTime getArrivalTime() { return arrivalTime; }
    public String getFStatus() { return fStatus; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public String getFlightDuration() { return flightDuration; }
    public String getAircraftType() { return aircraftType; }
    public String getGateNumber() { return gateNumber; }
    public String getTerminal() { return terminal; }
}
