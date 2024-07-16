package com.example.use.service;

import com.example.use.entity.AuthenticationResponse;
import com.example.use.entity.Users;
import com.example.use.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UsersRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(Users request) {
        Users user = new Users();
        user.setName(request.getName());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        repository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);


    }

    public AuthenticationResponse authenticate(Users request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( request.getEmail(), request.getPassword()));
        Users user=repository.findByEmail(request.getEmail());
        String token=jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
