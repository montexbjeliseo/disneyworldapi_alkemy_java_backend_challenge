package com.mtx.disneyworld.controller.impl;

import com.mtx.disneyworld.security.dto.TokenInfo;
import com.mtx.disneyworld.security.dto.UserRegisterDto;
import com.mtx.disneyworld.security.dto.UserLoginDto;
import com.mtx.disneyworld.service.impl.JwtUtilService;
import com.mtx.disneyworld.service.impl.UserService;
import com.mtx.disneyworld.dto.*;
import com.mtx.disneyworld.service.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class AuthController {

  @Autowired
  UserService service;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtilService jwtUtilService;

  @Autowired
  BCryptPasswordEncoder bcrypt;

  @Autowired 
  EmailService sendGridEmailService;

  @Autowired
    private String sendGridEmail;

  @PostMapping("/auth/login")
  public ResponseEntity<?> login(@RequestBody UserLoginDto myAuth) {
    try {
      Optional<com.mtx.disneyworld.security.entity.User> myUser = service.findByEmail(
      myAuth.getEmail()
    );
    if (!myUser.isPresent()) {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    TokenInfo info = signin(
      myUser.get().getName(),
      myUser.get().getEmail(),
      myUser.get().getPassword()
    );

    return new ResponseEntity<>(info, HttpStatus.OK);
    } catch(Exception e){
      return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }
    
  }

  @PostMapping("/auth/register")
  public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
    if (
      dto.getName().equals("") ||
      dto.getEmail().equals("") ||
      dto.getPassword().equals("")
    ) {
      return new ResponseEntity<>(
        "Error: Datos insuficientes",
        HttpStatus.BAD_REQUEST
      );
    }
    if (service.existsByName(dto.getName())) {
      return new ResponseEntity<>(
        "El nombre de usuario ya se encuentra en uso",
        HttpStatus.BAD_REQUEST
      );
    }
    if (service.existsByEmail(dto.getEmail())) {
      return new ResponseEntity<>(
        "El correo ya se encuentra en uso",
        HttpStatus.BAD_REQUEST
      );
    }
    com.mtx.disneyworld.security.entity.User user = new com.mtx.disneyworld.security.entity.User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(bcrypt.encode(dto.getPassword()));
    service.save(user);

    sendGridEmailService.sendHTML(sendGridEmail, dto.getEmail(), "Register confirmation", "Hello, " +dto.getName()+" <strong>how are you doing?<br>Thanks for register in DisneyworldApi.</strong>");

    TokenInfo info = signin(dto.getName(), dto.getEmail(), dto.getPassword());

    return new ResponseEntity<>(info, HttpStatus.OK);
  }

  private TokenInfo signin(String name, String email, String pass) {
    System.out.println("Here!");
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(name, pass)
    );
    final UserDetails userDetails = service.loadUserByEmail(email);
    final String jwt = jwtUtilService.generateToken(userDetails);
    return new TokenInfo(jwt);
  }
}
