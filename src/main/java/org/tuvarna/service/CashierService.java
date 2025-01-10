package org.tuvarna.service;

import org.tuvarna.entity.Cashier;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;

public class CashierService {

    private final TableDAO<Cashier> cashierDAO;

    public CashierService() {
        this.cashierDAO = FactoryDAO.getInstance().getDao(Cashier.class);
    }

    public List<Cashier> getAllCashiers() {
        return cashierDAO.findAll();
    }

    public Cashier getCashierById(int id) {
        return cashierDAO.findById(id);
    }

    public Cashier getCashierByName(String name) {
        return cashierDAO.findAll().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Cashier addCashier(Cashier cashier) {
        return cashierDAO.save(cashier);
    }

    public Cashier updateCashier(Cashier cashier) {
        return cashierDAO.update(cashier);
    }
}

