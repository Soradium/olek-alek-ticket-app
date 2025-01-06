package org.tuvarna.service;

import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;
import org.tuvarna.repository.TripDAO;
import org.tuvarna.repository.TripDAOImpl;

import java.util.List;
import java.util.stream.Collectors;

public class TripService {

    private final TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    private String currentCompanyName;

    public void setCurrentCompany(String currentCompanyName) {
        this.currentCompanyName = currentCompanyName;
    }

    public String getCurrentCompanyName() {
        return currentCompanyName;
    }

    public List<Trip> getAllTrips() {
        return tripDAO.getAllTrips();
    }

    public List<Trip> getAllTripsByCompany() {
        List<Trip> companyTrips = getAllTrips().
                stream().
                filter(t -> t.getCompany().getName().equals(currentCompanyName)).
                collect(Collectors.toList());
        return companyTrips;
    }

    public Trip addTrip(Trip trip) {
        return tripDAO.addTrip(trip);
    }
}
