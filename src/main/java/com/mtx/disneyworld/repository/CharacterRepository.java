package com.mtx.disneyworld.repository;

import com.mtx.disneyworld.entity.Character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

	Optional<Character> findByName(String name);

    boolean existsByName(String name);
}