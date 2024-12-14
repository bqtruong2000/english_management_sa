package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Token;
import org.example.englishmanagement.repository.TokenRepository;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    private final JwtUtil jwtUtil = JwtUtil.getInstance();

    public Token createToken(String roleId, String userId) {
        String jwt = jwtUtil.generateToken(userId, roleId);

        Token token = Token.builder()
                .roleId(roleId)
                .userId(userId)
                .jwt(jwt)
                .build();

        return tokenRepository.save(token);
    }

    public Optional<Token> getTokenById(String id) {
        return tokenRepository.findById(id);
    }

    public void deleteToken(String id) {
        tokenRepository.deleteById(id);
    }

    public boolean validateToken(String jwt) {
        return jwtUtil.validateToken(jwt);
    }
}
