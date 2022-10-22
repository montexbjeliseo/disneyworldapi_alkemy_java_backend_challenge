package com.mtx.disneyworld.controller.impl;

import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.service.IMovieService;
import com.mtx.disneyworld.util.Constants.Endpoints;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoints.MOVIE)
public class MovieController {

    @Autowired
    IMovieService movieService;

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(movieService.getAll(params), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MoviePostDto dto) {
        try {
            return new ResponseEntity<>(
                    movieService.save(dto),
                    HttpStatus.CREATED
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(Endpoints.MOVIEID)
    public ResponseEntity<?> update(@PathVariable Long movieId, @RequestBody MoviePostDto dto) {
        try {

            return new ResponseEntity<>(
                    movieService.update(movieId, dto),
                    HttpStatus.OK
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(Endpoints.MOVIEID)
    ResponseEntity<?> delete(@PathVariable Long movieId) throws Exception {
        return new ResponseEntity<>(movieService.delete(movieId), HttpStatus.OK);
    }

    @PostMapping(Endpoints.ADD_CHARACTER)
    public ResponseEntity<?> addCharacter(
            @PathVariable Long movieId,
            @PathVariable Long characterId
    ) {
        return new ResponseEntity<>(movieService.addCharacter(movieId, characterId), HttpStatus.OK);
    }

    @DeleteMapping(Endpoints.ADD_CHARACTER)
    public ResponseEntity<?> deleteCharacter(
            @PathVariable Long movieId,
            @PathVariable Long characterId
    ) {
        return new ResponseEntity<>(movieService.deleteCharacter(movieId, characterId), HttpStatus.OK);
    }
}
