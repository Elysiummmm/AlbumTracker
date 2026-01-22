package com.elysium.albumtracker.controller;

import com.elysium.albumtracker.model.Album;
import com.elysium.albumtracker.repository.AlbumRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    private void updateAlbum(Album a, String albumName, String jacketURL) {
        a.setName(albumName);
        a.setJacketURL(jacketURL);
        albumRepository.save(a);
    }

    @PostMapping(path = "/")
    public @ResponseBody Integer addAlbum(
            @Valid @RequestParam String albumName,
            @Valid @RequestParam String jacketURL) {
        Album a = new Album();
        updateAlbum(a, albumName, jacketURL);

        return a.getId();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateAlbumData(
            @PathVariable Integer id,
            @Valid @RequestParam String albumName,
            @Valid @RequestParam String jacketURL
    ) {
        Optional<Album> result = albumRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Album doesn't exist");
        }

        Album a = result.get();
        updateAlbum(a, albumName, jacketURL);

        return new ResponseEntity<>(HttpStatus.OK);
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Integer id) {
        albumRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
