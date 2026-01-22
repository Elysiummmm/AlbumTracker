package com.elysium.albumtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 300, message = "Artist name must not be empty and at most 300 characters")
    private String name;

    @OneToMany(mappedBy = "artist")
    private Set<Album> albums;

    public Set<Album> getAlbums() {
        return albums;
    }

    public void addAlbum(Album a) {
        albums.add(a);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
