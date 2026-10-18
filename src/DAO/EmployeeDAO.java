/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "tbqqqRrYVzvhjrU3fYN^";

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (employeeId, eName, eEmail, ePhoneNumber, eAddress, eUsername, ePassword) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employee.getEmployeeId());
            stmt.setString(2, employee.geteName());
            stmt.setString(3, employee.geteEmail());
            stmt.setString(4, employee.getePhoneNumber());
            stmt.setString(5, employee.geteAddress());
            stmt.setString(6, employee.geteUsername());
            stmt.setString(7, employee.getePassword());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET eName = ?, eEmail = ?, ePhoneNumber = ?, eAddress = ?, eUsername = ?, ePassword = ? WHERE employeeId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.geteName());
            stmt.setString(2, employee.geteEmail());
            stmt.setString(3, employee.getePhoneNumber());
            stmt.setString(4, employee.geteAddress());
            stmt.setString(5, employee.geteUsername());
            stmt.setString(6, employee.getePassword());
            stmt.setInt(7, employee.getEmployeeId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employee WHERE employeeId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("employeeId"),
                    rs.getString("eName"),
                    rs.getString("eEmail"),
                    rs.getString("ePhoneNumber"),
                    rs.getString("eAddress"),
                    rs.getString("eUsername"),
                    rs.getString("ePassword")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Employee> searchEmployees(String searchType, String searchValue) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE ";

        switch (searchType.toLowerCase()) {
            case "id":
                sql += "employeeId = ?";
                break;
            case "username":
                sql += "eUsername LIKE ?";
                searchValue = "%" + searchValue + "%";
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + searchType);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (searchType.equalsIgnoreCase("id")) {
                stmt.setInt(1, Integer.parseInt(searchValue));
            } else {
                stmt.setString(1, searchValue);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                        rs.getInt("employeeId"),
                        rs.getString("eName"),
                        rs.getString("eEmail"),
                        rs.getString("ePhoneNumber"),
                        rs.getString("eAddress"),
                        rs.getString("eUsername"),
                        rs.getString("ePassword")
                    );
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to be handled by the controller
        }
        return employees;
    }
}

