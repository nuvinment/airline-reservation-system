package DAO;

import Model.Flight;
import Model.Customer;
import Model.flightSearchModel; // Import flightSearchModel
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class flightSearchDAO {
    private Connection conn;

    public flightSearchDAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/mydb";
            String user = "root";
            String password = "tbqqqRrYVzvhjrU3fYN^";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Flight getFlightById(int flightId) {
    try {
        String query = "SELECT * FROM flight WHERE flightId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, String.valueOf(flightId)); // Convert int to String
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Convert SQL Date and Time types to LocalDate and LocalTime
            java.sql.Date depDateSql = rs.getDate("departureDate");
            java.sql.Date retDateSql = rs.getDate("returnDate");
            java.sql.Time depTimeSql = rs.getTime("departureTime");
            java.sql.Time arrTimeSql = rs.getTime("arrivalTime");

            // Safely convert nullable dates/times
            java.time.LocalDate departureDate = (depDateSql != null) ? depDateSql.toLocalDate() : null;
            java.time.LocalDate returnDate = (retDateSql != null) ? retDateSql.toLocalDate() : null;
            java.time.LocalTime departureTime = (depTimeSql != null) ? depTimeSql.toLocalTime() : null;
            java.time.LocalTime arrivalTime = (arrTimeSql != null) ? arrTimeSql.toLocalTime() : null;

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
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public List<flightSearchModel> searchFlights(String from, String to, String departureDateStr, String returnDateStr, boolean roundTrip, String seatClass, int passengerCount) {
        List<flightSearchModel> flights = new ArrayList<>();
        try {
            // Build query with basic filtering by fromLocation and toLocation
            String sql = "SELECT * FROM flight WHERE fromLocation LIKE ? AND toLocation LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + from + "%");
            ps.setString(2, "%" + to + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Format dates and times as strings for flightSearchModel
                String depDate = rs.getDate("departureDate") != null ? rs.getDate("departureDate").toString() : null;
                String retDate = rs.getDate("returnDate") != null ? rs.getDate("returnDate").toString() : null;
                String depTime = rs.getTime("departureTime") != null ? rs.getTime("departureTime").toString() : null;
                String arrTime = rs.getTime("arrivalTime") != null ? rs.getTime("arrivalTime").toString() : null;

                flightSearchModel fsm = new flightSearchModel(
                    rs.getString("flightId"),
                    rs.getString("flightName"),
                    rs.getString("airlineName"),
                    rs.getDouble("economicPrice"),
                    rs.getDouble("businessPrice"),
                    rs.getString("fromLocation"),
                    rs.getString("toLocation"),
                    depDate,
                    retDate,
                    depTime,
                    arrTime,
                    rs.getInt("availableSeats")
                );
                flights.add(fsm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }


    public Customer getCustomerByUsernameAndPassword(String username, String password) {
        try {
            String sql = "SELECT * FROM customer WHERE cUsername = ? AND Password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("customerId"),
                    rs.getString("cFirstName"),
                    rs.getString("cLastName"),
                    rs.getString("cUsername")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
