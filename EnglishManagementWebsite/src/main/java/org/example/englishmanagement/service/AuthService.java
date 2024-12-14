package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Auth;
import org.example.englishmanagement.repository.AuthRepository;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.englishmanagement.repository.AuthRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtUtil jwtUtil;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String register(Auth auth) {
        if (authRepository.findByUserName(auth.getUserName()) != null) {
            throw new RuntimeException("Username is already taken");
        }

        auth.setHPassword(passwordEncoder.encode(auth.getPassword()));
        auth.setDeleted(false);
        Auth savedAuth = authRepository.save(auth);

        return savedAuth.getId();
    }


    public Map<String, String> login(String userName, String password) {
        Auth existingUser = authRepository.findByUserName(userName);

        if (existingUser == null || existingUser.isDeleted()) {
            return Map.of("message", "Invalid username or password");
        }

        if (!passwordEncoder.matches(password, existingUser.getHPassword())) {
            return Map.of("message", "Invalid username or password");
        }

        String token = jwtUtil.generateToken(existingUser.getId(), existingUser.getRoleId());

        return Map.of("token", token, "roleId", existingUser.getRoleId());
    }

    public Iterable<Auth> getUsersByRole(String roleId) {
        return authRepository.findAllByRoleIdAndIsDeletedFalse(roleId);
    }
}
