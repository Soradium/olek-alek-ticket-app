package org.tuvarna.service;

import org.tuvarna.entity.Trip;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;
import java.util.stream.Collectors;

public class TripService {

    private final TableDAO<Trip> tripDAO;
    private String currentCompanyName;

    public TripService() {
        this.tripDAO = FactoryDAO.getInstance().getDao(Trip.class);
    }

    public void setCurrentCompany(String currentCompanyName) {
        this.currentCompanyName = currentCompanyName;
    }

    public String getCurrentCompanyName() {
        return currentCompanyName;
    }

    public List<Trip> getAllTrips() {
        return tripDAO.findAll();
    }

    public List<Trip> getAllTripsByCompany() {
        List<Trip> companyTrips = getAllTrips().
                stream().
                filter(t -> t.getCompany().getName().equals(currentCompanyName)).
                collect(Collectors.toList());
        return companyTrips;
    }

    public Trip getTripById(int id) {
        return tripDAO.findById(id);
    }

    public Trip addTrip(Trip trip) {
        return tripDAO.save(trip);
    }
}
