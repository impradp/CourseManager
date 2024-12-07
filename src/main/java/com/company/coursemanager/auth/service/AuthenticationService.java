package com.company.coursemanager.auth.service;

import com.company.coursemanager.auth.helper.JwtService;
import com.company.coursemanager.auth.helper.UserInfoService;
import com.company.coursemanager.auth.model.AuthRequest;
import com.company.coursemanager.utils.exception.GlobalExceptionWrapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {

    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates the user provided credentials.
     *
     * @param authRequest The user provided credentials.
     * @return The token on validating the user.
     */
    public HashMap<String, String> authenticate(@NonNull AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            HashMap<String, String> tokenMap = new HashMap<>();
            tokenMap.put("access_token", jwtService.generateToken(authRequest.getEmail()));
            tokenMap.put("refresh_token", jwtService.generateRefreshToken(authRequest.getEmail()));
            return tokenMap;
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid Credentials.");
        }
    }


}
