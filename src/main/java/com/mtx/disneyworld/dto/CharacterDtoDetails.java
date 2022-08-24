package com.mtx.disneyworld.dto;

public class CharacterDtoDetails extends CharacterDto {

  private Long id;

  public CharacterDtoDetails() {}

  public CharacterDtoDetails(com.mtx.disneyworld.entity.Character c) {
    super(c);
    this.id = c.getId();
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
