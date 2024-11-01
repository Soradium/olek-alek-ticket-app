package org.tuvarna.entity;
import jakarta.persistence.*;

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
    @Column(name = "trips_available")
    @OneToMany(mappedBy = "id")
    private List<Trip> tripsAvailable;

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
}
