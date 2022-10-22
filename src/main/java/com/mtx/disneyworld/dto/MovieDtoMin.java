package com.mtx.disneyworld.dto;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDtoMin {

    private String title;
    private String image;
    private Calendar creationDate;
}
