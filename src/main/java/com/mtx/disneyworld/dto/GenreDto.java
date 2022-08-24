package com.mtx.disneyworld.dto;

import com.mtx.disneyworld.entity.*;

import java.util.Set;
import java.util.HashSet;

public class GenreDto {
	private String name;
	private String image;
	private Set<MovieDtoMin> movies;
	public GenreDto(){

	}
	public GenreDto(Genre g){
		this.name = g.getName();
		this.image = g.getImage();
		this.movies = new HashSet<MovieDtoMin>();
    g
      .getMovies()
      .stream()
      .forEach(
        e -> {
          movies.add(new MovieDtoMin(e));
        }
      );
	}

	public String getName(){
		return this.name;
	}
	public void setName(String n){
		this.name = n;
	}
	public String getImage(){
		return this.image;
	}
	public void setImage(String n){
		this.image = n;
	}
	public Set<MovieDtoMin> getMovies(){
		return this.movies;
	}
	public void setMovies(Set<MovieDtoMin> n){
		this.movies = n;
	}
}