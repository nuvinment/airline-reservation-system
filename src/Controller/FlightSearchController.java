package Controller;

import View.flightSearch;
import DAO.flightSearchDAO;
import Model.Flight; // Assuming Model.Flight is the correct class for a flight object
import Model.flightSearchModel;
import View.customerAuthentication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class FlightSearchController {

    private final flightSearch view;
    private flightSearchDAO dao;

    public FlightSearchController(flightSearch view, flightSearchDAO dao) {
        this.view = view;
        this.dao = dao;

        this.view.addSearchListener(new SearchListener());
        this.view.addBookFlightListener(new BookListener()); // Add listener for the book flight button
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String from = view.getFromInput().trim();
                String to = view.getToInput().trim();
                Date departureDate = view.getDepartureDate();
                Date returnDate = view.getReturnDate();
                String seatClass = view.getSeatClass();
                int passengerCount = view.getPassengerCount();
                boolean isRoundTrip = view.isRoundTrip();

                // Validate required fields
                if (from.isEmpty() || to.isEmpty() || departureDate == null || seatClass.isEmpty() || passengerCount <= 0) {
                    JOptionPane.showMessageDialog(view, "All required fields must be filled!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If round trip selected, return date is required
                if (isRoundTrip && returnDate == null) {
                    JOptionPane.showMessageDialog(view, "Please select a return date for round trip.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Format dates
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String departureDateStr = sdf.format(departureDate);
                String returnDateStr = (isRoundTrip && returnDate != null) ? sdf.format(returnDate) : null;

                // Search flights using DAO
                List<Model.flightSearchModel> results = dao.searchFlights(
                    from, to, departureDateStr, returnDateStr, isRoundTrip, seatClass, passengerCount
                );

                // Set results in table
                view.setFlightTableData(results);

                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No matching flights found.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Passenger count must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Search error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class BookListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // 1. Get selected flight ID from table (returns int)
            int flightId = view.getFlightIdShow(); 

            // 2. Retrieve full Flight object from DAO
            Flight selectedFlight = dao.getFlightById(flightId);
            if (selectedFlight == null) {
                JOptionPane.showMessageDialog(view, 
                    "Flight not found with ID: " + flightId, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Get booking details from flightSearch view
            String flightClass = view.getSeatClass();
            int passengerCount = view.getPassengerCount();
            String tripType = view.isRoundTrip() ? "Round Trip" : "One Way";

            // 4. Open customer authentication view with flight details
            customerAuthentication authView = new customerAuthentication(
                selectedFlight, flightClass, passengerCount, tripType);

            // 5. Initialize controller (passes view + booking details)
            new customerAuthenticationController(
                authView, 
                selectedFlight, 
                flightClass, 
                passengerCount, 
                tripType);

            authView.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error booking flight: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

}
