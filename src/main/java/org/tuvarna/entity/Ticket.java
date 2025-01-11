package org.tuvarna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "distributor_id")
    private Cashier cashier;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "is_sold")
    private boolean isSold = false;

    @Column(name = "is_rate")
    private boolean isRate = false;

    public Ticket() {}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket User: ").append(user).append("\n");
        sb.append("Ticket Seat: ").append(seat).append("\n");
        sb.append("Ticket Trip: ").append(trip).append("\n");
        if(isRate){
            sb.append("Ticket Rate: ").append(isRate).append("\n");
        }
        return sb.toString();
    }

    public Ticket(Seat seat, Trip trip) {
        this.seat = seat;
        this.trip = trip;
        this.isSold = false;
        this.isRate = false;
    }

    public Ticket(Cashier cashier, Seat seat, Trip trip) {
        this.cashier = cashier;
        this.seat = seat;
        this.trip = trip;
        this.isSold = false;
        this.isRate = false;
    }

    public boolean isRate() {
        return isRate;
    }

    public void setRate(boolean rate) {
        isRate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cashier getDistributor() {
        return cashier;
    }

    public void setDistributor(Cashier cashier) {
        this.cashier = cashier;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }


}
