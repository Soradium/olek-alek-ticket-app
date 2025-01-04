package org.tuvarna.service;

import org.tuvarna.entity.Trip;
import org.tuvarna.repository.TripDAO;
import org.tuvarna.repository.TripDAOImpl;

import java.util.List;

public class TripService {

    private TripDAOImpl tripDAO = new TripDAOImpl();

    public List<Trip> getAllTrips() {
        return tripDAO.allTrips();
    }

    public Trip addTrip(Trip trip) {
        return tripDAO.addTrip(trip);
    }
}
