package org.tuvarna.service;

import org.tuvarna.entity.Bus;
import org.tuvarna.repository.BusDAO;

public class BusService {
    private BusDAO busService;

    public BusService(BusDAO busService) {
        this.busService = busService;
    }

    public void addBus(Bus bus) {
        busService.addBus(bus);
    }
}
