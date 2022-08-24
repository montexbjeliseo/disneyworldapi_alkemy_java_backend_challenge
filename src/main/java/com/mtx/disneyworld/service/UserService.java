package com.mtx.disneyworld.service;

import org.springframework.security.core.userdetails.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mtx.disneyworld.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repo;
    
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        

        com.mtx.disneyworld.entity.User us = repo.findByName(name).get();
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		
		UserDetails details = new User(us.getName(), us.getPassword(), roles);

        return details;
    }

    public Optional<com.mtx.disneyworld.entity.User> findByEmail(String email){
        return repo.findByEmail(email);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        
        com.mtx.disneyworld.entity.User us = repo.findByEmail(email).get();
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		
		UserDetails details = new User(us.getName(), us.getPassword(), roles);

        //return details;

        return new User(us.getName(), us.getPassword(), roles);
    }

    public boolean existsByName(String name){
        return repo.existsByName(name);
    }
    public boolean existsByEmail(String email){
        return repo.existsByEmail(email);
    }

    public void save(com.mtx.disneyworld.entity.User u){
        repo.save(u);
    }
}