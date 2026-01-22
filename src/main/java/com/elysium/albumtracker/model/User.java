package com.elysium.albumtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "author")
    private Set<Review> reviews;

    @OneToMany
    private Set<Album> listenedAlbums;

    @Size(min = 1, max = 100, message = "Username must not be empty and shorter than 100 characters")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<Album> getListenedAlbums() {
        return listenedAlbums;
    }

    public void addReview(Review r) {
        reviews.add(r);
    }

    public void addListenedAlbum(Album a) {
        listenedAlbums.add(a);
    }
}
