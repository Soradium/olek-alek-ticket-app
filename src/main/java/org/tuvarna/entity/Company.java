package org.tuvarna.entity;
import jakarta.persistence.*;
import org.controlsfx.control.Rating;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name ="current_rating")
    private float current_rating;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "company",
            fetch = FetchType.EAGER
    )
    private List<Bus> bus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private List<Trip> trips = new ArrayList<>();

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCurrent_rating() {
        return current_rating;
    }

    public void setCurrent_rating(float rating) {
        this.current_rating = rating;
    }
}
