package fr.intra.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private String name;
    private String email;
    private String gender;
    private SexPref sexPref;
    private String biography;
    private List<Tag> tags;
    private List<Picture> pictures;

    public User(){
    }

}
