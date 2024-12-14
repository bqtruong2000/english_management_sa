package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthFacade {

    @Autowired
    private AuthService authService;

    public String register(String userName, String password, String roleId) {
        Auth newUser = Auth.builder()
                .userName(userName)
                .password(password)
                .roleId(roleId)
                .build();
        return authService.register(newUser);
    }

    public Map<String, String> login(String userName, String password) {
        return authService.login(userName, password);
    }
}
