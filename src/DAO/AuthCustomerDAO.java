/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.AuthCustomer;
import java.sql.*;

public class AuthCustomerDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static AuthCustomer getCustomerByLogin(String username, String password) {
        AuthCustomer customer = null;
        String sql = "SELECT * FROM customer WHERE cUsername = ? AND Password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new AuthCustomer();
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setFirstName(rs.getString("cFirstName"));
                customer.setLastName(rs.getString("cLastName"));
                customer.setUsername(rs.getString("cUsername"));
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }

        return customer;
    }
}