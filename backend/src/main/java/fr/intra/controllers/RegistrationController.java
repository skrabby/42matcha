package fr.intra.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.intra.requests.RegistrationRequest;

@RestController
@RequestMapping("/reg")
public class RegistrationController {

    @PostMapping("/setreg")
    public boolean registration(@RequestBody RegistrationRequest request){
        System.out.println(request.getFirstName());
        return true;
    }

}
