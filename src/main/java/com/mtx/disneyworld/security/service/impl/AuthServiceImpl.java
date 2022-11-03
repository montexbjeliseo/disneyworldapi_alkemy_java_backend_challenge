package com.mtx.disneyworld.security.service.impl;

import com.mtx.disneyworld.repository.RoleRepository;
import com.mtx.disneyworld.repository.UserRepository;
import com.mtx.disneyworld.security.dto.TokenInfo;
import com.mtx.disneyworld.security.dto.UserLoginDto;
import com.mtx.disneyworld.security.dto.UserRegisterDto;
import com.mtx.disneyworld.security.entity.Role;
import com.mtx.disneyworld.security.entity.User;
import com.mtx.disneyworld.security.mapper.UserMapper;
import com.mtx.disneyworld.security.service.IAuthService;
import com.mtx.disneyworld.service.impl.JwtUtilService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mtx.disneyworld.service.IEmailService;
import static com.mtx.disneyworld.util.Constants.Roles.USER;
import java.util.HashSet;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@Transactional
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private IEmailService sendGridEmailService;

    @Autowired
    private String sendGridEmail;

    @Override
    public TokenInfo register(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya se encuentra registrado");
        }

        User user = userMapper.toEntity(dto);
        user.setRoles(new HashSet<>());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPassword(bcrypt.encode(dto.getPassword()));
        user.getRoles().add(getDefaultRole());
        User saved = userRepository.save(user);
        return new TokenInfo(jwtUtilService.generateToken(saved));
    }

    private Role getDefaultRole() {
        return roleRepository.findByName(USER).get();
    }

    @Override
    public TokenInfo authenticate(UserLoginDto dto) {
        if (!userRepository.existsByEmail(dto.getUsername())) {
            throw new RuntimeException("El correo no se encuentra registrado");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
            return new TokenInfo(jwtUtilService.generateToken((UserDetails) auth.getPrincipal()));
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Bad credentials");
        }

    }

}
