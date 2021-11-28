package fr.intra.controller;

import fr.intra.dto.LoginForm;
import fr.intra.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/")
@Transactional
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("auth")
    public ResponseEntity<Object> auth(@RequestBody LoginForm loginForm) {
        return new ResponseEntity<>(authService.authorize(loginForm.getUsername(),
                loginForm.getPassword()), HttpStatus.OK);
    }
}
