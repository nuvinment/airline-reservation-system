/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.reservationMgmtModel;
import DAO.reservationMgmtDAO;
import View.reservationMgmt;
import java.util.List;

public class reservationMgmtController {
    private final reservationMgmt view;
    private final reservationMgmtDAO dao;

    public reservationMgmtController(reservationMgmt view, reservationMgmtDAO dao) {
        this.view = view;
        this.dao = dao;
        setupController();
        loadAllReservations();
    }

    private void setupController() {
        view.getAddButton().addActionListener(e -> handleAdd());
        view.getUpdateButton().addActionListener(e -> handleUpdate());
        view.getDeleteButton().addActionListener(e -> handleDelete());
        view.getClearButton().addActionListener(e -> view.clearForm());
        view.getSearchButton().addActionListener(e -> handleSearch());
    }

    private void handleAdd() {
        try {
            reservationMgmtModel reservation = view.getReservationFromForm();
            if (dao.addReservation(reservation)) {
                view.showSuccess("Reservation added successfully");
                view.clearForm();
                loadAllReservations();
            }
        } catch (Exception e) {
            view.showError("Error adding reservation: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        try {
            reservationMgmtModel reservation = view.getReservationFromForm();
            if (dao.updateReservation(reservation)) {
                view.showSuccess("Reservation updated successfully");
                loadAllReservations();
            }
        } catch (Exception e) {
            view.showError("Error updating reservation: " + e.getMessage());
        }
    }

    private void handleDelete() {
        try {
            String refCode = view.getReferenceCode();
            if (dao.deleteReservation(refCode)) {
                view.showSuccess("Reservation deleted successfully");
                view.clearForm();
                loadAllReservations();
            }
        } catch (Exception e) {
            view.showError("Error deleting reservation: " + e.getMessage());
        }
    }

    private void handleSearch() {
        try {
            String searchTerm = view.getSearchTerm();
            List<reservationMgmtModel> results = dao.searchReservation(searchTerm);
            view.displayReservations(results);
        } catch (Exception e) {
            view.showError("Error searching: " + e.getMessage());
        }
    }

    private void loadAllReservations() {
        try {
            List<reservationMgmtModel> reservations = dao.getAllReservations();
            view.displayReservations(reservations);
        } catch (Exception e) {
            view.showError("Error loading reservations: " + e.getMessage());
        }
    }
}
