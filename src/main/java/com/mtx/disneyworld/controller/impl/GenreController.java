package com.mtx.disneyworld.controller.impl;

import com.mtx.disneyworld.entity.Genre;
import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.service.IGenreService;
import com.mtx.disneyworld.util.Constants.Endpoints;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoints.GENRE)
public class GenreController {

    @Autowired
    IGenreService genreService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(genreService.getAll(params), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GenrePostDto dto) {
        return new ResponseEntity<>(genreService.save(dto), HttpStatus.OK);
    }

    @PatchMapping(Endpoints.GENREID)
    public ResponseEntity<?> update(@PathVariable Long genreId, @RequestBody GenrePostDto dto) {
        return new ResponseEntity<>(genreService.update(genreId, dto), HttpStatus.OK);
    }

    @DeleteMapping(Endpoints.GENREID)
    ResponseEntity<?> delete(@PathVariable Long genreId) {
        return new ResponseEntity<>(genreService.delete(genreId), HttpStatus.OK);
    }

    @PostMapping(Endpoints.ADD_MOVIE)
    public ResponseEntity<?> addMovie(
            @PathVariable Long genreId,
            @PathVariable Long movieId
    ) {
        return new ResponseEntity<>(genreService.addMovie(genreId, movieId), HttpStatus.OK);
    }

    @DeleteMapping(Endpoints.ADD_MOVIE)
    public ResponseEntity<?> deleteMovie(
            @PathVariable Long genreId,
            @PathVariable Long movieId
    ) {
        return new ResponseEntity<>(genreService.deleteMovie(genreId, movieId), HttpStatus.OK);
    }
}
