package com.mtx.disneyworld.entity;

import com.mtx.disneyworld.dto.MovieDto;
import com.mtx.disneyworld.entity.*;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @JoinColumn(name = "image")
  private String image;

  @JoinColumn(name = "name")
  private String name;

  @ManyToMany(
    fetch = FetchType.LAZY,
    cascade = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  @JoinTable(
    name = "genres_movies",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id")
  )
  private Set<Movie> movies;

  public Genre() {}

  public Genre(String image, String name, Set<Movie> movies) {
    this.image = image;
    this.name = name;
    this.movies = movies;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Movie> getMovies() {
    return movies;
  }

  public void setMovies(Set<Movie> movies) {
    this.movies = movies;
  }

  public void addMovie(Movie m) {
    movies.add(m);
    m.getGenres().add(this);
  }

  public void removeMovie(Movie m) {
    movies.remove(m);
    m.getGenres().remove(this);
  }
  public boolean hasMovie(Long id) {
    return movies.stream().anyMatch(m -> m.getId() == id);
  }
}
