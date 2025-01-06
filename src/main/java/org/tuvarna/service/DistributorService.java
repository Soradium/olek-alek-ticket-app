package org.tuvarna.service;

import org.tuvarna.entity.Distributor;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;

public class DistributorService {

    private final TableDAO<Distributor> distributorDAO;

    public DistributorService() {
        this.distributorDAO = FactoryDAO.getInstance().getDao(Distributor.class);
    }

    public List<Distributor> getAllDistributors() {
        return distributorDAO.findAll();
    }

    public Distributor getDistributorByName(String name) {
        return distributorDAO.findAll().stream()
                .filter(d -> d.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Distributor getDistributorById(int id) {
        return distributorDAO.findById(id);
    }

    public Distributor addDistributor(Distributor distributor) {
        return distributorDAO.save(distributor);
    }
}

