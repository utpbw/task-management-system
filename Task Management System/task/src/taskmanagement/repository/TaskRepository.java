package taskmanagement.repository;

import taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Returns all tasks sorted by id descending — newest task first
    List<Task> findAllByOrderByIdDesc();

    // Returns tasks by a specific author, case-insensitive, newest first.
    // Spring generates the SQL from the method name automatically.
    List<Task> findByAuthorIgnoreCaseOrderByIdDesc(String author);
}
