package com.elysium.albumtracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String jacketURL;
}
