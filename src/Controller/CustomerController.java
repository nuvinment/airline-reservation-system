package Controller;

import View.addCustomer_Admin;
import Model.Customer;
import DAO.CustomerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException; // Import SQLException for DAO calls
import java.util.List;

public class CustomerController {
    private addCustomer_Admin view;
    private CustomerDAO dao;

    public CustomerController(addCustomer_Admin view, CustomerDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.addAddCustomerListener(new AddCustomerListener());
        this.view.addClearListener(new ClearListener());
        this.view.addUpdateListener(new UpdateCustomerListener());
        this.view.addDeleteListener(new DeleteCustomerListener());
        this.view.setSearchButtonListener(new SearchButtonListener());
        
        loadCustomerData(); // Load data when controller is initialized
    }

    private void loadCustomerData() {
        try {
            List<Customer> customers = dao.getAllCustomers();
            view.setCustomerTableData(customers);
        } catch (Exception e) { // Catch generic Exception for now, or more specific SQLException
            JOptionPane.showMessageDialog(view,
                "Error loading customer data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    class AddCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get all input values from the view
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String username = view.getUsername();
            String cgender = view.getGender();
            String nicNo = view.getNIC();
            String email = view.getEmail();
            String phoneNo = view.getPhoneNo();
            String address = view.getAddress();
            String password = view.getPassword1(); // Use password1 for the actual password
            String passwordConfirm = view.getPassword2();

            // Validate input fields
            if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() ||
                cgender.trim().isEmpty() || nicNo.trim().isEmpty() || email.trim().isEmpty() ||
                phoneNo.trim().isEmpty() || address.trim().isEmpty() ||
                password.trim().isEmpty() || passwordConfirm.trim().isEmpty()) {

                JOptionPane.showMessageDialog(view,
                    "All fields must be filled!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Compare passwords
            if (!password.equals(passwordConfirm)) {
                JOptionPane.showMessageDialog(view,
                    "Passwords do not match!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Create Customer object with all data from the view
                Customer customer = new Customer(
                    firstName, lastName, username, cgender, nicNo,
                    email, phoneNo, address, password
                );

                if (dao.addCustomer(customer)) {
                    JOptionPane.showMessageDialog(view,
                        "Customer added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    view.clearFields();
                    loadCustomerData(); // Refresh table
                } else {
                    // This else block might be hit if the DAO returns false for other reasons (e.g., unique constraint violation)
                    throw new Exception("Failed to add customer. Database operation returned false. Check for duplicate NIC/Username.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                    "Error adding customer: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearFields();
        }
    }

    class UpdateCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String customerIdStr = view.getCustomerId();
            if (customerIdStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view,
                    "Customer ID is required to update a customer!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId;
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view,
                    "Invalid Customer ID. Please enter a number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get other fields for update
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String username = view.getUsername();
            String cgender = view.getGender();
            String nicNo = view.getNIC();
            String email = view.getEmail();
            String phoneNo = view.getPhoneNo();
            String address = view.getAddress();
            String password = view.getPassword1(); // Assuming password1 is the current password

            // Basic validation for update fields
            if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() ||
                cgender.trim().isEmpty() || nicNo.trim().isEmpty() || email.trim().isEmpty() ||
                phoneNo.trim().isEmpty() || address.trim().isEmpty() || password.trim().isEmpty()) {

                JOptionPane.showMessageDialog(view,
                    "All fields must be filled for update!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Create Customer object with all data from the view, including the ID
                Customer customer = new Customer(
                    customerId, firstName, lastName, username, cgender, nicNo,
                    email, phoneNo, address, password
                );

                if (dao.updateCustomer(customer)) {
                    JOptionPane.showMessageDialog(view,
                        "Customer updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    view.clearFields();
                    loadCustomerData(); // Refresh table
                } else {
                    throw new Exception("Customer update failed. Customer ID might not exist or other database error.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                    "Customer update failed: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    class DeleteCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String customerIdStr = view.getCustomerId();
            if (customerIdStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view,
                    "Customer ID is required to delete a customer!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId;
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view,
                    "Invalid Customer ID. Please enter a number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete customer ID: " + customerId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (dao.deleteCustomer(customerId)) {
                        JOptionPane.showMessageDialog(view,
                            "Customer deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                        view.clearFields();
                        loadCustomerData(); // Refresh table
                    } else {
                        throw new Exception("Customer deletion failed. Customer ID might not exist.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view,
                        "Customer deletion failed: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }
    
    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchValue = view.getSearchValue();
            
            if (searchValue.trim().isEmpty()) {
                // If search field is empty, load all customers
                loadCustomerData();
                return;
            }
            
            try {
                List<Customer> customers;
                // Determine search type based on input format
                if (searchValue.matches("\\d+")) { // If input is purely numeric, try searching by ID
                    customers = dao.searchCustomers("id", searchValue);
                } else { // Otherwise, search by username (or first name, depending on your preference)
                    customers = dao.searchCustomers("username", searchValue); // Or "first name"
                }
                
                view.updateCustomerTable(customers);
                if (customers.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No customers found for the given search value.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) { // Catch SQLException specifically
                JOptionPane.showMessageDialog(view, "Database error during search: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) { // Catch any other unexpected exceptions
                JOptionPane.showMessageDialog(view, "An unexpected error occurred during search: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
