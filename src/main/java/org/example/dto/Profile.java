package org.example.dto;

import org.example.enums.Role;
import org.example.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Profile {
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDateTime localDateTime;
    private Status status;
    private Role role;


}
