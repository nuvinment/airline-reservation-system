package DAO;

import java.sql.*;
import Model.accessReserveModel;

public class accessReserveDAO {
    private final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private final String DB_USER = "root";
    private final String DB_PASS = "tbqqqRrYVzvhjrU3fYN^";

    public int authenticateCustomer(String username, String password) {
        int customerId = -1;

        String sql = "SELECT customerId FROM customer WHERE cUsername = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("customerId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerId;
    }
}
