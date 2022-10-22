package com.mtx.disneyworld.service;

import com.mtx.disneyworld.dto.CharacterDto;
import com.mtx.disneyworld.dto.CharacterPostDto;
import java.util.List;
import java.util.Map;

public interface ICharacterService {
    CharacterDto save(CharacterPostDto dto);
    CharacterDto delete(Long id);
    CharacterDto update(Long id, CharacterPostDto dto);
    List<?> getAll(Map<String, String> params);
}
