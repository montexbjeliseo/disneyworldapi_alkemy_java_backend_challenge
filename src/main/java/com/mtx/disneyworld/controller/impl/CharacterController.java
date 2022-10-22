package com.mtx.disneyworld.controller.impl;

import com.mtx.disneyworld.controller.ICharacterController;
import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.service.ICharacterService;
import com.mtx.disneyworld.util.Constants.Endpoints;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoints.CHARACTER)
public class CharacterController implements ICharacterController {

    @Autowired
    ICharacterService characterService;

    @Override
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(characterService.getAll(params), HttpStatus.OK);
    }

    @Override
    @GetMapping(Endpoints.NEW)
    public ResponseEntity<?> getNew() {
        return new ResponseEntity<>(new CharacterDto(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(Endpoints.CHARACTERID)
    public ResponseEntity<?> delete(@PathVariable Long characterId) {
        return new ResponseEntity<>(characterService.delete(characterId), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CharacterPostDto dto) {
        try {
            return new ResponseEntity<>(
                    characterService.save(dto),
                    HttpStatus.CREATED
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PatchMapping(Endpoints.CHARACTERID)
    public ResponseEntity<?> update(@PathVariable Long characterId, @RequestBody CharacterPostDto dto) {
        try {
            return new ResponseEntity<>(
                    characterService.update(characterId, dto),
                    HttpStatus.OK
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
