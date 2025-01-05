package org.tuvarna.repository;

import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Cashier;

import java.util.List;

public interface BusDAO {
    Bus getBusById(int id);
    List<Bus> getBuses();
    Bus addBus(Bus bus);
}
