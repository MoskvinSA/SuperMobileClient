package com.example.autoservice.data;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFormDto {

    private String username;

    private String password;

    private String fullName;

    private String phoneNumber;

    private String email;

}
