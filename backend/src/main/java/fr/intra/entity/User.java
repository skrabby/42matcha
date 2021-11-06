package fr.intra.entity;

import lombok.*;

import java.util.ArrayList;

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
    private ArrayList<String> tags;
}
