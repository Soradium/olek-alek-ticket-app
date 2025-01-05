package org.tuvarna.repository;

import org.tuvarna.entity.Cashier;

import java.util.List;

public interface CashierDAO {
    Cashier getCashierById(int id);
    List<Cashier> getCashiers();
    Cashier addCashier(Cashier Cashier);
}
