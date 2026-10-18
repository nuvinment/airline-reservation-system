package Controller;

import DAO.AuthCustomerDAO;
import DAO.checkoutDAO;
import Model.AuthCustomer;
import Model.CheckoutFlight; 
import Model.Booking;
import Model.Flight;
import View.booking;
import View.customerAuthentication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;

public class customerAuthenticationController {
    private final customerAuthentication view;
    private final Flight selectedFlight;
    private final String flightClass;
    private final int passengerCount;
    private final String tripType;

    public customerAuthenticationController(customerAuthentication view, 
                                         Flight selectedFlight,
                                         String flightClass,
                                         int passengerCount,
                                         String tripType) {
        this.view = view;
        this.selectedFlight = selectedFlight;
        this.flightClass = flightClass;
        this.passengerCount = passengerCount;
        this.tripType = tripType;

        setupListeners();
    }

    private void setupListeners() {
        view.addLoginListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent e) {
        try {
            String username = view.getUsername();
            String password = view.getPassword();

            AuthCustomer customer = AuthCustomerDAO.getCustomerByLogin(username, password);
            if (customer == null) {
                view.showError("Invalid username or password");
                return;
            }

            CheckoutFlight flightDetails = checkoutDAO.getFlightDetailsById(
                selectedFlight.getFlightId()
            );

            if (flightDetails == null) {
                view.showError("Flight details not found");
                return;
            }

            // Create booking with all necessary details
            Booking booking = createBooking(customer, flightDetails);
            
            // Show booking confirmation
            new booking(booking).setVisible(true);
            view.dispose();
            
        } catch (Exception ex) {
            view.showError("Error processing booking: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Booking createBooking(AuthCustomer customer, CheckoutFlight flightDetails) {
        Booking booking = new Booking();
        
        // Set passenger details
        booking.setPassengerName(customer.getFirstName() + " " + customer.getLastName());
        booking.setCustomerId(customer.getCustomerId());
        booking.setPassengerCount(passengerCount);

        // Set flight details
        booking.setFromLocation(flightDetails.getFromLocation());
        booking.setToLocation(flightDetails.getToLocation());
        booking.setFlightClass(flightClass);
        booking.setFlightName(flightDetails.getFlightName());
        booking.setFlightId(flightDetails.getFlightId());
        
        // Handle dates and times
        booking.setFlightDate(flightDetails.getDepartureDate());
        booking.setDepartureTime(flightDetails.getDepartureTime());
        
        // Calculate and set price
        double pricePerPassenger = flightClass.equals("Business") 
            ? flightDetails.getBusinessPrice() 
            : flightDetails.getEconomicPrice();
        booking.setTotalPrice(pricePerPassenger * passengerCount);
        
        return booking;
    }
}
