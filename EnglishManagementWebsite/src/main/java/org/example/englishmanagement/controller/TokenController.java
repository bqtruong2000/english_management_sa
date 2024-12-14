package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.Token;
import org.example.englishmanagement.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<Token> createToken(@RequestParam String roleId, @RequestParam String userId) {
        Token token = tokenService.createToken(roleId, userId);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Token> getToken(@PathVariable String id) {
        return tokenService.getTokenById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToken(@PathVariable String id) {
        tokenService.deleteToken(id);
        return ResponseEntity.ok("Token deleted successfully.");
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String jwt) {
        boolean isValid = tokenService.validateToken(jwt);
        return ResponseEntity.ok(isValid);
    }
}
