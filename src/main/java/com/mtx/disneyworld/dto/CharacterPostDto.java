package com.mtx.disneyworld.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterPostDto {
    private String image;
    private String name;
    private int age;
    private float weight;
    private String story;
}
