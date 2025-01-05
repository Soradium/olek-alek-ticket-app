package org.tuvarna.service;

import org.tuvarna.entity.Distributor;
import org.tuvarna.repository.DistributorDAO;

import java.util.List;

public class DistributorService {

    private final DistributorDAO distributorDAO;

    public DistributorService(DistributorDAO distributorDAO) {
        this.distributorDAO = distributorDAO;
    }

    public List<Distributor> getAllDistributors() {
        return distributorDAO.getDistributors();
    }

    public Distributor getDistributorByName(String name) {
        return distributorDAO.getDistributors().stream()
                .filter(d -> d.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Distributor getDistributorById(int id) {
        return distributorDAO.getDistributorById(id);
    }

    public Distributor addDistributor(Distributor distributor) {
        return distributorDAO.addDistributor(distributor);
    }
}

