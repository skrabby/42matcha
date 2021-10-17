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
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> auth(@RequestBody LoginForm loginForm) {
        Map<String, String> response = new HashMap<String, String>() {{
            put("token", authService.authorize(loginForm.getUsername(), loginForm.getPassword()));
        }};
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
