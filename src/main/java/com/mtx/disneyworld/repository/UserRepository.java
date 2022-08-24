package com.mtx.disneyworld.repository;

import com.mtx.disneyworld.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);
    
    Optional<User> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String name);
}