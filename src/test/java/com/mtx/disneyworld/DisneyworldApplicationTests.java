package com.mtx.disneyworld;

import org.springframework.beans.factory.annotation.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mtx.disneyworld.repository.UserRepository;
import com.mtx.disneyworld.security.entity.User;

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

    @Autowired
    String sendGridEmail;

    @Test
    public void sendEmail(){
        logger.info("Email: " + sendGridEmail, sendGridEmail);
        assertTrue(sendGridEmail.equalsIgnoreCase("secondmtx@gmail.com"));
    }
}