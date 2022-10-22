package com.mtx.disneyworld.service.impl;

import com.mtx.disneyworld.dto.CharacterDto;
import com.mtx.disneyworld.dto.CharacterPostDto;
import com.mtx.disneyworld.repository.CharacterRepository;
import com.mtx.disneyworld.entity.Character;
import com.mtx.disneyworld.mapper.CharacterMapper;
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
public class CharacterServiceImpl {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterMapper characterMapper;

    public List<?> getAll(Map<String, String> params) {
        List<Character> allCharacters = characterRepository.findAll();

        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.AGE))) {
            //Filtrar por edad
            if (!params.get(Params.AGE).equals("")) {
                allCharacters
                        = allCharacters
                                .stream()
                                .filter(e -> e.getAge() == Integer.parseInt(params.get(Params.AGE)))
                                .collect(Collectors.toList());
            }
        }

        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.MOVIE))) {
            //Filtrar por pelicula
            if (!params.get(Params.MOVIE).equals("")) {
                Pattern reg = java.util.regex.Pattern.compile("^[0-9]");
                boolean valid = !Arrays
                        .asList(params.get(Params.MOVIE).split(""))
                        .stream()
                        .anyMatch(e -> !reg.asPredicate().test(e));

                if (valid) {
                    allCharacters
                            = allCharacters
                                    .stream()
                                    .filter(e -> e.hasMovie(Long.parseLong(params.get(Params.MOVIE))))
                                    .collect(Collectors.toList());
                }
            }
        }

        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.NAME))) {
            //Filtrar por nombre
            allCharacters
                    = allCharacters
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
                allCharacters.sort(
                        (a, b) -> a.getId().intValue() - b.getId().intValue()
                );
            } else if (orderBy.equals(Params.DESC) || orderBy.equals(Params.DESC_ASC)) {
                allCharacters.sort(
                        (a, b) -> b.getId().intValue() - a.getId().intValue()
                );
            }
        }
        if (params.keySet().stream().anyMatch(k -> k.trim().equals(Params.DETAILS))) {
            return characterMapper.toDtoList(allCharacters);
        } else {
            return characterMapper.toMinDtoList(allCharacters);
        }
    }

    public CharacterDto save(CharacterPostDto dto) {
        Character character = characterMapper.toEntity(dto);
        characterRepository.save(character);
        return characterMapper.toDto(character);
    }

    public CharacterDto update(Long id, CharacterPostDto dto) throws Exception {
        if (!characterRepository.existsById(id)) {
            throw new RuntimeException("No existe!");
        }
        Character character = characterRepository.findById(id).get();

        character = characterMapper.update(dto, character);

        characterRepository.save(character);

        return characterMapper.toDto(characterRepository.findById(id).get());
    }

    public CharacterDto delete(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new RuntimeException("No existe!");
        }
        Character found = characterRepository.findById(id).get();

        characterRepository.deleteById(id);

        return characterMapper.toDto(found);
    }
}
