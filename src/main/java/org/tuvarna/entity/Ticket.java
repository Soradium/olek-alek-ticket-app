package org.tuvarna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat")
    private Seat seat;

    @ManyToOne()
    @JoinColumn(name = "tripsAvailable")
    private Company company;
}
