package taskmanagement.controller;

import taskmanagement.dto.CommentRequest;
import taskmanagement.dto.CommentResponse;
import taskmanagement.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<Void> addComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication) {
        commentService.addComment(taskId, request.getText(), authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long taskId) {
        var comments = commentService.getComments(taskId)
                .stream()
                .map(CommentResponse::from)
                .toList();
        return ResponseEntity.ok(comments);
    }
}
