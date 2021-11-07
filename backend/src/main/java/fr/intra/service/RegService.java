package fr.intra.service;

import fr.intra.entity.User;
import fr.intra.repository.RegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegService {
    private final RegRepository regRepository;
    private final MailService mailService;

    @Autowired
    public RegService(RegRepository regRepository, MailService mailService){
        this.regRepository = regRepository;
        this.mailService = mailService;
    }

    public ResponseEntity<?> sendConfirmEmail(User user) {
        String secretKey = regRepository.insertEmailToReg(user);
        if (secretKey != null){
            mailService.sendConfirmEmail(secretKey, user.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> confirmEmail(String secretKey, String email) {
        return regRepository.confirmEmail(secretKey, email);
    }

}
