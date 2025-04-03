package com.example.internintelligenceportfolioapi.controller;

import com.example.internintelligenceportfolioapi.model.input.ProjectDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ProjectDtoOutput;
import com.example.internintelligenceportfolioapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get")
    public List<ProjectDtoOutput> get() {
        return projectService.get();
    }

    @PostMapping("/add")
    public void add(@RequestBody ProjectDtoInput projectDtoInput) {
        projectService.add(projectDtoInput);
    }

    @PatchMapping("/update/name")
    public void updateName(@RequestParam("index") Integer index,
                           @RequestParam("newName") String newName) {
        projectService.updateName(index, newName);
    }

    @PatchMapping("/update/url")
    public void updateUrl(@RequestParam("index") Integer index,
                          @RequestParam("newUrl") String newUrl) {
        projectService.updateUrl(index, newUrl);
    }

    @PatchMapping("/update/about")
    public void updateAbout(@RequestParam("index") Integer index,
                            @RequestParam("newAbout") String newAbout) {
        projectService.updateAbout(index, newAbout);
    }

    @PatchMapping("/update/start/date")
    public void updateStartDate(@RequestParam("index") Integer index,
                                @RequestParam("newStartDate") LocalDate newStartDate) {
        projectService.updateStartDate(index, newStartDate);
    }

    @PatchMapping("/update/end/date")
    public void updateEndDate(@RequestParam("index") Integer index,
                              @RequestParam("newEndDate") LocalDate newEndDate) {
        projectService.updateEndDate(index, newEndDate);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("index") Integer index) {
        projectService.delete(index);
    }
}
