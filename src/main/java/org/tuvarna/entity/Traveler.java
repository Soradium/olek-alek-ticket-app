package org.tuvarna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "traveler")
public class Traveler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "traveler_id")
    private int id;
}
