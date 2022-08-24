package com.mtx.disneyworld.controller;

import com.mtx.disneyworld.dto.CharacterDto;
import com.mtx.disneyworld.entity.Character;
import com.mtx.disneyworld.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.mtx.disneyworld.service.MyService;
import java.util.Map;

import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    @Autowired
    MyService service;

    @GetMapping("/index")
    public String view(Model model){
        model.addAttribute("characterList", service.getAllCharacters());
        model.addAttribute("movieList", service.getAllMovies());
        model.addAttribute("genreList", service.getAllGenres());
        return "index";
    }    
    @GetMapping("/components/{name}")
    public String getComponent(@PathVariable String name){
        return "components/" + name + "/" + name;
    }
}
