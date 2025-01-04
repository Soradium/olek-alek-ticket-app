package org.tuvarna.repository;

import org.tuvarna.entity.Distributor;

import java.util.List;

public interface DistributorDAO {
    Distributor getDistributorByName(String name);
    List<Distributor> getDistributors();
    void addDistributor(Distributor distributor);

}
