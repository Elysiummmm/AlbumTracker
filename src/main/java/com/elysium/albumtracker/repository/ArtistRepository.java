package com.elysium.albumtracker.repository;

import com.elysium.albumtracker.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
}
