package fr.intra.controller;

import fr.intra.entity.User;
import fr.intra.service.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/")
@Transactional
public class RegController {
    private final RegService regService;

    @Autowired
    public RegController(RegService regService){
        this.regService  = regService;
    }

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody User user){
        return regService.sendConfirmEmail(user);
    }

    @GetMapping("confirm/{secret_key}/{email}")
    public ResponseEntity<?> confirmEmail(@PathVariable("secret_key") String secretKey,
                                          @PathVariable("email") String email) {
        return regService.confirmEmail(secretKey, email);
    }
}
