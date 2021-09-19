package fr.intra.requests;

import lombok.Value;

@Value
public class RegistrationRequest {
    String FirstName;
    String LastName;
    boolean gender;
}
