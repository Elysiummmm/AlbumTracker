package com.elysium.albumtracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private float length;
}
