package taskmanagement.repository;

import taskmanagement.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTaskIdOrderByIdDesc(Long taskId);

    // Single GROUP BY query — fetches counts for all task IDs at once
    // avoiding N+1 queries when building the task list response
    @Query("SELECT c.taskId, COUNT(c) FROM Comment c WHERE c.taskId IN :taskIds GROUP BY c.taskId")
    List<Object[]> countByTaskIdIn(@Param("taskIds") List<Long> taskIds);
}
