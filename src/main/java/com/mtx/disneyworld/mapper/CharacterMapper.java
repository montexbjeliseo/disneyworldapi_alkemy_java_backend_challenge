package com.mtx.disneyworld.mapper;

import com.mtx.disneyworld.dto.CharacterDto;
import com.mtx.disneyworld.dto.CharacterDtoMin;
import com.mtx.disneyworld.dto.CharacterPostDto;
import com.mtx.disneyworld.entity.Character;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDto toDto(Character c);

    Character toEntity(CharacterDto dto);
    
    Character toEntity(CharacterPostDto dto);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Character update(CharacterPostDto dto, @MappingTarget Character character);

    List<CharacterDto> toDtoList(List<Character> list);

    List<CharacterDtoMin> toMinDtoList(List<Character> list);
}
