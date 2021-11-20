package fr.intra.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FilesStorageService {
    private final Path root;

    @Autowired
    PicturesService picturesService;

    public FilesStorageService(PicturesService picturesService){
        this.picturesService = picturesService;
        this.root = Paths.get(picturesService.getDestination());
        this.init();
    }


    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            return;
        }
    }

    public Path initUserDirectory(String url){
        Path pathUrl = Paths.get(picturesService.getDestination() + url);
        try {
            Files.createDirectory(pathUrl);
            return pathUrl;
        } catch (IOException e) {
            return pathUrl;
        }
    }

    public boolean save(MultipartFile file, String fileName) {
        String id = fileName.split("_")[0];
        String pictureNum = fileName.split("_")[1];
        Path path = initUserDirectory(id);
        String[] exp = file.getOriginalFilename().split("\\.");
        try {
            Files.copy(file.getInputStream(), path.resolve(fileName + "." + exp[exp.length - 1]));
            if (!(picturesService.savePictureToDB(id, pictureNum, exp[exp.length - 1]))) {
                Files.delete(path.resolve(fileName + "." + exp[exp.length - 1]));
                return false;
            }
            return true;
        } catch (Exception e) {
            try {
                Files.delete(path.resolve(fileName + "." + exp[exp.length - 1]));
                Files.copy(file.getInputStream(), path.resolve(fileName + "." + exp[exp.length - 1]));
                if (!(picturesService.savePictureToDB(id, pictureNum, exp[exp.length - 1]))) {
                    Files.delete(path.resolve(fileName + "." + exp[exp.length - 1]));
                    return false;
                }
            } catch (IOException ex) {
                return false;
            }
        }
        return true;
    }


    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public String getPicture(long userID, int pictureNum) {
        return picturesService.getPicture(userID, pictureNum);
    }
}
