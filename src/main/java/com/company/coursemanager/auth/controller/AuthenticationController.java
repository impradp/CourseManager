package com.company.coursemanager.auth.controller;

import com.company.coursemanager.auth.model.AuthRequest;
import com.company.coursemanager.auth.service.AuthenticationService;
import com.company.coursemanager.users.model.User;
import com.company.coursemanager.users.service.UserService;
import com.company.coursemanager.utils.RestHelper;
import com.company.coursemanager.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService loginService;

    @Autowired
    private UserService userService;

    /**
     * Handles the authentication for the user provided credentials.
     *
     * @param authRequest The authentication credentials containing object
     * @return The access keys and refresh keys for the associated authenticated user.
     */
    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> listHashMap = new HashMap<>(loginService.authenticate(authRequest));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Handles token refresh using a valid refresh token
     *
     * @param authorizationHeader Headers with Authorization keyword
     * @return New access and refresh tokens
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<RestResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Extract token from Bearer authorization header
        String refreshToken = authorizationHeader.substring(7); // Remove "Bearer "
        Map<String, Object> tokenMap = new HashMap<>(loginService.refreshToken(refreshToken));
        return RestHelper.responseSuccess(tokenMap);
    }

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }
}
