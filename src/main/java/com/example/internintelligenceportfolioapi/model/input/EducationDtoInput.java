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
public class EducationDtoInput {
    private String name;
    private String speciality;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
}
