package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Cashier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private Company company;
    @Column(name = "tickets")
    @OneToMany
    private List<Ticket> tickets = new ArrayList<>();


}
