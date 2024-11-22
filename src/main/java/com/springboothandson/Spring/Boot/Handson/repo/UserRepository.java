package com.springboothandson.Spring.Boot.Handson.repo;

import com.springboothandson.Spring.Boot.Handson.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
