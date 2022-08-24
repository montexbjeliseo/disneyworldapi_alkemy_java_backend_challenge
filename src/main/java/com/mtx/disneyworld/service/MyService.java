package com.mtx.disneyworld.service;

import com.mtx.disneyworld.dto.CharacterDto;
import com.mtx.disneyworld.dto.CharacterDtoMin;
import com.mtx.disneyworld.entity.Character;
import com.mtx.disneyworld.entity.Genre;
import com.mtx.disneyworld.entity.Movie;
import com.mtx.disneyworld.repository.CharacterRepository;
import com.mtx.disneyworld.repository.GenreRepository;
import com.mtx.disneyworld.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MyService {

  @Autowired
  CharacterRepository charRepo;

  @Autowired
  MovieRepository movieRepo;

  @Autowired
  GenreRepository genreRepo;

  public List<Character> getAllCharacters() {
    return charRepo.findAll();
  }

  public Optional<Character> getCharacter(Long id) {
    return charRepo.findById(id);
  }

  public Optional<Character> getCharacterByName(String name) {
    return charRepo.findByName(name);
  }

  public void saveCharacter(Character c) {
    charRepo.save(c);
  }

  public void deleteCharacter(Long id) {
    charRepo.deleteById(id);
  }

  public boolean existsCharacterById(Long id) {
    return charRepo.existsById(id);
  }

  public boolean existsCharacterByName(String name) {
    return charRepo.existsByName(name);
  }

  public List<Movie> getAllMovies() {
    return movieRepo.findAll();
  }

  public Optional<Movie> getMovie(Long id) {
    return movieRepo.findById(id);
  }

  public Optional<Movie> getMovieByTitle(String title) {
    return movieRepo.findByTitle(title);
  }

  public void saveMovie(Movie c) {
    movieRepo.save(c);
  }

  public void deleteMovie(Long id) {
    movieRepo.deleteById(id);
  }

  public boolean existsMovieById(Long id) {
    return movieRepo.existsById(id);
  }

  public boolean existsMovieByTitle(String title) {
    return movieRepo.existsByTitle(title);
  }

  public List<Genre> getAllGenres() {
    return genreRepo.findAll();
  }

  public Optional<Genre> getGenre(Long id) {
    return genreRepo.findById(id);
  }

  public Optional<Genre> getGenreByName(String name) {
    return genreRepo.findByName(name);
  }

  public void saveGenre(Genre c) {
    genreRepo.save(c);
  }

  public void deleteGenre(Long id) {
    genreRepo.deleteById(id);
  }

  public boolean existsGenreById(Long id) {
    return genreRepo.existsById(id);
  }

  public boolean existsGenreByName(String name) {
    return genreRepo.existsByName(name);
  }
}
