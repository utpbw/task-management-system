package taskmanagement.controller;

import taskmanagement.dto.AssignRequest;
import taskmanagement.dto.StatusRequest;
import taskmanagement.dto.TaskRequest;
import taskmanagement.dto.TaskResponse;
import taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
        var task = taskService.createTask(request, authentication.getName());
        return ResponseEntity.ok(TaskResponse.from(task));
    }

    @PutMapping("/tasks/{taskId}/assign")
    public ResponseEntity<TaskResponse> assignTask(
            @PathVariable Long taskId,
            @Valid @RequestBody AssignRequest request,
            Authentication authentication) {
        var task = taskService.assignTask(taskId, request.getAssignee(), authentication.getName());
        return ResponseEntity.ok(TaskResponse.from(task));
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody StatusRequest request,
            Authentication authentication) {
        var task = taskService.updateStatus(taskId, request.getStatus(), authentication.getName());
        return ResponseEntity.ok(TaskResponse.from(task));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String assignee) {
        var tasks = taskService.getAllTasks(author, assignee)
                .stream()
                .map(TaskResponse::from)
                .toList();
        return ResponseEntity.ok(tasks);
    }
}
