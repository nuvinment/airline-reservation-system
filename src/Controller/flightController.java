package Controller;

import Model.Flight;
import DAO.FlightDAO;
import View.flightMgmt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class flightController {
    private flightMgmt view;
    private FlightDAO dao;

    public flightController(flightMgmt view, FlightDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.setAddFlightListener(new AddFlightListener());
        this.view.setUpdateFlightListener(new UpdateFlightListener());
        this.view.setDeleteFlightListener(new DeleteFlightListener());
        this.view.setClearListener(e -> view.clearFields());
        this.view.setSearchListener(new SearchFlightListener());
        this.view.setFlightTableSelectionListener(new FlightTableListener());


        
        loadFlightTable();
    }
    
     public void loadFlightTable() {
        List<Flight> flights = dao.getAllFlights(); // ✅ FIXED
        view.setFlightTableData(flights);
    }


    class AddFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get all fields with null-safe conversion
            String flightId = view.getFlightId() != null ? view.getFlightId().trim() : "";
            String flightName = view.getFlightName() != null ? view.getFlightName().trim() : "";
            String airlineName = view.getAirlineName() != null ? view.getAirlineName().trim() : "";
            String fromLocation = view.getFromLocation() != null ? view.getFromLocation().trim() : "";
            String toLocation = view.getToLocation() != null ? view.getToLocation().trim() : "";
            String departureTime = view.getDepartureTime() != null ? view.getDepartureTime().trim() : "";
            String arrivalTime = view.getArrivalTime() != null ? view.getArrivalTime().trim() : "";
            String economicPrice = view.getEconomicPrice() != null ? view.getEconomicPrice().trim() : "";
            String businessPrice = view.getBusinessPrice() != null ? view.getBusinessPrice().trim() : "";
            String totalSeats = view.getTotalSeats() != null ? view.getTotalSeats().trim() : "";
            String availableSeats = view.getAvailableSeats() != null ? view.getAvailableSeats().trim() : "";

            // Validate required fields
            if (flightId.isEmpty() || flightName.isEmpty() || airlineName.isEmpty() ||
                fromLocation.isEmpty() || toLocation.isEmpty() || view.getDepartureDate() == null ||
                departureTime.isEmpty() || arrivalTime.isEmpty() || economicPrice.isEmpty() ||
                businessPrice.isEmpty() || totalSeats.isEmpty() || availableSeats.isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "All required fields must be filled!", "Validation Error", JOptionPane.ERROR_MESSAGE);


                return;
            }

            try {
                // Parse numeric values
                double econPrice = Double.parseDouble(economicPrice);
                double busPrice = Double.parseDouble(businessPrice);
                int totSeats = Integer.parseInt(totalSeats);
                int availSeats = Integer.parseInt(availableSeats);

                if (econPrice <= 0 || busPrice <= 0) {
                    JOptionPane.showMessageDialog(null,"Prices must be positive numbers", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (totSeats <= 0 || availSeats < 0 || availSeats > totSeats) {
                    JOptionPane.showMessageDialog(null, "Invalid seat configuration", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Convert Date to LocalDate
                Date depDateRaw = view.getDepartureDate();
                LocalDate depDate = depDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                Date retDateRaw = view.getReturnDate();
                LocalDate retDate = (retDateRaw != null) ? retDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

                // Parse time
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime depTime = LocalTime.parse(departureTime, timeFormatter);
                LocalTime arrTime = LocalTime.parse(arrivalTime, timeFormatter);

                if (arrTime.isBefore(depTime)) {
                    JOptionPane.showMessageDialog(null, "Arrival time must be after departure time", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create Flight object
                Flight flight = new Flight(
                    flightId,
                    flightName,
                    airlineName,
                    econPrice,
                    busPrice,
                    fromLocation,
                    toLocation,
                    depDate,
                    retDate,
                    depTime,
                    arrTime,
                    view.getFStatus(),
                    totSeats,
                    availSeats,
                    view.getFlightDuration(),
                    view.getAircraftType(),
                    view.getGateNumber(),
                    view.getTerminal()
                );


                // Add to database
                if (dao.addFlight(flight)) {
                    JOptionPane.showMessageDialog(null, "Flight added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.clearFields();
                    loadFlightTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add flight to database", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for prices and seats", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class UpdateFlightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get all fields with null-safe conversion
            String flightId = view.getFlightId() != null ? view.getFlightId().trim() : "";
            String flightName = view.getFlightName() != null ? view.getFlightName().trim() : "";
            String airlineName = view.getAirlineName() != null ? view.getAirlineName().trim() : "";
            String fromLocation = view.getFromLocation() != null ? view.getFromLocation().trim() : "";
            String toLocation = view.getToLocation() != null ? view.getToLocation().trim() : "";
            String departureTime = view.getDepartureTime() != null ? view.getDepartureTime().trim() : "";
            String arrivalTime = view.getArrivalTime() != null ? view.getArrivalTime().trim() : "";
            String economicPrice = view.getEconomicPrice() != null ? view.getEconomicPrice().trim() : "";
            String businessPrice = view.getBusinessPrice() != null ? view.getBusinessPrice().trim() : "";
            String totalSeats = view.getTotalSeats() != null ? view.getTotalSeats().trim() : "";
            String availableSeats = view.getAvailableSeats() != null ? view.getAvailableSeats().trim() : "";
            
            if (flightId.isEmpty() || flightName.isEmpty() || airlineName.isEmpty() ||
                fromLocation.isEmpty() || toLocation.isEmpty() || view.getDepartureDate() == null ||
                departureTime.isEmpty() || arrivalTime.isEmpty() || economicPrice.isEmpty() ||
                businessPrice.isEmpty() || totalSeats.isEmpty() || availableSeats.isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "All required fields must be filled!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // Parse numeric values
                double econPrice = Double.parseDouble(economicPrice);
                double busPrice = Double.parseDouble(businessPrice);
                int totSeats = Integer.parseInt(totalSeats);
                int availSeats = Integer.parseInt(availableSeats);
                
                if (econPrice <= 0 || busPrice <= 0) {
                    JOptionPane.showMessageDialog(null,"Prices must be positive numbers", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (totSeats <= 0 || availSeats < 0 || availSeats > totSeats) {
                    JOptionPane.showMessageDialog(null, "Invalid seat configuration", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Convert Date to LocalDate
                Date depDateRaw = view.getDepartureDate();
                LocalDate depDate = depDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date retDateRaw = view.getReturnDate();
                LocalDate retDate = (retDateRaw != null) ? retDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                // Parse time
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime depTime = LocalTime.parse(departureTime, timeFormatter);
                LocalTime arrTime = LocalTime.parse(arrivalTime, timeFormatter);
                
                if (arrTime.isBefore(depTime)) {
                    JOptionPane.showMessageDialog(null, "Arrival time must be after departure time", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create Flight object
                Flight flight = new Flight(
                    flightId,
                    flightName,
                    airlineName,
                    econPrice,
                    busPrice,
                    fromLocation,
                    toLocation,
                    depDate,
                    retDate,
                    depTime,
                    arrTime,
                    view.getFStatus(),
                    totSeats,
                    availSeats,
                    view.getFlightDuration(),
                    view.getAircraftType(),
                    view.getGateNumber(),
                    view.getTerminal()
                );
                
                 // Update in database
                if (dao.updateFlight(flight)) {
                    JOptionPane.showMessageDialog(null, "Flight updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.clearFields();
                    loadFlightTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update flight in database", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                 } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for prices and seats", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
                
    }
    

    }
    class DeleteFlightListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String flightId = view.getFlightId() != null ? view.getFlightId().trim() : "";
        
        if (flightId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Flight ID must be provided!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this flight?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteFlight(flightId)) {
                JOptionPane.showMessageDialog(null, "Flight deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearFields();
                loadFlightTable();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete flight from database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
    class SearchFlightListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String keyword = view.getSearchKeyword();
        if (!keyword.isEmpty()) {
            List<Flight> results = dao.searchFlights(keyword);
            view.setFlightTableData(results);
        } else {
            loadFlightTable(); // Load all flights again if empty
        }
    }
    
}
    class FlightTableListener implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = view.getFlightTable().getSelectedRow(); // Use getter
            if (selectedRow >= 0) {
                String flightId = view.getFlightTable().getValueAt(selectedRow, 0).toString(); // Use getter
                Flight flight = dao.getFlightById(flightId);
                if (flight != null) {
                    view.setFlightFormData(flight);
                }
            }
        }
    }
}
//


    

    

    
   
}


    
    

