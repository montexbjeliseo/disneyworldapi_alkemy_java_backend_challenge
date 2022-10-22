package com.mtx.disneyworld.service.impl;

import com.mtx.disneyworld.dto.GenreDto;
import com.mtx.disneyworld.dto.GenrePostDto;
import com.mtx.disneyworld.dto.MovieDto;
import com.mtx.disneyworld.entity.Genre;
import com.mtx.disneyworld.entity.Movie;
import com.mtx.disneyworld.mapper.GenreMapper;
import com.mtx.disneyworld.repository.GenreRepository;
import com.mtx.disneyworld.repository.MovieRepository;
import com.mtx.disneyworld.service.IGenreService;
import com.mtx.disneyworld.util.Constants.Params;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GenreServiceImpl implements IGenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreMapper genreMapper;

    public List<?> getAll(Map<String, String> params) {
        List<Genre> AllThegenres = genreRepository.findAll();
        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.NAME))) {
            //Filtrar por nombre
            AllThegenres
                    = AllThegenres
                            .stream()
                            .filter(
                                    e
                                    -> e
                                            .getName()
                                            .toLowerCase()
                                            .contains(params.get(Params.NAME).toLowerCase())
                            )
                            .collect(Collectors.toList());
        }

        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.ORDER))) {
            //Ordenar
            String orderBy = params.get(Params.ORDER).toLowerCase();
            if (orderBy.equals(Params.ASC) || orderBy.equals(Params.ASC_DESC)) {
                AllThegenres.sort(
                        (a, b) -> a.getId().intValue() - b.getId().intValue()
                );
            } else if (orderBy.equals(Params.DESC) || orderBy.equals(Params.DESC_ASC)) {
                AllThegenres.sort(
                        (a, b) -> b.getId().intValue() - a.getId().intValue()
                );
            }
        }

        //Realizar la conversion
        if (params.keySet().stream().anyMatch(k -> k.trim().equals("details"))) {
            return genreMapper.toDtoList(AllThegenres);
        } else {
            return genreMapper.toMinDtoList(AllThegenres);
        }
    }

    @Override
    public GenreDto save(GenrePostDto dto) {
        if (genreRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Ya existe un género con este nombre");
        }
        Genre saved = genreRepository.save(genreMapper.toEntity(dto));
        return genreMapper.toDto(saved);
    }

    public GenreDto update(Long id, GenrePostDto dto) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("No existe recurso con ese id");
        }

        Genre genre = genreRepository.findById(id).get();

        genre = genreMapper.update(dto, genre);

        return genreMapper.toDto(genre);
    }

    public GenreDto delete(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("No existe recurso con ese id");
        }
        Genre deleted = genreRepository.findById(id).get();

        genreRepository.deleteById(id);

        return genreMapper.toDto(deleted);
    }

    public GenreDto addMovie(Long id, Long movieId) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("No existe genero con ese id");
        }
        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("No existe peliculas con ese id");
        }

        Genre genre = genreRepository.findById(id).get();
        Movie movie = movieRepository.findById(movieId).get();

        if (genre.hasMovie(movie.getId())) {
            throw new RuntimeException("Ya existe personaje con ese id en la lista");
        }

        genre.getMovies().add(movie);
        genre = genreRepository.save(genre);
        return genreMapper.toDto(genre);
    }

    public GenreDto deleteMovie(Long id, Long movieId) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("No existe pelicula con ese id");
        }
        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("No existe personaje con ese id");
        }
        Genre genre = genreRepository.findById(id).get();
        Movie movie = movieRepository.findById(movieId).get();

        if (!genre.hasMovie(movieId)) {
            throw new RuntimeException("No existe personaje con ese id en la lista");
        }

        genre.getMovies().remove(movie);
        genre = genreRepository.save(genre);
        return genreMapper.toDto(genre);
    }

}
