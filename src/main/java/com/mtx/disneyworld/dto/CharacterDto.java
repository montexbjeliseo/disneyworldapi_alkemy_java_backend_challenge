package com.mtx.disneyworld.dto;

import java.util.Set;
import java.util.HashSet;

public class CharacterDto {
    private String image;
    private String name;
    private int age;
    private float weight;
    private String story;

    private Set<MovieDtoMin> movies;

    public CharacterDto() {

    }

    public CharacterDto(String bmp, String name, int age, float weight, String story, Set<MovieDtoMin> movies) {
        this.image = bmp;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.story = story;
        this.movies = movies;
    }

    public CharacterDto(com.mtx.disneyworld.entity.Character c){
        this.image = c.getImage();
        this.name = c.getName();
        this.age = c.getAge();
        this.weight = c.getWeight();
        this.story = c.getStory();
        this.movies = new HashSet();
	  c
		.getMovies()
		.stream()
		.forEach(
		  e -> {
			movies.add(new MovieDtoMin(e));
		  }
		);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String bmp) {
        this.image = bmp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Set<MovieDtoMin> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieDtoMin> movies) {
        this.movies = movies;
    }
}
