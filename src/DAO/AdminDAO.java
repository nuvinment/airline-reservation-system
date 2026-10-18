package DAO;

import Model.Admin;
import java.sql.*;

public class AdminDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb"; // Update the URL if your database is hosted elsewhere
    private static final String USER = "root";
    private static final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    // Method to establish a database connection
    private Connection getConnection() {
        Connection conn = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Method to authenticate an admin user
    public boolean authenticate(Admin admin) {
        String sql = "SELECT * FROM administrator WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if a matching admin exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
