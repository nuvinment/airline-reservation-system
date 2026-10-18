package DAO;

import Model.reservation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Import ResultSet
import java.sql.Statement; // Import Statement
import java.util.ArrayList;
import java.util.List;

public class reservationDAO {
    private final String URL = "jdbc:mysql://localhost:3306/mydb";
    private final String USER = "root";
    private final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    // Modified method to insert and return the generated reservationId
    public int insertReservationAndGetId(reservation Reservation) {
        String query = "INSERT INTO reservation (customerId, `from`, `to`, departureDate, class, flightId, totalPrice) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1; // Default to -1 to indicate failure

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, Reservation.getCustomerId());
            stmt.setString(2, Reservation.getFromLocation());
            stmt.setString(3, Reservation.getToLocation());
            stmt.setString(4, Reservation.getDepartureDate());
            stmt.setString(5, Reservation.getFlightClass());
            stmt.setString(6, Reservation.getFlightId());
            stmt.setDouble(7, Reservation.getTotalPrice());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                // Retrieve the auto-generated key
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1); // Get the first (and usually only) generated key
                    }
                }
            }
            return generatedId; // Return the generated ID or -1 if failed

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 on exception
        }
    }

    // New method to get all reservations for a specific customer ID
    public List<reservation> getReservationsByCustomerId(int customerId) {
        List<reservation> reservations = new ArrayList<>();
        String query = "SELECT referenceCode, customerId, `from`, `to`, departureDate, class, flightId, totalPrice " +
                       "FROM reservation WHERE customerId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservation res = new reservation();
                res.setReservationId(rs.getInt("referenceCode"));
                res.setCustomerId(rs.getInt("customerId"));
                res.setFromLocation(rs.getString("from"));
                res.setToLocation(rs.getString("to"));
                res.setDepartureDate(rs.getString("departureDate")); // Adjust type if necessary
                res.setFlightClass(rs.getString("class"));
                res.setFlightId(rs.getString("flightId"));
                res.setTotalPrice(rs.getDouble("totalPrice"));

                reservations.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return reservations; // Return the list of reservations
    }
}
