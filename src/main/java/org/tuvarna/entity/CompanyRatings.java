package org.tuvarna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company_ratings")
public class CompanyRatings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company companyId;

    @Column(name = "rating_value")
    private int ratingValue;

    public CompanyRatings() {
    }

    public CompanyRatings(Company companyId, int ratingValue) {
        this.companyId = companyId;
        this.ratingValue = ratingValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}
