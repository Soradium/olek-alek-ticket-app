package org.tuvarna.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.entity.Bus;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;
import java.util.stream.Collectors;

public class BusService {

    private final TableDAO<Bus> busDao;

    private String currentCompanyName;

    private final static Logger logger = LogManager.getLogger(BusService.class);

    public void setCurrentCompany(String currentCompanyName) {
        this.currentCompanyName = currentCompanyName;
    }

    public BusService() {
        this.busDao = FactoryDAO.getInstance().getDao(Bus.class);
    }

    public Bus getBusById(int id) {
        return busDao.findById(id);
    }

    public Bus addBus(Bus bus) {
        return busDao.save(bus);
    }

    public List<Bus> getAllBuses() {
        return busDao.findAll();
    }

    public List<Bus> getAllAvailableBusesByCompany() {
        List<Bus> companyBuses = getAllBuses().
                stream().
                filter(t -> t.getCompany().getName().equals(currentCompanyName) && t.isAvailable()).
                collect(Collectors.toList());
        logger.info("All available buses in company: {}", companyBuses);
        return companyBuses;
    }
}
