package taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class StatusRequest {

    @NotBlank(message = "Status must not be blank")
    @Pattern(
        regexp = "CREATED|IN_PROGRESS|COMPLETED",
        message = "Status must be CREATED, IN_PROGRESS, or COMPLETED"
    )
    private String status;

    public StatusRequest() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
