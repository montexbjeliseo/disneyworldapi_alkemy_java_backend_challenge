package com.mtx.disneyworld.controller;

import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.entity.*;
import com.mtx.disneyworld.entity.Character;
import com.mtx.disneyworld.service.MyService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 *
 * @author: Montex BJ Eliseo
 */

@RestController
public class CharacterController {

  @Autowired
  MyService service;

  @GetMapping("/characters")
  ResponseEntity<?> getChars(@RequestParam Map<String, String> params) {
    try {
      List<Character> allOfTheChars = service.getAllCharacters();

      if (params.keySet().stream().anyMatch(k -> k.trim().equals("age"))) {
        //Filtrar por edad
        if (!params.get("age").equals("")) {
          allOfTheChars =
            allOfTheChars
              .stream()
              .filter(e -> e.getAge() == Integer.parseInt(params.get("age")))
              .collect(Collectors.toList());
        }
      }

      if (params.keySet().stream().anyMatch(k -> k.trim().equals("movie"))) {
        //Filtrar por pelicula
        if (!params.get("movie").equals("")) {
          Pattern reg = java.util.regex.Pattern.compile("^[0-9]");
          boolean valid = !Arrays
            .asList(params.get("movie").split(""))
            .stream()
            .anyMatch(e -> !reg.asPredicate().test(e));

          if (valid) {
            allOfTheChars =
              allOfTheChars
                .stream()
                .filter(e -> e.hasMovie(Long.parseLong(params.get("movie"))))
                .collect(Collectors.toList());
          }
        }
      }

      if (params.keySet().stream().anyMatch(k -> k.trim().equals("name"))) {
        //Filtrar por nombre
        allOfTheChars =
          allOfTheChars
            .stream()
            .filter(
              e ->
                e
                  .getName()
                  .toLowerCase()
                  .contains(params.get("name").toLowerCase())
            )
            .collect(Collectors.toList());
      }

      if (params.keySet().stream().anyMatch(k -> k.trim().equals("order"))) {
        //Ordenar
        String orderBy = params.get("order").toLowerCase();
        if (orderBy.equals("asc") || orderBy.equals("asc|desc")) {
          allOfTheChars.sort(
            (a, b) -> a.getId().intValue() - b.getId().intValue()
          );
        } else if (orderBy.equals("desc") || orderBy.equals("desc|asc")) {
          allOfTheChars.sort(
            (a, b) -> b.getId().intValue() - a.getId().intValue()
          );
        }
      }
      //Realizar la conversion

      if (params.keySet().stream().anyMatch(k -> k.trim().equals("details"))) {
        List<CharacterDtoDetails> list = new ArrayList<>();
        for (Character e : allOfTheChars) {
          list.add(new CharacterDtoDetails(e));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
      } else {
        List<CharacterDtoMin> list = new ArrayList<>();
        for (Character e : allOfTheChars) {
          list.add(new CharacterDtoMin(e));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
      }
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(e, HttpStatus.OK);
    }
  }

  @GetMapping("/characters/new")
  ResponseEntity<?> getNewCharacter() {
    CharacterDto newChar = new CharacterDto();
    return new ResponseEntity<>(newChar, HttpStatus.OK);
  }

  @DeleteMapping("/characters/{id}/delete")
  ResponseEntity<?> delete(@PathVariable Long id) {
    if (!service.existsCharacterById(id)) {
      return new ResponseEntity<>("No encontrado: " + id, HttpStatus.NOT_FOUND);
    }
    service.deleteCharacter(id);
    return new ResponseEntity<>("Se ha eliminado: " + id, HttpStatus.OK);
  }

  @PostMapping("/characters/create")
  public ResponseEntity<?> createCharacter(@RequestBody Character myCharDto) {
    try {
      if (myCharDto.getName() == null) {
        return new ResponseEntity<>(
          "El nombre es necesario!",
          HttpStatus.BAD_REQUEST
        );
      }
      if (service.existsCharacterByName(myCharDto.getName())) {
        return new ResponseEntity<>(
          "El nombre \"" + myCharDto.getName() + "\" ya está en uso!",
          HttpStatus.BAD_REQUEST
        );
      }
      myCharDto.setId(null);
      service.saveCharacter(myCharDto);
      return new ResponseEntity<>(
        service.getCharacterByName(myCharDto.getName()).get(),
        HttpStatus.CREATED
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/characters/update")
  public ResponseEntity<?> updateCharacter(@RequestBody Character myCharDto) {
    try {
      if (!service.existsCharacterById(myCharDto.getId())) {
        return new ResponseEntity<>(
          "El recurso no existe!",
          HttpStatus.NOT_FOUND
        );
      }

      Character updated = new Character();
      Character older = service.getCharacter(myCharDto.getId()).get();

      updated.setId(myCharDto.getId());
      updated.setName(
        myCharDto.getName() != null ? myCharDto.getName() : older.getName()
      );
      updated.setImage(
        myCharDto.getImage() != null ? myCharDto.getImage() : older.getImage()
      );
      updated.setAge(
        new Float(myCharDto.getAge()) != null
          ? myCharDto.getAge()
          : older.getAge()
      );
      updated.setWeight(
        new Float(myCharDto.getWeight()) != null
          ? myCharDto.getWeight()
          : older.getWeight()
      );
      updated.setStory(
        myCharDto.getStory() != null ? myCharDto.getStory() : older.getStory()
      );
      updated.setMovies(
        myCharDto.getMovies() != null
          ? myCharDto.getMovies()
          : older.getMovies()
      );

      service.saveCharacter(updated);
      return new ResponseEntity<>(
        service.getCharacterByName(myCharDto.getName()).get(),
        HttpStatus.OK
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
  }
}
