package com.mtx.disneyworld.service.impl;

import com.mtx.disneyworld.dto.MovieDto;
import com.mtx.disneyworld.dto.MoviePostDto;
import com.mtx.disneyworld.entity.Character;
import com.mtx.disneyworld.entity.Movie;
import com.mtx.disneyworld.mapper.MovieMapper;
import com.mtx.disneyworld.repository.CharacterRepository;
import com.mtx.disneyworld.repository.MovieRepository;
import com.mtx.disneyworld.service.IMovieService;
import com.mtx.disneyworld.util.Constants.Params;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieMapper movieMapper;

    public List<?> getAll(Map<String, String> params) {
        List<Movie> AllTheMovies = movieRepository.findAll();
        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.GENRE))) {
            //Filtrar por pelicula
            if (!params.get(Params.GENRE).equals("")) {
                Pattern reg = java.util.regex.Pattern.compile("^[0-9]");
                boolean valid = !Arrays
                        .asList(params.get(Params.GENRE).split(""))
                        .stream()
                        .anyMatch(e -> !reg.asPredicate().test(e));

                if (valid) {
                    AllTheMovies
                            = AllTheMovies
                                    .stream()
                                    .filter(e -> e.hasGenre(Long.parseLong(params.get(Params.GENRE))))
                                    .collect(Collectors.toList());
                }
            }
        }

        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.NAME))) {
            //Filtrar por nombre
            AllTheMovies
                    = AllTheMovies
                            .stream()
                            .filter(
                                    e
                                    -> e
                                            .getTitle()
                                            .toLowerCase()
                                            .contains(params.get(Params.NAME).toLowerCase())
                            )
                            .collect(Collectors.toList());
        }

        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.ORDER))) {
            //Ordenar
            String orderBy = params.get(Params.ORDER).toLowerCase();
            if (orderBy.equals(Params.ASC) || orderBy.equals(Params.ASC_DESC)) {
                AllTheMovies.sort(
                        (a, b) -> a.getId().intValue() - b.getId().intValue()
                );
            } else if (orderBy.equals(Params.DESC) || orderBy.equals(Params.DESC_ASC)) {
                AllTheMovies.sort(
                        (a, b) -> b.getId().intValue() - a.getId().intValue()
                );
            }
        }

        //Realizar la conversion
        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.DETAILS))) {

            return movieMapper.toDtoList(AllTheMovies);
        } else {
            return movieMapper.toMinDtoList(AllTheMovies);
        }

    }

    public MovieDto save(MoviePostDto dto) throws Exception {
        if (movieRepository.existsByTitle(dto.getTitle())) {
            throw new RuntimeException("Ya existe este título");
        }
        Movie saved = movieRepository.save(movieMapper.toEntity(dto));
        return movieMapper.toDto(saved);
    }

    public MovieDto update(Long id, MoviePostDto dto) throws Exception {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("No existe recurso con ese id");
        }

        Movie movie = movieRepository.findById(id).get();

        movie = movieMapper.update(dto, movie);

        return movieMapper.toDto(movie);
    }

    public MovieDto delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("No existe recurso con ese id");
        }
        Movie deleted = movieRepository.findById(id).get();

        movieRepository.deleteById(id);

        return movieMapper.toDto(deleted);
    }

    public MovieDto addCharacter(Long movieId, Long characterId) {
        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("No existe pelicula con ese id");
        }
        if (!characterRepository.existsById(characterId)) {
            throw new RuntimeException("No existe personaje con ese id");
        }

        Movie movie = movieRepository.findById(movieId).get();
        Character character = characterRepository.findById(characterId).get();

        if (movie.hasCharacter(character.getId())) {
            throw new RuntimeException("Ya existe personaje con ese id en la lista");
        }

        movie.getCharacters().add(character);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    public MovieDto deleteCharacter(Long movieId, Long characterId) {
        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("No existe pelicula con ese id");
        }
        if (!characterRepository.existsById(characterId)) {
            throw new RuntimeException("No existe personaje con ese id");
        }

        Movie movie = movieRepository.findById(movieId).get();
        Character character = characterRepository.findById(characterId).get();

        if (!movie.hasCharacter(character.getId())) {
            throw new RuntimeException("No existe personaje con ese id en la lista");
        }

        movie.getCharacters().remove(character);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

}
