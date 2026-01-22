package com.elysium.albumtracker.controller;

import com.elysium.albumtracker.model.Album;
import com.elysium.albumtracker.model.Artist;
import com.elysium.albumtracker.repository.AlbumRepository;
import com.elysium.albumtracker.repository.ArtistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

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

    @PostMapping(path = "/")
    public @ResponseBody Integer addArtist(@Valid @RequestParam String artistName) {
        Artist a = new Artist();
        updateArtist(a, artistName);

        return a.getId();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateArtistData(
            @PathVariable Integer id,
            @Valid @RequestParam String artistName
    ) {
        Optional<Artist> result = artistRepository.findById(id);

        Artist a = result.orElseGet(Artist::new);
        updateArtist(a, artistName);

        return new ResponseEntity<>(OK);
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

    @PostMapping(path = "/{id}/albums")
    public ResponseEntity<String> addAlbum(
            @PathVariable Integer id,
            @RequestParam Integer albumId
    ) {
        Optional<Artist> artist = artistRepository.findById(id);
        Optional<Album> album = albumRepository.findById(albumId);

        if (artist.isEmpty()) {
            return new ResponseEntity<>("Artist doesn't exist", NOT_FOUND);
        }

        if (album.isEmpty()) {
            return new ResponseEntity<>("Album doesn't exist", NOT_FOUND);
        }

        artist.get().addAlbum(album.get());
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
