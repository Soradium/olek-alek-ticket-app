package org.tuvarna.entity;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trips")
    private List<Trip> tripsAvailable;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
        //this.tripsAvailable = tripsAvailable;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tripsAvailable=" + tripsAvailable +
                '}';
    }
}
