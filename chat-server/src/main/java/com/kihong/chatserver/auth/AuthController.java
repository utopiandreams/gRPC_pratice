package com.kihong.chatserver.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<String> jwtLogin(String username, HttpServletResponse response) {
        String token = JwtUtil.generateToken(username, UUID.randomUUID().toString());
        Cookie tokenCookie = new Cookie("session", token);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(14 * 24 * 60 * 60);
        response.addCookie(tokenCookie);
        return ResponseEntity.ok("로그인 성공");
    }

}
