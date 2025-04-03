package com.example.internintelligenceportfolioapi.controller;

import com.example.internintelligenceportfolioapi.model.input.EducationDtoInput;
import com.example.internintelligenceportfolioapi.model.output.EducationDtoOutput;
import com.example.internintelligenceportfolioapi.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/education")
public class EducationController {

    private final EducationService educationService;

    @GetMapping("/get")
    public List<EducationDtoOutput> get() {
        return educationService.get();
    }

    @PostMapping("/add")
    public void add(@RequestBody EducationDtoInput educationDtoInput) {
        educationService.add(educationDtoInput);
    }

    @PatchMapping("/update/name")
    public void updateName(@RequestParam("index") Integer index,
                           @RequestParam("newName") String newName) {
        educationService.updateName(index, newName);
    }

    @PatchMapping("/update/speciality")
    public void updateSpeciality(@RequestParam("index") Integer index,
                                 @RequestParam("newSpeciality") String newSpeciality) {
        educationService.updateSpeciality(index, newSpeciality);
    }

    @PatchMapping("/update/degree")
    public void updateDegree(@RequestParam("index") Integer index,
                             @RequestParam("newDegree") String newDegree) {
        educationService.updateDegree(index, newDegree);
    }

    @PatchMapping("/update/start/date")
    public void updateStartDate(@RequestParam("index") Integer index,
                                @RequestParam("newStartDate") LocalDate newStartDate) {
        educationService.updateStartDate(index, newStartDate);
    }

    @PatchMapping("/update/end/date")
    public void updateEndDate(@RequestParam("index") Integer index,
                              @RequestParam("newEndDate") LocalDate newEndDate) {
        educationService.updateEndDate(index, newEndDate);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("index") Integer index) {
        educationService.delete(index);
    }
}
