package com.example.internintelligenceportfolioapi.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegistrationDtoInput {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
}
