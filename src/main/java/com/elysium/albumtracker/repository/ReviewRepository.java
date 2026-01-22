package com.elysium.albumtracker.repository;

import com.elysium.albumtracker.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
