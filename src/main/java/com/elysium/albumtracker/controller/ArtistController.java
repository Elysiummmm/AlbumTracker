package com.elysium.albumtracker.controller;

import com.elysium.albumtracker.model.Artist;
import com.elysium.albumtracker.repository.ArtistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    private void updateArtist(Artist a, String artistName) {
        a.setName(artistName);
        artistRepository.save(a);
    }

    @PostMapping(path = "/")
    public @ResponseBody Integer addArtist(@Valid @RequestParam String artistName) {
        Artist a = new Artist();
        updateArtist(a, artistName);

        return a.getId();
    }

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Artist> getArtists() {
        return artistRepository.findAll();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateArtist(
            @PathVariable Integer id,
            @Valid @RequestParam String artistName
            ) {
        Optional<Artist> result = artistRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Artist doesn't exist");
        }

        Artist a = result.get();
        updateArtist(a, artistName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Artist getArtist(@PathVariable Integer id) {
        Optional<Artist> result = artistRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Artist doesn't exist");
        } else {
            return result.get();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Integer id) {
        artistRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
