package fr.intra.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String gender;
    private String orientation;
    private String description;
    private List<String> tags;
    private String birthday;
    private int popularity;
    private String latitude;
    private String longitude;
    private String avatar_url;
}
