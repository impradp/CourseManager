package com.company.coursemanager.auth.service;

import com.company.coursemanager.auth.helper.JwtService;
import com.company.coursemanager.auth.helper.UserInfoService;
import com.company.coursemanager.auth.model.AuthRequest;
import com.company.coursemanager.users.model.User;
import com.company.coursemanager.users.service.UserService;
import com.company.coursemanager.utils.exception.GlobalExceptionWrapper;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Authenticates the user provided credentials.
     *
     * @param authRequest The user provided credentials.
     * @return The token on validating the user.
     */
    public Map<String, String> authenticate(@NonNull AuthRequest authRequest) {
        Optional<User> selectedUser = userService.findByEmail(authRequest.getEmail());
        if (selectedUser.isEmpty() || !encoder.matches(authRequest.getPassword(), selectedUser.get().getPassword())) {
            throw new GlobalExceptionWrapper.NotFoundException("Invalid Credentials.");
        }
        return generateTokens(authRequest.getEmail());
    }

    /**
     * The updated access tokens from the refresh token provided.
     *
     * @param refreshToken The refresh tokens.
     * @return The map of access and refresh tokens.
     */
    public Map<String, String> refreshToken(String refreshToken) {
        // Check if token is a refresh token
        if (!isRefreshToken(refreshToken)) {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid Refresh Token.");
        }

        // Extract username from the refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Validate the refresh token
        UserDetails userDetails = userInfoService.loadUserByUsername(username);

        if (jwtService.validateToken(refreshToken, userDetails)) {
            Map<String, String> tokens = generateTokens(username);
            //Omit refreshing of refresh tokens
            tokens.put("refreshToken", refreshToken);
            return tokens;
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid or Expired Refresh Token.");
        }
    }

    /**
     * Additional method to check if the token is a refresh token
     *
     * @param token JWT token to check
     * @return boolean indicating if it's a refresh token
     */
    private boolean isRefreshToken(String token) {
        try {
            Claims claims = jwtService.extractAllClaims(token);
            // Check for a custom claim or another identifier for refresh tokens
            return claims.containsKey("type") && "refresh".equals(claims.get("type"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generates the token for provided username.
     *
     * @param username The username for the provided token.
     * @return The map of token types and tokens.
     */
    private Map<String, String> generateTokens(String username) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", jwtService.generateToken(username));
        tokenMap.put("refreshToken", jwtService.generateRefreshToken(username));
        return tokenMap;
    }

}
