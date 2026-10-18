package DAO;

import Model.employeeLoginModel;
import java.sql.*;

public class employeeLoginDAO {

    // Database credentials (you can adjust these)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    public boolean authenticateEmployee(employeeLoginModel employee) {
        String sql = "SELECT * FROM employee WHERE eUsername = ? AND ePassword = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getUsername());
            stmt.setString(2, employee.getPassword());

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if match found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
