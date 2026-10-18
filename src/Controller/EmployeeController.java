/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.employeeMgmt;
import Model.Employee;
import DAO.EmployeeDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeController {
    private employeeMgmt view;
    private EmployeeDAO dao;

    public EmployeeController(employeeMgmt view, EmployeeDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.addAddEmployeeListener(new AddEmployeeListener());
        this.view.addClearListener(new ClearListener());
        this.view.addUpdateListener(new UpdateEmployeeListener());
        this.view.addDeleteListener(new DeleteEmployeeListener());
        this.view.setSearchButtonListener(new SearchButtonListener());

        loadEmployeeData();
    }

    private void loadEmployeeData() {
        List<Employee> employees = dao.getAllEmployees();
        view.setEmployeeTableData(employees);
    }

    class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeIdStr = view.getEmployeeId();
            String name = view.getEmployeeName();
            String email = view.getEmployeeEmail();
            String phone = view.getEmployeePhone();
            String address = view.getEmployeeAddress();
            String username = view.getEmployeeUsername();
            String password1 = view.getEmployeeCreatePwd();
            String password2 = view.getEmployeeConfirmPwd();

            if (name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() ||
                address.trim().isEmpty() || username.trim().isEmpty() || password1.trim().isEmpty() ||
                password2.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields must be filled!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int employeeId;
            try {
                employeeId = Integer.parseInt(employeeIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid Employee ID. Please enter a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Employee employee = new Employee(employeeId, name, email, phone, address, username, password1);
            if (dao.addEmployee(employee)) {
                JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearFields();
                loadEmployeeData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearFields();
        }
    }

    class UpdateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeIdStr = view.getEmployeeId();
            if (employeeIdStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Employee ID is required to update an employee!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int employeeId;
            try {
                employeeId = Integer.parseInt(employeeIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid Employee ID. Please enter a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = view.getEmployeeName();
            String email = view.getEmployeeEmail();
            String phone = view.getEmployeePhone();
            String address = view.getEmployeeAddress();
            String username = view.getEmployeeUsername();
            String password = view.getEmployeeCreatePwd();

            Employee employee = new Employee(employeeId, name, email, phone, address, username, password);
            if (dao.updateEmployee(employee)) {
                JOptionPane.showMessageDialog(view, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearFields();
                loadEmployeeData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeIdStr = view.getEmployeeId();
            if (employeeIdStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Employee ID is required to delete an employee!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int employeeId;
            try {
                employeeId = Integer.parseInt(employeeIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid Employee ID. Please enter a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete employee ID: " + employeeId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteEmployee(employeeId)) {
                    JOptionPane.showMessageDialog(view, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.clearFields();
                    loadEmployeeData();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchValue = view.getSearchValue();
            if (searchValue.trim().isEmpty()) {
                loadEmployeeData(); // Load all employees if search field is empty
                return;
            }

            try {
                List<Employee> employees;
                try {
                    int employeeId = Integer.parseInt(searchValue);
                    employees = dao.searchEmployees("id", searchValue);
                } catch (NumberFormatException ex) {
                    employees = dao.searchEmployees("username", searchValue);
                }

                view.updateEmployeeTable(employees);
                if (employees.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No employees found for the given search value.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error searching employees: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}

