package org.tuvarna.service;

import org.tuvarna.entity.Bus;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

public class BusService {

    private final TableDAO<Bus> busDao;

    public BusService() {
        this.busDao = FactoryDAO.getInstance().getDao(Bus.class);
    }

    public Bus getBusById(int id) {
        return busDao.findById(id);
    }

    public Bus addBus(Bus bus) {
        return busDao.save(bus);
    }
}
