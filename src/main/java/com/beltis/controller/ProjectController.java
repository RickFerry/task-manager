package com.beltis.controller;

import com.beltis.model.Project;
import com.beltis.repository.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    private Project project;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "projects/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        project = new Project();
        model.addAttribute("project", project);
        return "projects/create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/projects/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        project = projectRepository.findById(id);
        model.addAttribute("project", project);
        return "projects/edit";
    }

    @PostMapping("/edit")
    public String editProject(@ModelAttribute Project project) {
        projectRepository.update(project);
        return "redirect:/projects/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.delete(id);
        return "redirect:/projects/list";
    }
}
