//package fr.intra.controller;
//
//import fr.intra.service.DBService;
//import fr.intra.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//
//@RestController("Profile_Controller")
//public class ProfileController {
//
//    @Autowired
//    DBService dbService;
//
//    @PostMapping("/setProfile")
//    public ResponseEntity<?> setProfile(@RequestBody User user) {
//        try {
//            if (DBService.createClient(user.getName(), user.getEmail()))
//                return new ResponseEntity<>(HttpStatus.CREATED);
//            else
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PatchMapping("/id{id}/edit")
//    public ResponseEntity<?> updateProfile(@RequestBody User user, @PathVariable("id") String id) {
//        try {
//            if (DBService.updateClient(user, id))
//                return new ResponseEntity<>(HttpStatus.ACCEPTED);
//            else
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @RequestMapping(value = "id{id}/upload",
//            produces = { "application/json" },
//            consumes = { "multipart/form-data" },
//            method = RequestMethod.PUT)
//    public ResponseEntity<?>  savePicture(@Valid @RequestParam(value = "commit", required = false, defaultValue="false") Boolean commit,
//                                            @Valid @RequestPart("picture") MultipartFile file,
//                                            @PathVariable("id") String id){
//        try {
//            String resultFile = String.format("./usersPictures/%s/%s",id, file.getOriginalFilename());
//            File newFile = new File(resultFile);
//            File parent = newFile.getParentFile();
//            if (parent != null && !parent.exists() && !parent.mkdirs()) {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            // if file doesnt exists, then create it
//            if (!newFile.exists()) {
//                newFile.createNewFile();
//            }
//            Path path = Paths.get(resultFile);
//            Files.write(path, file.getBytes());
//            return new ResponseEntity<>(HttpStatus.ACCEPTED);
//        } catch (Exception ex){
//            ex.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//}
