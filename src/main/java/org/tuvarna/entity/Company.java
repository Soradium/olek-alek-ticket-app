package org.tuvarna.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trips_available")
    private List<Trip> tripsAvailable = new ArrayList<>();

    @Column
    private float rating;

    @Column(name = "total_ratings")
    private int totalRatings;

    public Company() {
    }

    public Company(String name, List<Trip> tripsAvailable) {
        this.name = name;
        this.tripsAvailable = tripsAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Trip> getTripsAvailable() {
        return tripsAvailable;
    }

    public void setTripsAvailable(List<Trip> tripsAvailable) {
        this.tripsAvailable = tripsAvailable;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }
}
