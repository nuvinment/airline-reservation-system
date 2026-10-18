// --- checkout.java (Model) ---
package Model;

import java.io.Serializable;

public class Checkout implements Serializable {
    private Flight flight;
    private Customer customer;
    private String selectedClass;
    private int passengerCount;
    private String tripType;

    public Checkout(Flight flight, Customer customer, String selectedClass, int passengerCount, String tripType) {
        this.flight = flight;
        this.customer = customer;
        this.selectedClass = selectedClass;
        this.passengerCount = passengerCount;
        this.tripType = tripType;
    }

    public Flight getFlight() {
        return flight;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String getTripType() {
        return tripType;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
}
