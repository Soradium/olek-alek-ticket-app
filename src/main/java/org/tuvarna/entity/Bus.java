package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Bus {
    @Id
    @Column(name = "bus_id")
    private int id;
    @OneToMany(mappedBy = "bus")
    @JoinColumn(name = "bus_id")
    private List<Seat> seats;

    public Bus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
