package Model;

import java.time.LocalDate;

public class reservationMgmtModel {
    private String referenceCode;
    private String customerId;
    private String from;
    private String to;
    private LocalDate departureDate;
    private String classType;
    private String flightId;
    private double totalPrice;

    public reservationMgmtModel(String referenceCode, String customerId, String from, 
                              String to, LocalDate departureDate, String classType, 
                              String flightId, double totalPrice) {
        this.referenceCode = referenceCode;
        this.customerId = customerId;
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
        this.classType = classType;
        this.flightId = flightId;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getReferenceCode() { return referenceCode; }
    public void setReferenceCode(String referenceCode) { this.referenceCode = referenceCode; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    
    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) { this.departureDate = departureDate; }
    
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
