package com.mtx.disneyworld.dto;

import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.entity.Movie;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDto {

  private String image;
  private String title;
  private Calendar creationDate;
  //Rate from 0 to 5
  private float rate;
  private Set<CharacterDtoMin> characters;
  private Set<GenreDtoMin> genres;

  public MovieDto() {}

  public MovieDto(Movie m) {
    this.title = m.getTitle();
    this.image = m.getImage();
    this.creationDate = m.getCreationDate();
    this.rate = m.getRate();
    this.characters = new HashSet();
    m
      .getCharacters()
      .stream()
      .forEach(
        e -> {
          characters.add(new CharacterDtoMin(e));
        }
      );
    this.genres = new HashSet();
    m
      .getGenres()
      .stream()
      .forEach(
        e -> {
          genres.add(new GenreDtoMin(e));
        }
      );
  }

  public MovieDto(
    String image,
    String title,
    Calendar creationDate,
    float rating,
    Set<CharacterDtoMin> characters,
    Set<GenreDtoMin> genres
  ) {
    this.image = image;
    this.title = title;
    this.creationDate = creationDate;
    this.rate = rating;
    this.characters = characters;
    this.genres = genres;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Calendar getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Calendar creationDate) {
    this.creationDate = creationDate;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public Set<CharacterDtoMin> getCharacters() {
    return characters;
  }

  public void setCharacters(Set<CharacterDtoMin> chrDtos) {
    this.characters = chrDtos;
  }

  public Set<GenreDtoMin> getGenres() {
    return this.genres;
  }

  public void setGenres(Set<GenreDtoMin> genres) {
    this.genres = genres;
  }
}
