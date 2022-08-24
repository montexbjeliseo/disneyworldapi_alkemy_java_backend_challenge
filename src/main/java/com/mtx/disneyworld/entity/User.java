package com.mtx.disneyworld.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String password;

  private String email;

  private String role;

  public User() {}

  public User(Long id, String name, String password, String email) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String n) {
    this.name = n;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String n) {
    this.password = n;
  }
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String n) {
    this.email = n;
  }
  public String getRole() {
    return this.role;
  }

  public void setRole(String n) {
    this.role = n;
  }
}
