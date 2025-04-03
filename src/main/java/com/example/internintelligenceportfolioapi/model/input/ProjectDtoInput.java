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
public class ProjectDtoInput {
    private String name;
    private String url;
    private String about;
    private LocalDate startDate;
    private LocalDate endDate;
}
