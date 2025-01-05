package org.tuvarna.service;

import org.tuvarna.entity.Trip;
import org.tuvarna.repository.TripDAO;
import org.tuvarna.repository.TripDAOImpl;

import java.util.List;

public class TripService {

    private final TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public List<Trip> getAllTrips() {
        return tripDAO.getAllTrips();
    }

    public Trip addTrip(Trip trip) {
        return tripDAO.addTrip(trip);
    }
}
