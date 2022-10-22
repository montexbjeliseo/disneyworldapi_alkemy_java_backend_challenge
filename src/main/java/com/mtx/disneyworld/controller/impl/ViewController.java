package com.mtx.disneyworld.controller.impl;

import com.mtx.disneyworld.service.ICharacterService;
import com.mtx.disneyworld.service.IGenreService;
import com.mtx.disneyworld.service.IMovieService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    @Autowired
    ICharacterService characterService;
    @Autowired
    IMovieService movieService;
    @Autowired
    IGenreService genreService;

    @GetMapping("/index")
    public String view(Model model) {
        model.addAttribute("characterList", characterService.getAll(new HashMap<>()));
        model.addAttribute("movieList", movieService.getAll(new HashMap<>()));
        model.addAttribute("genreList", genreService.getAll(new HashMap<>()));
        return "index";
    }
}
