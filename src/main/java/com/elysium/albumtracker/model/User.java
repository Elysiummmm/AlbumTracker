package com.elysium.albumtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * Represents a user that listens to music.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * All reviews written by this user.
     */
    @OneToMany(mappedBy = "author")
    private Set<Review> reviews;

    /**
     * All albums this user has listened to.
     */
    @OneToMany
    private Set<Album> listenedAlbums;

    /**
     * This users' name.
     */
    @Size(min = 1, max = 100, message = "Username must not be empty and shorter than 100 characters")
    private String username;

    /**
     * The URL of this users' profile picture.
     */
    private String pfpUrl;

    public Integer getId() {
        return id;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }

    public void setPfpUrl(String pfpUrl) {
        this.pfpUrl = pfpUrl;
    }

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
