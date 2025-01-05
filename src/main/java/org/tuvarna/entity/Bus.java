package org.tuvarna.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@Entity
@Table
public class Bus {
    @Id
    private int id;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "bus",
            fetch = FetchType.EAGER)
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
