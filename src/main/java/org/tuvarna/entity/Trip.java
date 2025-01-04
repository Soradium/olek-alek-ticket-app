package org.tuvarna.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String departure;

    private String destination;

    private String date;

    @Column(name = "triptype")
    private String tripType;

    public Trip() {}

    public Trip(String departure, String destination, String date, String tripType) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.tripType = tripType;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    @Override
    public String toString() {
        return "Departure: " + departure + ", Destination: " + destination +
                ", Time: " + date + ", Type: " + tripType;
    }
}
