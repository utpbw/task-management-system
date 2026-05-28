package taskmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    // This method only ever executes for authenticated users.
    // Spring Security intercepts every incoming request to /api/tasks first —
    // if the Authorization header is missing or the credentials are wrong,
    // Security rejects the request with 401 before it even reaches this method.
    // That means you get the 401 behaviour for free, with zero extra code here.
    @GetMapping("/tasks")
    public ResponseEntity<List<Object>> getTasks() {
        // Returns an empty list for now — tasks are added in a later stage
        // of the course. The important thing at this stage is just that
        // authenticated users get a 200 and unauthenticated users get a 401.
        return ResponseEntity.ok(List.of());
    }
}
