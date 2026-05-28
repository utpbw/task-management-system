package taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank(message = "Text must not be blank")
    private String text;

    public CommentRequest() {}

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
