package com.mtx.disneyworld.mapper;

import com.mtx.disneyworld.dto.MovieDto;
import com.mtx.disneyworld.dto.MovieDtoMin;
import com.mtx.disneyworld.dto.MoviePostDto;
import com.mtx.disneyworld.entity.Movie;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toDto(Movie c);

    Movie toEntity(MovieDto dto);
    
    Movie toEntity(MoviePostDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Movie update(MoviePostDto dto, @MappingTarget Movie character);
    
    List<MovieDto> toDtoList(List<Movie> movies);

    List<MovieDtoMin> toMinDtoList(List<Movie> movies);
}
