package Controller;

import DAO.AdminDAO;
import DAO.employeeLoginDAO;
import Model.Admin;
import Model.employeeLoginModel;
import View.Login;
import View.dashboardAdmin;
import View.dashbaordEmployee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private Login view;
    private AdminDAO adminDAO;
    private employeeLoginDAO employeeDAO;

    public LoginController(Login view) {
        this.view = view;
        this.adminDAO = new AdminDAO();
        this.employeeDAO = new employeeLoginDAO();

        this.view.setLoginAction(new LoginListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please enter both username and password.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // First, try admin login
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            if (adminDAO.authenticate(admin)) {
                JOptionPane.showMessageDialog(view, "Admin login successful!");
                new dashboardAdmin().setVisible(true);
                view.dispose();
                return;
            }

            // Next, try employee login
            employeeLoginModel employee = new employeeLoginModel();
            employee.setUsername(username);
            employee.setPassword(password);
            if (employeeDAO.authenticateEmployee(employee)) {
                JOptionPane.showMessageDialog(view, "Employee login successful!");
                new dashbaordEmployee().setVisible(true);
                view.dispose();
                return;
            }

            // If neither match
            JOptionPane.showMessageDialog(view, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
