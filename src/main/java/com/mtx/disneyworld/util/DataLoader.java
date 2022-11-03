package com.mtx.disneyworld.util;

import com.mtx.disneyworld.repository.RoleRepository;
import com.mtx.disneyworld.repository.UserRepository;
import com.mtx.disneyworld.security.entity.Role;
import com.mtx.disneyworld.security.entity.User;
import static com.mtx.disneyworld.util.Constants.DefaultData.ADMIN_EMAIL;
import static com.mtx.disneyworld.util.Constants.DefaultData.USER_EMAIL;
import static com.mtx.disneyworld.util.Constants.Roles.ADMIN;
import static com.mtx.disneyworld.util.Constants.Roles.ADMIN_DESCRIPTION;
import static com.mtx.disneyworld.util.Constants.Roles.USER;
import static com.mtx.disneyworld.util.Constants.Roles.USER_DESCRIPTION;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bcrypt;
    
    @Override
    public void run(String... args) throws Exception {
        checkRoles();
        checkUsers();
    }
    
    private void checkRoles(){
        if(!roleRepository.existsByName(ADMIN)){
            Role admin = new Role();
            admin.setName(ADMIN);
            admin.setDescription(ADMIN_DESCRIPTION);
            roleRepository.save(admin);
        }
        if(!roleRepository.existsByName(USER)){
            Role user = new Role();
            user.setName(USER);
            user.setDescription(USER_DESCRIPTION);
            roleRepository.save(user);
        }
    }
    
    private void checkUsers(){
        if(!userRepository.existsByEmail(ADMIN)){
            User user = new User();
            user.setFirstName(ADMIN);
            user.setLastName(ADMIN);
            user.setRoles(new HashSet<>());
            user.getRoles().add(roleRepository.findByName(ADMIN).get());
            user.setEmail(ADMIN_EMAIL);
            user.setPassword(bcrypt.encode("1234"));
            userRepository.save(user);
        }
        if(!userRepository.existsByEmail(USER)){
            User user = new User();
            user.setFirstName(USER);
            user.setLastName(USER);
            user.setRoles(new HashSet<>());
            user.getRoles().add(roleRepository.findByName(USER).get());
            user.setEmail(USER_EMAIL);
            user.setPassword(bcrypt.encode("1234"));
            userRepository.save(user);
        }
    }
}
