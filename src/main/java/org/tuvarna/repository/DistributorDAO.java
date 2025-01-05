package org.tuvarna.repository;

import org.tuvarna.entity.Distributor;

import java.util.List;

public interface DistributorDAO {
    Distributor getDistributorById(int id);
    List<Distributor> getDistributors();
    Distributor addDistributor(Distributor distributor);

}
