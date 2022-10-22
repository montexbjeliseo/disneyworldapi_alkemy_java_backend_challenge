package com.mtx.disneyworld.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {
    private Long id;
    private String image;
    private String name;
    private int age;
    private float weight;
    private String story;
    private Set<MovieDtoMin> movies;
}
