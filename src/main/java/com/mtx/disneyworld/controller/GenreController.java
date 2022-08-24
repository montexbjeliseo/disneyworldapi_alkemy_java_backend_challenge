package com.mtx.disneyworld.controller;

import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.entity.*;
import com.mtx.disneyworld.service.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GenreController {

  @Autowired
  MyService service;

  @GetMapping("/genres")
  public ResponseEntity<?> getgenres(@RequestParam Map<String, String> params) {
    List<Genre> AllThegenres = service.getAllGenres();
    if (params.keySet().stream().anyMatch(k -> k.trim().equals("name"))) {
      //Filtrar por nombre
      AllThegenres =
        AllThegenres
          .stream()
          .filter(
            e ->
              e
                .getName()
                .toLowerCase()
                .contains(params.get("name").toLowerCase())
          )
          .collect(Collectors.toList());
    }

    if (params.keySet().stream().anyMatch(k -> k.trim().equals("order"))) {
      //Ordenar
      String orderBy = params.get("order").toLowerCase();
      if (orderBy.equals("asc") || orderBy.equals("asc|desc")) {
        AllThegenres.sort(
          (a, b) -> a.getId().intValue() - b.getId().intValue()
        );
      } else if (orderBy.equals("desc") || orderBy.equals("desc|asc")) {
        AllThegenres.sort(
          (a, b) -> b.getId().intValue() - a.getId().intValue()
        );
      }
    }

    //Realizar la conversion

    if (params.keySet().stream().anyMatch(k -> k.trim().equals("details"))) {
      List<GenreDtoDetails> list = new ArrayList<>();
      for (Genre e : AllThegenres) {
        list.add(new GenreDtoDetails(e));
      }
      return new ResponseEntity<>(list, HttpStatus.OK);
    } else {
      List<GenreDtoMin> list = new ArrayList<>();
      for (Genre e : AllThegenres) {
        list.add(new GenreDtoMin(e));
      }
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
  }

  @PostMapping("/genres/create")
  public ResponseEntity<?> createGenre(@RequestBody Genre genre) {
    try {
      if (genre.getName() == null) {
        return new ResponseEntity<>(
          "El nombre es necesario!",
          HttpStatus.BAD_REQUEST
        );
      }
      if (service.existsGenreByName(genre.getName())) {
        return new ResponseEntity<>(
          "El nombre \"" + genre.getName() + "\" ya está en uso!",
          HttpStatus.BAD_REQUEST
        );
      }
      genre.setId(null);
      service.saveGenre(genre);
      return new ResponseEntity<>(
        service.getGenreByName(genre.getName()).get().getId(),
        HttpStatus.CREATED
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/genres/update")
  public ResponseEntity<?> update(@RequestBody Genre dto) {
    try {
      if (!service.existsGenreById(dto.getId())) {
        return new ResponseEntity<>(
          "El recurso no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      Genre updated = new Genre();
      Genre older = service.getGenre(dto.getId()).get();

      updated.setId(dto.getId());
      updated.setName(dto.getName() != null ? dto.getName() : older.getName());
      updated.setImage(
        dto.getImage() != null ? dto.getImage() : older.getImage()
      );
      updated.setMovies(older.getMovies());

      service.saveGenre(updated);
      return new ResponseEntity<>(
        service.getGenreByName(dto.getName()).get(),
        HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/genres/{id}/delete")
  ResponseEntity<?> delete(@PathVariable Long id) {
    if (!service.existsGenreById(id)) {
      return new ResponseEntity<>("No encontrado: " + id, HttpStatus.NOT_FOUND);
    }
    service.deleteGenre(id);
    return new ResponseEntity<>("Se ha eliminado: " + id, HttpStatus.OK);
  }

  @PostMapping("/genres/{idGenre}/movies/{idMovie}")
  public ResponseEntity<?> addMovie(
    @PathVariable Long idGenre,
    @PathVariable Long idMovie
  ) {
    Optional<Genre> theGenre = service.getGenre(idGenre);
    Optional<com.mtx.disneyworld.entity.Movie> theMovie = service.getMovie(
      idMovie
    );
    try {
      if (!theGenre.isPresent()) {
        return new ResponseEntity<>(
          "El genero no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      if (!theMovie.isPresent()) {
        return new ResponseEntity<>(
          "La pelicula no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      theGenre.get().addMovie(theMovie.get());
      service.saveGenre(theGenre.get());

      return new ResponseEntity<>(new GenreDto(theGenre.get()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.OK);
    }
  }

  @DeleteMapping("/genres/{idGenre}/movies/{idMovie}")
  public ResponseEntity<?> deleteMovie(
    @PathVariable Long idGenre,
    @PathVariable Long idMovie
  ) {
    Optional<Genre> theGenre = service.getGenre(idGenre);
    Optional<com.mtx.disneyworld.entity.Movie> theMovie = service.getMovie(
      idMovie
    );
    try {
      if (!theGenre.isPresent()) {
        return new ResponseEntity<>(
          "El genero no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      if (!theGenre.get().hasMovie(idMovie)) {
        return new ResponseEntity<>(
          "La pelicula no está relacionada a este genero!",
          HttpStatus.NOT_FOUND
        );
      }

      if (!theMovie.isPresent()) {
        return new ResponseEntity<>(
          "La pelicula no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      theGenre.get().removeMovie(theMovie.get());
      service.saveGenre(theGenre.get());

      return new ResponseEntity<>(new GenreDto(theGenre.get()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.OK);
    }
  }
}
