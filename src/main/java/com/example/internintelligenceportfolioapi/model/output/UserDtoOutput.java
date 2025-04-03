package com.example.internintelligenceportfolioapi.model.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDtoOutput {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private Set<String> skills;
}
