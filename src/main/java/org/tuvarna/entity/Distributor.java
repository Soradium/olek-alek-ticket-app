package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distributor_id")
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "distributor",
            fetch = FetchType.EAGER
    )
    private List<Cashier> cashiers = new ArrayList<>();

    @OneToMany(
            cascade = {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.PERSIST},
            mappedBy = "distributor",
            fetch = FetchType.EAGER
    )
    private List<Trip> trips = new ArrayList<>();

    public Distributor() {
    }

    public Distributor(String name, List<Cashier> cashiers) {
        this.name = name;
        this.cashiers = cashiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public void addCashier(Cashier cashier) {
        this.cashiers.add(cashier);
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

}
