package fr.intra.user;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String name;
    private String email;
    private String gender;
    private String sexpref;
    private String biography;
    private ArrayList<String> tags;

    public User(){
    }

}
