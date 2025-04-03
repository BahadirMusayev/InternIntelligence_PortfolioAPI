package com.example.internintelligenceportfolioapi.controller;

import com.example.internintelligenceportfolioapi.model.input.ExperienceDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ExperienceDtoOutput;
import com.example.internintelligenceportfolioapi.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/get")
    public List<ExperienceDtoOutput> get() {
        return experienceService.get();
    }

    @PostMapping("/add")
    public void add(@RequestBody ExperienceDtoInput experienceDtoInput) {
        experienceService.add(experienceDtoInput);
    }

    @PatchMapping("/update/work/place")
    public void updateWorkPlace(@RequestParam("index") Integer index,
                           @RequestParam("newWorkPlace") String newWorkPlace) {
        experienceService.updateWorkPlace(index, newWorkPlace);
    }

    @PatchMapping("/update/job/title")
    public void updateJobTitle(@RequestParam("index") Integer index,
                                 @RequestParam("newJobTitle") String newJobTitle) {
        experienceService.updateJobTitle(index, newJobTitle);
    }

    @PatchMapping("/update/start/date")
    public void updateStartDate(@RequestParam("index") Integer index,
                                @RequestParam("newStartDate") LocalDate newStartDate) {
        experienceService.updateStartDate(index, newStartDate);
    }

    @PatchMapping("/update/end/date")
    public void updateEndDate(@RequestParam("index") Integer index,
                              @RequestParam("newEndDate") LocalDate newEndDate) {
        experienceService.updateEndDate(index, newEndDate);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("index") Integer index) {
        experienceService.delete(index);
    }
}
