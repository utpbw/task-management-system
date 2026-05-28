package taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import taskmanagement.model.Comment;

public class CommentResponse {

    private String id;

    @JsonProperty("task_id")
    private String taskId;

    private String text;
    private String author;

    public CommentResponse() {}

    public static CommentResponse from(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.id = String.valueOf(comment.getId());
        response.taskId = String.valueOf(comment.getTaskId());
        response.text = comment.getText();
        response.author = comment.getAuthor();
        return response;
    }

    public String getId() { return id; }

    @JsonProperty("task_id")
    public String getTaskId() { return taskId; }

    public String getText() { return text; }
    public String getAuthor() { return author; }
}
