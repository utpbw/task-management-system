package taskmanagement.dto;

import taskmanagement.model.Task;

public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private String status;
    private String author;
    private String assignee;

    public TaskResponse() {}

    public static TaskResponse from(Task task) {
        TaskResponse response = new TaskResponse();
        response.id = String.valueOf(task.getId());
        response.title = task.getTitle();
        response.description = task.getDescription();
        response.status = task.getStatus().name();
        response.author = task.getAuthor();
        response.assignee = task.getAssignee() == null ? "none" : task.getAssignee();
        return response;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getAuthor() { return author; }
    public String getAssignee() { return assignee; }
}
