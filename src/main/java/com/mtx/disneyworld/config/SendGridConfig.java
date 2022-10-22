package com.mtx.disneyworld.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.sendgrid.SendGrid;

@Configuration
@PropertySource("classpath:application.properties")
public class SendGridConfig {
    @Bean
    @Value("${sendgrid.api.key}")
    public SendGrid sendGridClient(String sendGridAPIKey){
        return new SendGrid(sendGridAPIKey);
    }
    @Bean
    @Value("${sendgrid.email}")
    public String sendGridEmail(String email){
        return email;
    }
}