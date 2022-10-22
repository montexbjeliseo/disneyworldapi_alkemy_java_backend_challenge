package com.mtx.disneyworld.controller;

import com.mtx.disneyworld.dto.CharacterPostDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface ICharacterController {
    ResponseEntity<?> getNew();
    ResponseEntity<?> getAll(Map<String, String> params);
    ResponseEntity<?> save(CharacterPostDto dto);
    ResponseEntity<?> update(Long id, CharacterPostDto dto);
    ResponseEntity<?> delete(Long id);
}
