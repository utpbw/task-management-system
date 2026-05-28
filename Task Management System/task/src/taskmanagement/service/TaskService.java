package taskmanagement.service;

import taskmanagement.dto.TaskRequest;
import taskmanagement.dto.TaskResponse;
import taskmanagement.model.Task;
import taskmanagement.model.TaskStatus;
import taskmanagement.repository.CommentRepository;
import taskmanagement.repository.TaskRepository;
import taskmanagement.repository.UserAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserAccountRepository userAccountRepository;
    private final CommentRepository commentRepository;

    public TaskService(TaskRepository taskRepository,
                       UserAccountRepository userAccountRepository,
                       CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.userAccountRepository = userAccountRepository;
        this.commentRepository = commentRepository;
    }

    public Task createTask(TaskRequest request, String authorEmail) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.CREATED);
        task.setAuthor(authorEmail);
        return taskRepository.save(task);
    }

    public List<TaskResponse> getAllTasks(String author, String assignee) {
        boolean hasAuthor = author != null && !author.isBlank();
        boolean hasAssignee = assignee != null && !assignee.isBlank();

        List<Task> tasks;
        if (hasAuthor && hasAssignee) {
            tasks = taskRepository.findByAuthorIgnoreCaseAndAssigneeIgnoreCaseOrderByIdDesc(author, assignee);
        } else if (hasAuthor) {
            tasks = taskRepository.findByAuthorIgnoreCaseOrderByIdDesc(author);
        } else if (hasAssignee) {
            tasks = taskRepository.findByAssigneeIgnoreCaseOrderByIdDesc(assignee);
        } else {
            tasks = taskRepository.findAllByOrderByIdDesc();
        }

        if (tasks.isEmpty()) return List.of();

        List<Long> taskIds = tasks.stream().map(Task::getId).toList();
        Map<Long, Long> counts = commentRepository.countByTaskIdIn(taskIds)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        return tasks.stream()
                .map(t -> TaskResponse.from(t, counts.getOrDefault(t.getId(), 0L)))
                .toList();
    }

    public Task assignTask(Long taskId, String assigneeValue, String requesterEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        if (!task.getAuthor().equalsIgnoreCase(requesterEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the task author can assign tasks");
        }
        if ("none".equals(assigneeValue)) {
            task.setAssignee(null);
        } else {
            if (!userAccountRepository.existsByEmail(assigneeValue.toLowerCase())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            task.setAssignee(assigneeValue.toLowerCase());
        }
        return taskRepository.save(task);
    }

    public Task updateStatus(Long taskId, String newStatus, String requesterEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        boolean isAuthor = task.getAuthor().equalsIgnoreCase(requesterEmail);
        boolean isAssignee = task.getAssignee() != null
                && task.getAssignee().equalsIgnoreCase(requesterEmail);
        if (!isAuthor && !isAssignee) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only author or assignee can change status");
        }
        task.setStatus(TaskStatus.valueOf(newStatus));
        return taskRepository.save(task);
    }
}
