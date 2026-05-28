package taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AssignRequest {

    @NotBlank(message = "Assignee must not be blank")
    @Pattern(
        regexp = "none|[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}",
        message = "Assignee must be 'none' or a valid email address"
    )
    private String assignee;

    public AssignRequest() {}

    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }
}
