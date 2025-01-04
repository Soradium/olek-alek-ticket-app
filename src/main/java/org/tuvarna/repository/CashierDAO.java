package org.tuvarna.repository;

import org.tuvarna.entity.Cashier;

import java.util.List;

public interface CashierDAO {
    Cashier getCashierByName(String name);
    List<Cashier> getCompanies();
    void addCashier(Cashier Cashier);
}
