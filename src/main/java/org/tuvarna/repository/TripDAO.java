package org.tuvarna.repository;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.tuvarna.entity.Trip;

import java.util.List;

public interface TripDAO {
    Trip getTripById(int id);
    List<Trip> getAllTrips();
    Trip addTrip(Trip trip);
}
