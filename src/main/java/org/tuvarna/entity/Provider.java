package org.tuvarna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "access_level")
    private boolean accessLevel;

    public Provider() {
    }

    public Provider(String firstName, boolean accessLevel) {
        this.firstName = firstName;
        this.accessLevel = accessLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(boolean accessLevel) {
        this.accessLevel = accessLevel;
    }
}
