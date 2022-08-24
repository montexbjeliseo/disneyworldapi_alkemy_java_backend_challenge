package com.mtx.disneyworld.entity;

import com.mtx.disneyworld.entity.*;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "characters")
@SuppressWarnings("PersistenceUnitPresent")
public class Character implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @JoinColumn(name = "image")
  private String image;

  @JoinColumn(name = "name")
  private String name;

  @JoinColumn(name = "age")
  private int age;

  @JoinColumn(name = "weight")
  private float weight;

  @JoinColumn(name = "story")
  private String story;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
  @JoinTable(
    name = "characters_movies",
    joinColumns = @JoinColumn(name = "character_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id")
  )
  private Set<Movie> movies;

  public Character() {
    super();
  }

  public Character(
    Long id,
    String image,
    String name,
    int age,
    float weight,
    String story,
    Set<Movie> movies
  ) {
    super();
    this.id = id;
    this.image = image;
    this.name = name;
    this.age = age;
    this.weight = weight;
    this.story = story;
    this.movies = movies;
  }

  public Character(
    String image,
    String name,
    int age,
    float weight,
    String story,
    Set<Movie> movies
  ) {
    this.image = image;
    this.name = name;
    this.age = age;
    this.weight = weight;
    this.story = story;
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

  public void setImage(String img) {
    this.image = img;
  }

  public String getName() {
    return name;
  }

  public void setName(String n) {
    this.name = n;
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

  public void setWeight(float f) {
    this.weight = f;
  }

  public String getStory() {
    return story;
  }

  public void setStory(String story) {
    this.story = story;
  }

  public Set<Movie> getMovies() {
    return movies;
  }

  public void setMovies(Set<Movie> movies) {
    this.movies = movies;
  }

  public boolean hasMovie(Long id) {
    return movies.stream().anyMatch(m -> m.getId() == id);
  }

  public void addMovie(Movie m) {
    movies.add(m);
    m.getCharacters().add(this);
  }

  public void removeMovie(Movie m) {
    movies.remove(m);
  }
}
