package com.elysium.albumtracker.repository;

import com.elysium.albumtracker.model.Track;
import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, Integer> {
}
