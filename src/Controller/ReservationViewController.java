package Controller;

import DAO.reservationDAO;
import Model.reservation;
import View.reservations;
import javax.swing.JOptionPane;
import java.util.List;

public class ReservationViewController {
    private reservations view;
    private reservationDAO dao;

    public ReservationViewController(reservations view) {
        this.view = view;
        this.dao = new reservationDAO();
        loadReservations(); // Load on start
    }

    public void loadReservations() {
        int customerId = view.getCustomerId();

        List<reservation> list = dao.getReservationsByCustomerId(customerId);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No reservations found.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.displayReservations(list);
        }
    }
}
