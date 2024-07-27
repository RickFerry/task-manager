package com.beltis.controller;

import com.beltis.model.Project;
import com.beltis.model.Task;
import com.beltis.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    private Task task;
    private Long projectId;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/list/{projectId}")
    public String list(@PathVariable Long projectId, Model model) {
        this.projectId = projectId;
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        task = new Task();
        task.setProject(new Project()); // Initialize with a new project
        model.addAttribute("task", task);
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        task.getProject().setId(projectId); // Ensure task is associated with the correct project
        taskRepository.save(task);
        return "redirect:/tasks/list/" + projectId;
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        task = taskRepository.findById(id);
        model.addAttribute("task", task);
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task) {
        taskRepository.update(task);
        return "redirect:/tasks/list/" + task.getProject().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id);
        Long projectId = task.getProject().getId();
        taskRepository.delete(id);
        return "redirect:/tasks/list/" + projectId;
    }
}
