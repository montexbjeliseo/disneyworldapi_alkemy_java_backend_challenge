package com.mtx.disneyworld.dto;

public class GenreDtoMin {
    private String name;
    private String image;

    public GenreDtoMin(){

    }
    public GenreDtoMin(com.mtx.disneyworld.entity.Genre g){
        this.name = g.getName();
        this.image = g.getImage();
    }
    public GenreDtoMin(String name, String image){
        this.name = name;
        this.image = image;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
}