package taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import taskmanagement.model.Task;

public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private String status;
    private String author;
    private String assignee;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total_comments")
    private Long totalComments;

    public TaskResponse() {}

    public static TaskResponse from(Task task) {
        TaskResponse r = new TaskResponse();
        r.id = String.valueOf(task.getId());
        r.title = task.getTitle();
        r.description = task.getDescription();
        r.status = task.getStatus().name();
        r.author = task.getAuthor();
        r.assignee = task.getAssignee() == null ? "none" : task.getAssignee();
        // totalComments left null — omitted from JSON
        return r;
    }

    public static TaskResponse from(Task task, long commentCount) {
        TaskResponse r = from(task);
        r.totalComments = commentCount; // present in JSON even when 0
        return r;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getAuthor() { return author; }
    public String getAssignee() { return assignee; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total_comments")
    public Long getTotalComments() { return totalComments; }
}
