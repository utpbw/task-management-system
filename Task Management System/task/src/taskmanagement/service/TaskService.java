package taskmanagement.service;

import taskmanagement.dto.TaskRequest;
import taskmanagement.model.Task;
import taskmanagement.model.TaskStatus;
import taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest request, String authorEmail) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.CREATED);
        task.setAuthor(authorEmail);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String author) {
        if (author != null && !author.isBlank()) {
            return taskRepository.findByAuthorIgnoreCaseOrderByIdDesc(author);
        }
        return taskRepository.findAllByOrderByIdDesc();
    }
}
