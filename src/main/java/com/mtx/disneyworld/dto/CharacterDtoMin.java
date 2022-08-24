package com.mtx.disneyworld.dto;

public class CharacterDtoMin {
    private String name;
    private String image;

    public CharacterDtoMin(){

    }
    public CharacterDtoMin(com.mtx.disneyworld.entity.Character c){
        this.name = c.getName();
        this.image = c.getImage();
    }
    public CharacterDtoMin(String name, String image){
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