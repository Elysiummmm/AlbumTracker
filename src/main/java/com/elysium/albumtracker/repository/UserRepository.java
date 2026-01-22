package com.elysium.albumtracker.repository;

import com.elysium.albumtracker.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
