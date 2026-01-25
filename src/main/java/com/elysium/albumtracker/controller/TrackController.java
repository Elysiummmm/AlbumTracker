package com.elysium.albumtracker.controller;

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
import com.elysium.albumtracker.model.Track;
import com.elysium.albumtracker.repository.AlbumRepository;
import com.elysium.albumtracker.repository.TrackRepository;

import jakarta.validation.Valid;

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

    @PostMapping
    public @ResponseBody Integer createTrack(
            @Valid @RequestParam String trackName,
            @RequestParam Float length,
            @RequestParam Integer albumId) {
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
