package com.elysium.albumtracker.controller;

import com.elysium.albumtracker.model.Album;
import com.elysium.albumtracker.model.Artist;
import com.elysium.albumtracker.model.Track;
import com.elysium.albumtracker.repository.AlbumRepository;
import com.elysium.albumtracker.repository.TrackRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/tracks")
public class TrackController {
    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private void updateTrack(Track t, String trackName, Float length, Integer albumId) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (album.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        t.setName(trackName);
        t.setLength(length);
        t.setAlbum(album.get());
        trackRepository.save(t);
    }

    @PostMapping(path = "/")
    public @ResponseBody Integer createTrack(
            @Valid @RequestParam String trackName,
            @RequestParam Float length,
            Integer albumId) {
        Track t = new Track();
        updateTrack(t, trackName, length, albumId);

        return t.getId();
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Integer updateTrackData(
            @PathVariable Integer id,
            @Valid @RequestParam String trackName,
            @RequestParam Float length,
            @RequestParam Integer albumId
    ) {
        Optional<Track> result = trackRepository.findById(id);

        Track t = result.orElseGet(Track::new);
        updateTrack(t, trackName, length, albumId);

        return t.getId();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Track getTrack(@PathVariable Integer id) {
        Optional<Track> result = trackRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Track doesn't exist");
        } else {
            return result.get();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Integer id) {
        trackRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
