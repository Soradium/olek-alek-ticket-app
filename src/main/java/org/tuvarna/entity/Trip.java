package org.tuvarna.entity;

import jakarta.persistence.*;
import org.hibernate.sql.Update;

import java.time.LocalDate;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int id;

    @Column
    private String departure;

    @Column
    private String destination;

    @Column(name = "trip_date")
    private LocalDate date;

    @Column(name = "type")
    private String tripType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "cashier_id")
    private Cashier cashier;

    @OneToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    public Trip() {
    }

    public Trip(String departure, String destination, LocalDate date, String tripType, Company company, Distributor distributor) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.tripType = tripType;
        this.company = company;
        this.distributor = distributor;
    }

    public Trip(String departure, String destination, LocalDate date, String tripType, Company company, Bus bus) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.tripType = tripType;
        this.company = company;
        this.bus = bus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    @Override
    public String toString() {
        return "Trip: " +
                "departure=" + departure +
                ", destination=" + destination +
                ", date=" + date +
                ", tripType=" + tripType +
                ", company=" + company.getName();
    }
}
