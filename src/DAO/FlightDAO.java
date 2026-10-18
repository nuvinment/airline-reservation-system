package DAO;

import Model.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class FlightDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    public boolean addFlight(Flight flight) {
        String sql = "INSERT INTO flight (flightId, flightName, airlineName, economicPrice, " +
                     "businessPrice, fromLocation, toLocation, departureDate, returnDate, " +
                     "departureTime, arrivalTime, fStatus, totalSeats, availableSeats, " +
                     "flightDuration, aircraftType, gateNumber, terminal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flight.getFlightId());
            stmt.setString(2, flight.getFlightName());
            stmt.setString(3, flight.getAirlineName());
            stmt.setDouble(4, flight.getEconomicPrice());
            stmt.setDouble(5, flight.getBusinessPrice());
            stmt.setString(6, flight.getFromLocation());
            stmt.setString(7, flight.getToLocation());
            stmt.setDate(8, Date.valueOf(flight.getDepartureDate()));
            stmt.setDate(9, Date.valueOf(flight.getReturnDate()));
            stmt.setTime(10, Time.valueOf(flight.getDepartureTime()));
            stmt.setTime(11, Time.valueOf(flight.getArrivalTime()));
            stmt.setString(12, flight.getFStatus());
            stmt.setInt(13, flight.getTotalSeats());
            stmt.setInt(14, flight.getAvailableSeats());
            stmt.setString(15, flight.getFlightDuration());
            stmt.setString(16, flight.getAircraftType());
            stmt.setString(17, flight.getGateNumber());
            stmt.setString(18, flight.getTerminal());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFlight(Flight flight) {
        String sql = "UPDATE flight SET flightName = ?, airlineName = ?, economicPrice = ?, " +
                     "businessPrice = ?, fromLocation = ?, toLocation = ?, departureDate = ?, " +
                     "returnDate = ?, departureTime = ?, arrivalTime = ?, fStatus = ?, " +
                     "totalSeats = ?, availableSeats = ?, flightDuration = ?, aircraftType = ?, " +
                     "gateNumber = ?, terminal = ? WHERE flightId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flight.getFlightName());
            stmt.setString(2, flight.getAirlineName());
            stmt.setDouble(3, flight.getEconomicPrice());
            stmt.setDouble(4, flight.getBusinessPrice());
            stmt.setString(5, flight.getFromLocation());
            stmt.setString(6, flight.getToLocation());
            stmt.setDate(7, Date.valueOf(flight.getDepartureDate()));
            stmt.setDate(8, Date.valueOf(flight.getReturnDate()));
            stmt.setTime(9, Time.valueOf(flight.getDepartureTime()));
            stmt.setTime(10, Time.valueOf(flight.getArrivalTime()));
            stmt.setString(11, flight.getFStatus());
            stmt.setInt(12, flight.getTotalSeats());
            stmt.setInt(13, flight.getAvailableSeats());
            stmt.setString(14, flight.getFlightDuration());
            stmt.setString(15, flight.getAircraftType());
            stmt.setString(16, flight.getGateNumber());
            stmt.setString(17, flight.getTerminal());
            stmt.setString(18, flight.getFlightId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFlight(String flightId) {
        String sql = "DELETE FROM flight WHERE flightId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flight";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public List<Flight> searchFlights(String keyword) {
        List<Flight> results = new ArrayList<>();
        String sql = "SELECT * FROM flight WHERE flightId LIKE ? OR flightName LIKE ? OR fromLocation LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToFlight(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return results;
    }

    public Flight getFlightById(String id) {
        String sql = "SELECT * FROM flight WHERE flightId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFlight(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Extracted reusable mapping function
    private Flight mapResultSetToFlight(ResultSet rs) throws SQLException {
        LocalDate departureDate = rs.getDate("departureDate").toLocalDate();
        LocalDate returnDate = rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null;
        LocalTime departureTime = rs.getTime("departureTime").toLocalTime();
        LocalTime arrivalTime = rs.getTime("arrivalTime").toLocalTime();

        return new Flight(
            rs.getString("flightId"),
            rs.getString("flightName"),
            rs.getString("airlineName"),
            rs.getDouble("economicPrice"),
            rs.getDouble("businessPrice"),
            rs.getString("fromLocation"),
            rs.getString("toLocation"),
            departureDate,
            returnDate,
            departureTime,
            arrivalTime,
            rs.getString("fStatus"),
            rs.getInt("totalSeats"),
            rs.getInt("availableSeats"),
            rs.getString("flightDuration"),
            rs.getString("aircraftType"),
            rs.getString("gateNumber"),
            rs.getString("terminal")
        );
    }
}
