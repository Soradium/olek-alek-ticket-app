package org.tuvarna.service;

import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Company;
import org.tuvarna.repository.BusDAO;

public class BusService {

    private final BusDAO busService;

    public BusService(BusDAO busService) {
        this.busService = busService;
    }

    public Bus getBusById(int id) {
        return busService.getBusById(id);
    }

    public Bus addBus(Bus bus) {
        return busService.addBus(bus);
    }
}
