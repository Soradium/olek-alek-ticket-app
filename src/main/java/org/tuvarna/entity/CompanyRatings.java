package org.tuvarna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company_ratings")
public class CompanyRatings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company companyId;

    @Column(name = "rating_value")
    private int ratingValue;

    @Column(name = "review")
    private String review;

    public CompanyRatings() {
    }

    public CompanyRatings(Company companyId, int ratingValue, String review) {
        this.companyId = companyId;
        this.ratingValue = ratingValue;
        this.review = review;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
