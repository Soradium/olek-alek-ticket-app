package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private int id;
    @Column(name = "available")
    private boolean available;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    public Bus() {
    }

    public Bus(Company company) {
        this.company = company;
    }

    public Bus(int id, boolean available, Company company) {
        this.id = id;
        this.available = available;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", company=" + company +
                '}';
    }
}
