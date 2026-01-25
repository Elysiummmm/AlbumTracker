package com.elysium.albumtracker.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.elysium.albumtracker.model.Album;
import com.elysium.albumtracker.model.Artist;
import com.elysium.albumtracker.repository.AlbumRepository;
import com.elysium.albumtracker.repository.ArtistRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private void updateArtist(Artist a, String artistName) {
        a.setName(artistName);
        artistRepository.save(a);
    }

    @PostMapping
    public @ResponseBody Integer createArtist(@Valid @RequestParam String artistName) {
        Artist a = new Artist();
        updateArtist(a, artistName);

        return a.getId();
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Integer updateArtistData(
            @PathVariable Integer id,
            @Valid @RequestParam String artistName
    ) {
        Optional<Artist> result = artistRepository.findById(id);

        Artist a = result.orElseGet(Artist::new);
        updateArtist(a, artistName);

        return a.getId();
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
        return new ResponseEntity<>(OK);
    }

    @GetMapping(path = "/{id}/albums")
    public @ResponseBody Set<Album> getAlbums(@PathVariable Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        return artist.get().getAlbums();
    }
}
