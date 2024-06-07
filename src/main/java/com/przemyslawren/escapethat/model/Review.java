package com.przemyslawren.escapethat.model;

import com.przemyslawren.escapethat.model.interfaces.Rateable;
import com.przemyslawren.escapethat.model.interfaces.Reviewable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review implements Rateable, Reviewable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private int rating;
    private String comment;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private EscapeRoom escapeRoom;

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String getReview() {
        return comment;
    }

    @Override
    public void setReview(String review) {
        this.comment = review;
    }
}
