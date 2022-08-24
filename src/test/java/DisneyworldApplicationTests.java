package com.mtx.disneyworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mtx.disneyworld.repository.UserRepository;
import com.mtx.disneyworld.entity.User;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class DisneyworldApplicationTests {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(DisneyworldApplicationTests.class);


    
    public void crearUsuarioTest(){
        User user = new User();
        
        user.setName("admin1");
        user.setPassword(encoder.encode("1234"));
        user.setEmail("secondmtx");
        user.setRole("ADMIN");
        userRepo.save(user); 

        assertTrue(userRepo.findByName(user.getName()).get().getPassword().equalsIgnoreCase("1234"));
    }

    @Autowired
    String sendGridEmail;

    @Test
    public void sendEmail(){
        logger.info("Email: " + sendGridEmail, sendGridEmail);
        assertTrue(sendGridEmail.equalsIgnoreCase("secondmtx@gmail.com"));
    }
}