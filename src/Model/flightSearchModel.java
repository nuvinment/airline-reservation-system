package Model;

public class flightSearchModel {
    private String flightId;
    private String flightName;
    private String airlineName;
    private double economicPrice;
    private double businessPrice;
    private String fromLocation;
    private String toLocation;
    private String departureDate;
    private String returnDate;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;

    // Constructor
    public flightSearchModel(String flightId, String flightName, String airlineName, 
                             double economicPrice, double businessPrice, 
                             String fromLocation, String toLocation, 
                             String departureDate, String returnDate, 
                             String departureTime, String arrivalTime, 
                             int availableSeats) {
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
        this.availableSeats = availableSeats; // Correctly assigning availableSeats
    }

    // Getters and Setters
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public double getEconomicPrice() {
        return economicPrice;
    }

    public void setEconomicPrice(double economicPrice) {
        this.economicPrice = economicPrice;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
