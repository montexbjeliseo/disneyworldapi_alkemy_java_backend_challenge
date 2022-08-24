package com.mtx.disneyworld.dto;

import com.mtx.disneyworld.entity.Genre;

public class GenreDtoDetails extends GenreDto {
    private Long id;
    public GenreDtoDetails(){

    }
    public GenreDtoDetails(Genre g){
        super(g);
        this.id = g.getId();
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
}