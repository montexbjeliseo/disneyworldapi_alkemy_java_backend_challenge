package com.mtx.disneyworld.service;

import com.mtx.disneyworld.dto.MovieDto;
import com.mtx.disneyworld.dto.MoviePostDto;
import java.util.List;
import java.util.Map;

public interface IMovieService {
    MovieDto save(MoviePostDto dto) throws Exception;
    MovieDto delete(Long id) throws Exception;
    MovieDto update(Long id, MoviePostDto dto) throws Exception;
    List<?> getAll(Map<String, String> params);
    MovieDto addCharacter(Long id, Long charId);
    MovieDto deleteCharacter(Long id, Long charId);
}
