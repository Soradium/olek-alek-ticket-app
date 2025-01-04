package org.tuvarna.repository;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.tuvarna.entity.Trip;

import java.util.List;

public interface TripDAO {
    List<Trip> allTrips();
    Trip addTrip(Trip trip);
}
