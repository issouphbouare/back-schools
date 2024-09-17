package com.mas.school.jwtSwagger;

import java.util.Set;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Basic";
    private String username;
    private String email;
    private Set<Role> roles;

    public JwtResponse(String jwt, Long id, String username, String email, Set<Role> roles) {

        this.token=jwt;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
