package fr.intra.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatInfo {
    String  UserName;
    long    id;
}
