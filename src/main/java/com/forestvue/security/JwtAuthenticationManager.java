package com.forestvue.security;

import com.forestvue.exception.JwtTokenMalformedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
    @Autowired
    private JwtGenerator jwtGenerator;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = (String) jwtAuthenticationToken.getPrincipal();

        AuthenticatedUser user = jwtGenerator.parseToken(token);
        if (user == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        } else if (!user.isAuthenticated()) {
            throw new JwtTokenMalformedException("required field is wrong.");
        }

        return user;
    }
}
