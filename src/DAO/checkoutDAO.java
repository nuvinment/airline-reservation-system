package DAO;

import Model.CheckoutFlight;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class checkoutDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    public static CheckoutFlight getFlightDetailsById(String flightId) {
        CheckoutFlight flight = null;
        String sql = "SELECT * FROM flight WHERE flightId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                flight = new CheckoutFlight();
                flight.setFlightId(rs.getString("flightId"));
                flight.setFlightName(rs.getString("flightName"));
                flight.setAirlineName(rs.getString("airlineName"));
                flight.setEconomicPrice(rs.getDouble("economicPrice"));
                flight.setBusinessPrice(rs.getDouble("businessPrice"));
                flight.setFromLocation(rs.getString("fromLocation"));
                flight.setToLocation(rs.getString("toLocation"));
                flight.setDepartureDate(rs.getDate("departureDate").toLocalDate());
                flight.setReturnDate(rs.getDate("returnDate").toLocalDate());
                flight.setDepartureTime(rs.getTime("departureTime").toLocalTime());
                flight.setArrivalTime(rs.getTime("arrivalTime").toLocalTime());
                flight.setfStatus(rs.getString("fStatus"));
                flight.setTotalSeats(rs.getInt("totalSeats"));
                flight.setAvailableSeats(rs.getInt("availableSeats"));
                flight.setFlightDuration(rs.getString("flightDuration"));
                flight.setAircraftType(rs.getString("aircraftType"));
                flight.setGateNumber(rs.getString("gateNumber"));
                flight.setTerminal(rs.getString("terminal"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching flight: " + e.getMessage());
        }
        return flight;
    }
}