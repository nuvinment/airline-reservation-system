package Controller;

import DAO.accessReserveDAO;
import Model.accessReserveModel;
import View.accessReservations;
import View.reservations;

import javax.swing.JOptionPane;

public class accessReserveController {
    private accessReservations view;
    private accessReserveDAO dao;
    private accessReserveModel model;

    public accessReserveController(accessReservations view) {
        this.view = view;
        this.dao = new accessReserveDAO();
        this.model = new accessReserveModel();

        // Attach event handler to login button
        this.view.setLoginAction(e -> authenticateUser());
    }

    private void authenticateUser() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter both username and password.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int customerId = dao.authenticateCustomer(username, password);

        if (customerId != -1) {
            // Set model values
            model.setCustomerId(customerId);
            model.setUsername(username);
            model.setPassword(password);

            JOptionPane.showMessageDialog(view, "Login successful! Customer ID: " + customerId);

            // Open reservations UI and load customer data
            reservations resView = new reservations(customerId);
            resView.setVisible(true);
            new ReservationViewController(resView);

            view.dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(view, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
