package taskmanagement.service;

import taskmanagement.model.Comment;
import taskmanagement.repository.CommentRepository;
import taskmanagement.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository,
                          TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public void addComment(Long taskId, String text, String authorEmail) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setText(text);
        comment.setAuthor(authorEmail);
        commentRepository.save(comment);
    }

    public List<Comment> getComments(Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return commentRepository.findByTaskIdOrderByIdDesc(taskId);
    }
}
