package com.mtx.disneyworld.dto;

import java.util.Calendar;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private Long id;
    private String image;
    private String title;
    private Calendar creationDate;
    //Rate from 0 to 5
    private float rate;
    private Set<CharacterDtoMin> characters;
    private Set<GenreDtoMin> genres;
}
