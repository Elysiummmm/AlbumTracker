package com.elysium.albumtracker.controller;

import com.elysium.albumtracker.model.Album;
import com.elysium.albumtracker.model.Artist;
import com.elysium.albumtracker.model.Review;
import com.elysium.albumtracker.model.User;
import com.elysium.albumtracker.repository.AlbumRepository;
import com.elysium.albumtracker.repository.ReviewRepository;
import com.elysium.albumtracker.repository.UserRepository;
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
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private void updateUser(User u, String username, String pfpUrl) {
        u.setUsername(username);
        u.setPfpUrl(pfpUrl);
        userRepository.save(u);
    }

    @PostMapping(path = "/")
    public @ResponseBody Integer createUser(
            @Valid @RequestParam String username,
            @Valid @RequestParam String pfpUrl
    ) {
        User u = new User();
        updateUser(u, username, pfpUrl);

        return u.getId();
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Integer updateUserData(
            @PathVariable Integer id,
            @Valid @RequestParam String username,
            @Valid @RequestParam String pfpUrl
    ) {
        Optional<User> result = userRepository.findById(id);

        User u = result.orElseGet(User::new);
        updateUser(u, username, pfpUrl);

        return u.getId();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id) {
        Optional<User> result = userRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "User doesn't exist");
        } else {
            return result.get();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(OK);
    }

    @PutMapping(path = "/{id}/albums")
    public ResponseEntity<String> addAlbum(
            @PathVariable Integer id,
            @RequestParam Integer albumId
    ) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        Optional<Album> album = albumRepository.findById(id);

        if (album.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        user.get().addListenedAlbum(album.get());
        return new ResponseEntity<>(OK);
    }

    @PutMapping(path = "/{id}/reviews")
    public ResponseEntity<String> addReview(
            @PathVariable Integer id,
            @RequestParam Integer reviewId
    ) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        Optional<Review> review = reviewRepository.findById(id);

        if (review.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        user.get().addReview(review.get());
        return new ResponseEntity<>(OK);
    }

    @GetMapping(path = "/{id}/albums")
    public @ResponseBody Set<Album> getAlbums(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        return user.get().getListenedAlbums();
    }

    @GetMapping(path = "/{id}/reviews")
    public @ResponseBody Set<Review> getReviews(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        return user.get().getReviews();
    }
}
