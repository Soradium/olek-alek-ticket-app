package org.tuvarna.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column()
    private String name;

    @OneToMany()
    @Column()
    private List<Cashier> cashiers;

    public Distributor() {
    }

    public Distributor(String name, List<Cashier> cashiers) {
        this.name = name;
        this.cashiers = cashiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<Cashier> cashiers) {
        this.cashiers = cashiers;
    }
}
