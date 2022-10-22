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
public class GenreDto {

    private String name;
    private String image;
    private Set<MovieDtoMin> movies;
}
