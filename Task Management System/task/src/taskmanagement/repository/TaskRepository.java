package taskmanagement.repository;

import taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOrderByIdDesc();
    List<Task> findByAuthorIgnoreCaseOrderByIdDesc(String author);
    List<Task> findByAssigneeIgnoreCaseOrderByIdDesc(String assignee);
    List<Task> findByAuthorIgnoreCaseAndAssigneeIgnoreCaseOrderByIdDesc(String author, String assignee);
}
