package org.tuvarna.service;

import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Distributor;
import org.tuvarna.repository.CashierDAO;

import java.util.List;

public class CashierService {

    private final CashierDAO cashierDAO;

    public CashierService(CashierDAO cashierDAO) {
        this.cashierDAO = cashierDAO;
    }

    public List<Cashier> getAllCashiers() {
        return cashierDAO.getCashiers();
    }

    public Cashier getCashierById(int id) {
        return cashierDAO.getCashierById(id);
    }

    public Cashier getCashierByName(String name) {
        return cashierDAO.getCashiers().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Cashier addCashier(Cashier cashier) {
        return cashierDAO.addCashier(cashier);
    }
}

