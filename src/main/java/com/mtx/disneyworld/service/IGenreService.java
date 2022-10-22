package com.mtx.disneyworld.service;

import com.mtx.disneyworld.dto.GenreDto;
import com.mtx.disneyworld.dto.GenrePostDto;
import java.util.List;
import java.util.Map;

public interface IGenreService {
    GenreDto save(GenrePostDto dto);
    GenreDto delete(Long id);
    List<?> getAll(Map<String, String> params);
    GenreDto update(Long id, GenrePostDto dto);
    GenreDto addMovie(Long id, Long movieId);
    GenreDto deleteMovie(Long id, Long movieId);
}
