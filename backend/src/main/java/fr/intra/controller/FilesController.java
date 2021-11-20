package fr.intra.controller;

import java.util.List;
import java.util.stream.Collectors;

import fr.intra.entity.User;
import fr.intra.exception.JWTException;
import fr.intra.service.AuthService;
import fr.intra.service.FilesStorageService;
import fr.intra.util.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@Controller
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
            storageService.save(file, userID + "_" + pictureNum);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}