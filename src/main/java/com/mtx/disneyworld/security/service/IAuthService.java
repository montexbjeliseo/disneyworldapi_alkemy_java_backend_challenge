package com.mtx.disneyworld.security.service;

import com.mtx.disneyworld.security.dto.TokenInfo;
import com.mtx.disneyworld.security.dto.UserLoginDto;
import com.mtx.disneyworld.security.dto.UserRegisterDto;

public interface IAuthService {
    TokenInfo register(UserRegisterDto dto);
    TokenInfo authenticate(UserLoginDto dto);
}
