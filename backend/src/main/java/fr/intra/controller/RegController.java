package fr.intra.controller;

import fr.intra.entity.User;
import fr.intra.service.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView confirmEmail(@PathVariable("secret_key") String secretKey,
                                     @PathVariable("email") String email) {
        if (regService.confirmEmail(secretKey, email).equals(new ResponseEntity<>(HttpStatus.ACCEPTED)))
            return new RedirectView("http://localhost:8080/");
        return new RedirectView("http://localhost:8080/error");
    }
}
