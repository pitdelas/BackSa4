package com.senai.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.back.dtos.token.LoginInput;
import com.senai.back.services.TokenService;


@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private TokenService service;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping()
    public String login(@RequestBody LoginInput input){
        var user = new UsernamePasswordAuthenticationToken(
            input.getEmail(), input.getPassword()     
        );
        var auth = manager.authenticate(user);
        var token = service.createToken((UserDetails) auth.getPrincipal());
        return token;
    }
}
