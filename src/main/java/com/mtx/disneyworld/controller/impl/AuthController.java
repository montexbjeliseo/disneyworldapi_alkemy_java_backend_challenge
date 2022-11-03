package com.mtx.disneyworld.controller.impl;

import com.mtx.disneyworld.security.dto.UserRegisterDto;
import com.mtx.disneyworld.security.dto.UserLoginDto;
import com.mtx.disneyworld.security.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private IAuthService service;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        return new ResponseEntity<>(service.authenticate(dto), HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
        return new ResponseEntity<>(service.register(dto), HttpStatus.OK);
    }
}
