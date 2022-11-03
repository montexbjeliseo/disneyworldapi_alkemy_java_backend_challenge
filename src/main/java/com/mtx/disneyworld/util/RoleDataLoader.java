package com.mtx.disneyworld.util;

import com.mtx.disneyworld.repository.RoleRepository;
import com.mtx.disneyworld.security.entity.Role;
import static com.mtx.disneyworld.util.Constants.Roles.ADMIN;
import static com.mtx.disneyworld.util.Constants.Roles.ADMIN_DESCRIPTION;
import static com.mtx.disneyworld.util.Constants.Roles.USER;
import static com.mtx.disneyworld.util.Constants.Roles.USER_DESCRIPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
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

}
