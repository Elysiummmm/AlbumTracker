package com.elysium.albumtracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Represents a musical album.
 */
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The artist behind this album.
     */
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    @JsonBackReference
    private Artist artist;

    /**
     * All tracks on this album.
     */
    @OneToMany(mappedBy = "album")
    private Set<Track> tracks;

    /**
     * The name of this album.
     */
    @Size(min = 1)
    private String name;

    /**
     * The URL of this albums' jacket art.
     */
    @Size(min = 1)
    private String jacketURL;

    public Set<Track> getTracks() {
        return tracks;
    }

    public Integer getId() {
        return id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJacketURL() {
        return jacketURL;
    }

    public void setJacketURL(String jacketURL) {
        this.jacketURL = jacketURL;
    }
}
