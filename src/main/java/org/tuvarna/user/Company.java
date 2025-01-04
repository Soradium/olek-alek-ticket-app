package org.tuvarna.user;

public class Company {

    private String departure;
    private String destination;
    private String date;
    private String tripType;

    public Company(String departure, String destination, String date, String tripType) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.tripType = tripType;
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
}
