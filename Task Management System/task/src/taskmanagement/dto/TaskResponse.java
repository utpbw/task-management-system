package taskmanagement.dto;

import taskmanagement.model.Task;

public class TaskResponse {

    // Returned as a String even though the DB stores it as a Long,
    // so the API doesn't expose database implementation details
    private String id;
    private String title;
    private String description;

    // The enum's .name() gives us "CREATED" as a plain String for JSON
    private String status;
    private String author;

    public TaskResponse() {}

    // Static factory — converts a Task entity into a TaskResponse DTO
    public static TaskResponse from(Task task) {
        TaskResponse response = new TaskResponse();
        response.id = String.valueOf(task.getId());
        response.title = task.getTitle();
        response.description = task.getDescription();
        response.status = task.getStatus().name();
        response.author = task.getAuthor();
        return response;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getAuthor() { return author; }
}
