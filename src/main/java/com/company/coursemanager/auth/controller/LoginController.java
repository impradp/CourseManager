package com.company.coursemanager.auth.controller;

import com.company.coursemanager.auth.model.AuthRequest;
import com.company.coursemanager.auth.service.LoginService;
import com.company.coursemanager.utils.RestHelper;
import com.company.coursemanager.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Handles the authentication for the user provided credentials.
     *
     * @param authRequest The authentication credentials containing object
     * @return The access keys and refresh keys for the associated authenticated user.
     */
    @PostMapping
    public ResponseEntity<RestResponse> login(@RequestBody AuthRequest authRequest) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("access_token", loginService.authenticate(authRequest));
        return RestHelper.responseSuccess(listHashMap);
    }
}
