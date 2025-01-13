package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private int id;
    @Column(name = "available")
    private boolean available;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToOne(mappedBy = "bus", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})
    private Trip trip;
    public Bus() {
    }

    public Bus(Company company) {
        this.company = company;
        this.available = true;
    }

    public Bus(int id, boolean available, Company company) {
        this.id = id;
        this.available = available;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "Bus: " +
                "number=" + id;
    }
}
