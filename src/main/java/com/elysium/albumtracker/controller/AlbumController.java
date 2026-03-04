package com.elysium.albumtracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;
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
@RequestMapping(path = "/albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private void updateAlbum(Album a, String albumName, String jacketURL, Integer artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (artist.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        a.setName(albumName);
        a.setJacketURL(jacketURL);
        a.setArtist(artist.get());
        albumRepository.save(a);
    }

    @PostMapping
    public @ResponseBody Integer createAlbum(
            @Valid @RequestParam String albumName,
            @Valid @RequestParam String jacketURL,
            @RequestParam Integer artistId) {
        Album a = new Album();
        updateAlbum(a, albumName, jacketURL, artistId);

        return a.getId();
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Integer updateAlbumData(
            @PathVariable Integer id,
            @Valid @RequestParam String albumName,
            @Valid @RequestParam String jacketURL,
            @RequestParam Integer artistId
    ) {
        Optional<Album> result = albumRepository.findById(id);

        Album a = result.orElseGet(Album::new);
        updateAlbum(a, albumName, jacketURL, artistId);

        return a.getId();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Album getAlbum(@PathVariable Integer id) {
        Optional<Album> result = albumRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Album doesn't exist");
        } else {
            return result.get();
        }
    }

    @GetMapping
    public @ResponseBody Iterable<Album> getAlbums() {
        return albumRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Integer id) {
        albumRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
