package com.mtx.disneyworld.mapper;

import com.mtx.disneyworld.dto.GenreDto;
import com.mtx.disneyworld.dto.GenreDtoMin;
import com.mtx.disneyworld.dto.GenrePostDto;
import com.mtx.disneyworld.entity.Genre;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genre c);
    Genre toEntity(GenreDto dto);
    Genre toEntity(GenrePostDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Genre update(GenrePostDto dto, @MappingTarget Genre character);
    List<GenreDto> toDtoList(List<Genre> list);
    List<GenreDtoMin> toMinDtoList(List<Genre> list);
}
