package com.mtx.disneyworld.controller;

import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.entity.*;
import com.mtx.disneyworld.service.MyService;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

  @Autowired
  MyService service;

  @GetMapping("/movies")
  public ResponseEntity<?> getMovies(@RequestParam Map<String, String> params) {
    List<Movie> AllTheMovies = service.getAllMovies();

    if (params.keySet().stream().anyMatch(k -> k.trim().equals("genre"))) {
      //Filtrar por pelicula
      if (!params.get("genre").equals("")) {
        Pattern reg = java.util.regex.Pattern.compile("^[0-9]");
        boolean valid = !Arrays
          .asList(params.get("genre").split(""))
          .stream()
          .anyMatch(e -> !reg.asPredicate().test(e));

        if (valid) {
          AllTheMovies =
            AllTheMovies
              .stream()
              .filter(e -> e.hasGenre(Long.parseLong(params.get("genre"))))
              .collect(Collectors.toList());
        }
      }
    }

    if (params.keySet().stream().anyMatch(k -> k.trim().equals("name"))) {
      //Filtrar por nombre
      AllTheMovies =
        AllTheMovies
          .stream()
          .filter(
            e ->
              e
                .getTitle()
                .toLowerCase()
                .contains(params.get("name").toLowerCase())
          )
          .collect(Collectors.toList());
    }

    if (params.keySet().stream().anyMatch(k -> k.trim().equals("order"))) {
      //Ordenar
      String orderBy = params.get("order").toLowerCase();
      if (orderBy.equals("asc") || orderBy.equals("asc|desc")) {
        AllTheMovies.sort(
          (a, b) -> a.getId().intValue() - b.getId().intValue()
        );
      } else if (orderBy.equals("desc") || orderBy.equals("desc|asc")) {
        AllTheMovies.sort(
          (a, b) -> b.getId().intValue() - a.getId().intValue()
        );
      }
    }

    //Realizar la conversion

    if (params.keySet().stream().anyMatch(k -> k.trim().equals("details"))) {
      List<MovieDtoDetails> list = new ArrayList<>();
      for (Movie e : AllTheMovies) {
        list.add(new MovieDtoDetails(e));
      }
      return new ResponseEntity<>(list, HttpStatus.OK);
    } else {
      List<MovieDtoMin> list = new ArrayList<>();
      for (Movie e : AllTheMovies) {
        list.add(new MovieDtoMin(e));
      }
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
  }

  @PostMapping("/movies/create")
  public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
    try {
      if (movie.getTitle() == null) {
        return new ResponseEntity<>(
          "El titulo es necesario!",
          HttpStatus.BAD_REQUEST
        );
      }
      if (service.existsMovieByTitle(movie.getTitle())) {
        return new ResponseEntity<>(
          "El titulo \"" + movie.getTitle() + "\" ya está en uso!",
          HttpStatus.BAD_REQUEST
        );
      }
      movie.setId(null);
      service.saveMovie(movie);
      return new ResponseEntity<>(
        service.getMovieByTitle(movie.getTitle()).get().getId(),
        HttpStatus.CREATED
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/movies/update")
  public ResponseEntity<?> update(@RequestBody Movie dto) {
    try {
      if (!service.existsMovieById(dto.getId())) {
        return new ResponseEntity<>(
          "El recurso no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      Movie updated = new Movie();
      Movie older = service.getMovie(dto.getId()).get();

      updated.setId(dto.getId());
      updated.setTitle(
        dto.getTitle() != null ? dto.getTitle() : older.getTitle()
      );
      updated.setImage(
        dto.getImage() != null ? dto.getImage() : older.getImage()
      );
      updated.setRate(
        new Float(dto.getRate()) != null ? dto.getRate() : older.getRate()
      );
      updated.setCreationDate(
        dto.getCreationDate() != null
          ? dto.getCreationDate()
          : older.getCreationDate()
      );
      updated.setCharacters(older.getCharacters());
      updated.setGenres(older.getGenres());

      service.saveMovie(updated);
      return new ResponseEntity<>(
        service.getMovieByTitle(dto.getTitle()).get(),
        HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/movies/{id}/delete")
  ResponseEntity<?> delete(@PathVariable Long id) {
    if (!service.existsMovieById(id)) {
      return new ResponseEntity<>("No encontrado: " + id, HttpStatus.NOT_FOUND);
    }
    service.deleteMovie(id);
    return new ResponseEntity<>("Se ha eliminado: " + id, HttpStatus.OK);
  }

  @PostMapping("/movies/{idMovie}/characters/{idCharacter}")
  public ResponseEntity<?> addCharacter(
    @PathVariable Long idMovie,
    @PathVariable Long idCharacter
  ) {
    Optional<Movie> theMovie = service.getMovie(idMovie);
    Optional<com.mtx.disneyworld.entity.Character> theChar = service.getCharacter(
      idCharacter
    );
    try {
      if (!theMovie.isPresent()) {
        return new ResponseEntity<>(
          "La pelicula no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      if (!theChar.isPresent()) {
        return new ResponseEntity<>(
          "El personaje no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      theMovie.get().addCharacter(theChar.get());
      service.saveMovie(theMovie.get());

      return new ResponseEntity<>(new MovieDto(theMovie.get()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.OK);
    }
  }

  @DeleteMapping("/movies/{idMovie}/characters/{idCharacter}")
  public ResponseEntity<?> deleteCharacter(
    @PathVariable Long idMovie,
    @PathVariable Long idCharacter
  ) {
    Optional<Movie> theMovie = service.getMovie(idMovie);
    Optional<com.mtx.disneyworld.entity.Character> theChar = service.getCharacter(
      idCharacter
    );
    try {
      if (!theMovie.isPresent()) {
        return new ResponseEntity<>(
          "La pelicula no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      if (!theMovie.get().hasCharacter(idCharacter)) {
        return new ResponseEntity<>(
          "El personaje no está relacionado a esta pelicula!",
          HttpStatus.NOT_FOUND
        );
      }

      if (!theChar.isPresent()) {
        return new ResponseEntity<>(
          "El personaje no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      theMovie.get().removeCharacter(theChar.get());
      service.saveMovie(theMovie.get());

      return new ResponseEntity<>(new MovieDto(theMovie.get()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.OK);
    }
  }
}
