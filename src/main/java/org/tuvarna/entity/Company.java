package org.tuvarna.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name ="current_rating")
    private float currentRating;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "company",
            fetch = FetchType.EAGER
    )
    private List<Bus> bus = new ArrayList<>();

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH, CascadeType.REMOVE},
            mappedBy = "company",
            fetch = FetchType.EAGER)
    private List<Trip> trips = new ArrayList<>();


    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public List<Bus> getBuses() {
        return bus;
    }

    public void setBuses(List<Bus> bus) {
        this.bus = bus;
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

    public float getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(float rating) {
        this.currentRating = rating;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentRating=" + currentRating;
    }
}
