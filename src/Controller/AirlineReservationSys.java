package Controller;

import View.Login;
import Controller.LoginController; // Unified login controller

public class AirlineReservationSys {

    public static void main(String[] args) {
        // Launch Login View and Unified Controller
        Login loginFrame = new Login();
        new LoginController(loginFrame); // Unified controller handles admin + employee
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null); // Center the login frame
    }
}

