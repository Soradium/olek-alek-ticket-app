package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private int id;
    @OneToMany()
    @JoinColumn(name = "bus_id")
    private List<Seat> seats;
}
