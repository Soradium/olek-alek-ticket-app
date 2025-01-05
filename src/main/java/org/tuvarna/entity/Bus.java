package org.tuvarna.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@Entity
@Table
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Bus() {
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
}
