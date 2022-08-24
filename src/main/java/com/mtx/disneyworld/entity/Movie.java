package com.mtx.disneyworld.entity;

import com.mtx.disneyworld.dto.MovieDto;
import com.mtx.disneyworld.entity.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "movies")
@SuppressWarnings("PersistenceUnitPresent")
public class Movie implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @JoinColumn(name = "image")
  private String image;

  @JoinColumn(name = "title")
  private String title;

  @JoinColumn(name = "rate")
  private float rate;

  @JoinColumn(name = "creationDate")
  @Temporal(TemporalType.DATE)
  private Calendar creationDate;

  @ManyToMany(
    fetch = FetchType.LAZY,
    cascade = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  @JoinTable(
    name = "characters_movies",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "character_id")
  )
  private Set<Character> characters;

  @ManyToMany(
    fetch = FetchType.LAZY,
    cascade = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  @JoinTable(
    name = "genres_movies",
    joinColumns = @JoinColumn(name = "genre_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id")
  )
  private Set<Genre> genres;

  public Movie() {}

  public Movie(
    String image,
    String title,
    float rate,
    Calendar creationDate,
    Set<Character> chars
  ) {
    this.image = image;
    this.title = title;
    this.rate = rate;
    this.creationDate = creationDate;
    this.characters = chars;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public Calendar getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Calendar d) {
    this.creationDate = d;
  }

  public Set<Character> getCharacters() {
    return characters;
  }

  public void setCharacters(Set<Character> characters) {
    this.characters = characters;
  }

  public Set<Genre> getGenres() {
    return genres;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }

  public void addCharacter(Character c) {
    characters.add(c);
    c.getMovies().add(this);
  }

  public void removeCharacter(Character c) {
    characters.remove(c);
    c.getMovies().remove(this);
  }

  public void addGenre(Genre g) {
    genres.add(g);
    g.getMovies().add(this);
  }

  public void removeGenre(Genre g) {
    genres.remove(g);
    g.getMovies().remove(this);
  }
  public boolean hasGenre(Long id) {
    return genres.stream().anyMatch(m -> m.getId() == id);
  }
  public boolean hasCharacter(Long id) {
    return characters.stream().anyMatch(m -> m.getId() == id);
  }
}
