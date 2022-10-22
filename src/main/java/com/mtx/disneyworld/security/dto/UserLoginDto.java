package com.mtx.disneyworld.security.dto;

import java.io.Serializable;

public class UserLoginDto implements Serializable {
    private String email;
    private String password;

    public UserLoginDto(){}
    public UserLoginDto(String e, String p){
        this.email = e;
        this.password = p;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String e){
        this.email = e;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String p){
        this.password = p;
    }
}