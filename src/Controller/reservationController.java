package Controller;

import DAO.reservationDAO;
import Model.reservation;
import View.booking;

import javax.swing.JOptionPane;

public class reservationController {
    private booking view;  // The booking UI
    private reservationDAO dao;

    public reservationController(booking view) {
        this.view = view;
        this.dao = new reservationDAO();
    }

    // Method to save a reservation
    public void saveReservation() {
        reservation r = new reservation();

        // Parse customerId
        int customerId;
        try {
            customerId = Integer.parseInt(view.getPassengerId());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid Passenger ID. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        r.setCustomerId(customerId);

        // Set reservation details
        r.setPassengerName(view.getPassengerName());
        r.setFromLocation(view.getFromLocation());
        r.setToLocation(view.getToLocation());
        r.setDepartureDate(view.getFlightDate());
        r.setFlightClass(view.getFlightClass());
        r.setFlightId(view.getFlightId());

        // Parse total price
        try {
            double totalPrice = Double.parseDouble(view.getTotalPrice());
            r.setTotalPrice(totalPrice);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid Total Price. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert into DB
        int generatedId = dao.insertReservationAndGetId(r);

        if (generatedId != -1) {
            JOptionPane.showMessageDialog(view, "Reservation saved successfully! Reservation ID: " + generatedId);
            view.updateBillWithReservationId(generatedId);
        } else {
            JOptionPane.showMessageDialog(view, "Failed to save reservation.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
