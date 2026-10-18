package DAO;

import Model.reservationMgmtModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reservationMgmtDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASS = "tbqqqRrYVzvhjrU3fYN^";

    public boolean addReservation(reservationMgmtModel reservation) throws SQLException {
        String sql = "INSERT INTO reservation (referenceCode, customerId, `from`, `to`, departureDate, class, flightId, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, reservation);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateReservation(reservationMgmtModel reservation) throws SQLException {
        String sql = "UPDATE reservation SET customerId=?, `from`=?, `to`=?, departureDate=?, class=?, flightId=?, totalPrice=? WHERE referenceCode=?";

        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(stmt, reservation);
            stmt.setString(8, reservation.getReferenceCode());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteReservation(String referenceCode) throws SQLException {
        String sql = "DELETE FROM reservation WHERE referenceCode=?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, referenceCode);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<reservationMgmtModel> getAllReservations() throws SQLException {
        List<reservationMgmtModel> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                reservations.add(mapToReservation(rs));
            }
        }
        return reservations;
    }

    public List<reservationMgmtModel> searchReservation(String referenceCode) throws SQLException {
        List<reservationMgmtModel> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE referenceCode LIKE ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + referenceCode + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapToReservation(rs));
            }
        }
        return reservations;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private void setStatementParameters(PreparedStatement stmt, reservationMgmtModel r) throws SQLException {
        stmt.setString(1, r.getReferenceCode());
        stmt.setString(2, r.getCustomerId());
        stmt.setString(3, r.getFrom());
        stmt.setString(4, r.getTo());
        stmt.setDate(5, Date.valueOf(r.getDepartureDate()));
        stmt.setString(6, r.getClassType());
        stmt.setString(7, r.getFlightId());
        stmt.setDouble(8, r.getTotalPrice());
    }

    private reservationMgmtModel mapToReservation(ResultSet rs) throws SQLException {
        return new reservationMgmtModel(
            rs.getString("referenceCode"),
            rs.getString("customerId"),
            rs.getString("from"),
            rs.getString("to"),
            rs.getDate("departureDate").toLocalDate(),
            rs.getString("class"),
            rs.getString("flightId"),
            rs.getDouble("totalPrice")
        );
    }
}
