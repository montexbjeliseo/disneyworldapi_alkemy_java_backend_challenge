package com.mtx.disneyworld.dto;

import com.mtx.disneyworld.entity.Movie;

public class MovieDtoDetails extends MovieDto {

  private Long id;

  public MovieDtoDetails() {}

  public MovieDtoDetails(Movie m) {
    super(m);
    this.id = m.getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
