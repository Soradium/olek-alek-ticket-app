package org.tuvarna.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.tuvarna.service.CashierService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.tuvarna.service.CashierService;
import org.tuvarna.entity.Cashier;

public class CashierController {

    @FXML
    private Label cashierName;

    private CashierService service;
    private Cashier currentCashier;

    @FXML
    public void initialize() {
//        cashierName = loadCashierDetails();
    }

    private void loadCashierDetails(String name) {

        currentCashier = service.getCashierByName(name);
        if (currentCashier != null) {
            cashierName.setText(currentCashier.getName());
        } else {
            cashierName.setText("Cashier not found");
        }
    }

    public void setCashierService(CashierService service) {
        this.service = service;
    }

    public Cashier getCurrentCashier() {
        return currentCashier;
    }

    public void setCurrentCashier(Cashier currentCashier) {
        this.currentCashier = currentCashier;
    }
}
