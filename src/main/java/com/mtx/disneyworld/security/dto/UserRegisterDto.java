package com.mtx.disneyworld.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
