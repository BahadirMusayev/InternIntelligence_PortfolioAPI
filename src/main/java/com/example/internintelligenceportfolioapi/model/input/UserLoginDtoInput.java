package com.example.internintelligenceportfolioapi.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginDtoInput {
    private String email;
    private String password;
}
