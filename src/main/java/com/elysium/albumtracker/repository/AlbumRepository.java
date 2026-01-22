package com.elysium.albumtracker.repository;

import com.elysium.albumtracker.model.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
}
