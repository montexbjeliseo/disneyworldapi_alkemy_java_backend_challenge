package com.mtx.disneyworld.dto;

import com.mtx.disneyworld.entity.*;
import java.util.Calendar;

public class MovieDtoMin {

  private String title;
  private String image;
  private Calendar creationDate;

  public MovieDtoMin() {}

  public MovieDtoMin(Movie m) {
    this.title = m.getTitle();
    this.image = m.getImage();
    this.creationDate = m.getCreationDate();
  }

  public MovieDtoMin(String title, String image, Calendar cd) {
    this.title = title;
    this.image = image;
    this.creationDate = cd;
  }

  public void setTitle(String name) {
    this.title = name;
  }

  public String getTitle() {
    return this.title;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getImage() {
    return this.image;
  }

  public Calendar getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(Calendar d) {
    this.creationDate = d;
  }
}
