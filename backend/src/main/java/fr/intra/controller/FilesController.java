package fr.intra.controller;

import fr.intra.exception.JWTException;
import fr.intra.service.AuthService;
import fr.intra.service.FilesStorageService;
import fr.intra.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;


@Controller
@RequestMapping("/")
@Transactional
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    AuthService  authService;

    @PutMapping("upload/{pictureNum}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestHeader("Token") String token,
                                        @PathVariable int pictureNum) {
        long userID;
        try {
            userID = authService.getUserId(token);
        } catch (JWTException ex) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        try {
            if (storageService.save(file, userID + "_" + pictureNum))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/picture/{pictureNum}")
    @ResponseBody
    public FileInfo getPicture(@RequestHeader("Token") String token,
                              @PathVariable int pictureNum) {
        long userID;
        try {
            userID = authService.getUserId(token);
        } catch (JWTException ex) {
            return null;
        }

        try{
            return new FileInfo(String.valueOf(userID), storageService.getPicture(userID, pictureNum));
        }  catch (Exception ex) {
            return null;
        }
    }

}
