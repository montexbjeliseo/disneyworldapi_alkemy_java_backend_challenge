package com.mtx.disneyworld.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class UserRegisterDto implements Serializable {
    @Getter @Setter private String name;
    @Getter @Setter private String email;
    @Getter @Setter private String password;
    
    public UserRegisterDto(){

    }
    public UserRegisterDto(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}