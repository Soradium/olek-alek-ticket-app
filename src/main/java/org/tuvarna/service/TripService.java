package org.tuvarna.service;

import org.tuvarna.entity.Trip;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;

public class TripService {

    private final TableDAO<Trip> tripDAO;

    public TripService() {
        this.tripDAO = FactoryDAO.getInstance().getDao(Trip.class);
    }

    public List<Trip> getAllTrips() {
        return tripDAO.findAll();
    }

    public Trip addTrip(Trip trip) {
        return tripDAO.save(trip);
    }
}
