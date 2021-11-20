package fr.intra.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FilesStorageService {
    private final Path root;

    public FilesStorageService(){
        this.root = Paths.get("uploads");
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
        Path pathUrl = Paths.get("uploads/" + url);
        try {
            Files.createDirectory(pathUrl);
            return pathUrl;
        } catch (IOException e) {
            return pathUrl;
        }
    }

    public void save(MultipartFile file, String fileName){
        Path path = initUserDirectory(fileName.split("_")[0]);
        String[] exp = file.getOriginalFilename().split("\\.");
        try {
            Files.copy(file.getInputStream(), path.resolve(fileName +  "." + exp[exp.length - 1]));
        } catch (Exception e) {
            try {
                Files.delete(path.resolve(fileName + "." + exp[exp.length - 1]));
                Files.copy(file.getInputStream(), path.resolve(fileName + "." + exp[exp.length - 1]));
            }
            catch (IOException ex){
                return;
            }
        }
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

}
