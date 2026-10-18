// FileName: /CustomerDAO.java
// FileContents:
package DAO;

import Model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^"; // Use environment variables in production

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        // This line now works because Customer class has a no-argument constructor
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customerId"));
        customer.setcFirstName(rs.getString("cFirstName"));
        customer.setcLastName(rs.getString("cLastName"));
        customer.setcUsername(rs.getString("cUsername"));
        customer.setGender(rs.getString("gender"));
        customer.setNic(rs.getString("NICno")); // Using "NICno" as per database column
        customer.setcEmail(rs.getString("cEmail"));
        customer.setcPhoneNo(rs.getString("cPhoneNo"));
        customer.setcAddress(rs.getString("cAddress"));
        customer.setPassword(rs.getString("password"));
        return customer;
    }

    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (cFirstName, cLastName, cUsername, gender, NICno, cEmail, cPhoneNo, cAddress, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, customer.getcFirstName());
            stmt.setString(2, customer.getcLastName());
            stmt.setString(3, customer.getcUsername());
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getNic());
            stmt.setString(6, customer.getcEmail());
            stmt.setString(7, customer.getcPhoneNo());
            stmt.setString(8, customer.getcAddress());
            stmt.setString(9, customer.getPassword());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCustomerId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET cFirstName = ?, cLastName = ?, cUsername = ?, gender = ?, NICno = ?, " +
                     "cEmail = ?, cPhoneNo = ?, cAddress = ?, password = ? WHERE customerId = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getcFirstName());
            stmt.setString(2, customer.getcLastName());
            stmt.setString(3, customer.getcUsername());
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getNic());
            stmt.setString(6, customer.getcEmail());
            stmt.setString(7, customer.getcPhoneNo());
            stmt.setString(8, customer.getcAddress());
            stmt.setString(9, customer.getPassword());
            stmt.setInt(10, customer.getCustomerId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customer WHERE customerId = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all customers: " + e.getMessage());
        }

        return customers;
    }

    public List<Customer> searchCustomers(String searchType, String searchValue) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE ";

        switch (searchType.toLowerCase()) {
            case "id":
                sql += "customerId = ?";
                break;
            case "first name":
                sql += "cFirstName LIKE ?";
                searchValue = "%" + searchValue + "%";
                break;
            case "username":
                sql += "cUsername LIKE ?";
                searchValue = "%" + searchValue + "%";
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + searchType);
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (searchType.equalsIgnoreCase("id")) {
                stmt.setInt(1, Integer.parseInt(searchValue));
            } else {
                stmt.setString(1, searchValue);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        }

        return customers;
    }

}
