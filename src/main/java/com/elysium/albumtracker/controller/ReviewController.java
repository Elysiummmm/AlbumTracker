package com.elysium.albumtracker.controller;

import com.elysium.albumtracker.model.Album;
import com.elysium.albumtracker.model.Review;
import com.elysium.albumtracker.model.Track;
import com.elysium.albumtracker.model.User;
import com.elysium.albumtracker.repository.AlbumRepository;
import com.elysium.albumtracker.repository.ReviewRepository;
import com.elysium.albumtracker.repository.TrackRepository;
import com.elysium.albumtracker.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private void updateReview(Review r, Integer authorId, Integer albumId, Integer score, String content) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (album.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        Optional<User> author = userRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        r.setAuthor(author.get());
        r.setAlbum(album.get());
        r.setScore(score);
        r.setContent(content);
        reviewRepository.save(r);
    }

    @PostMapping(path = "/")
    public @ResponseBody Integer createReview(
            @RequestParam Integer authorId,
            @RequestParam Integer albumId,
            @Valid @RequestParam Integer score,
            @Valid @RequestParam String content
    ) {
        Review r = new Review();
        updateReview(r, authorId, albumId, score, content);

        return r.getId();
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Integer updateReviewData(
            @PathVariable Integer id,
            @RequestParam Integer authorId,
            @RequestParam Integer albumId,
            @Valid @RequestParam Integer score,
            @Valid @RequestParam String content
    ) {
        Optional<Review> result = reviewRepository.findById(id);

        Review r = result.orElseGet(Review::new);
        updateReview(r, authorId, albumId, score, content);

        return r.getId();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Review getReview(@PathVariable Integer id) {
        Optional<Review> result = reviewRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Review doesn't exist");
        } else {
            return result.get();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer id) {
        reviewRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
