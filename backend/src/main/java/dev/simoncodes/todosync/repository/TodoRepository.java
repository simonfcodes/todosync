package dev.simoncodes.todosync.repository;

import dev.simoncodes.todosync.entity.Priority;
import dev.simoncodes.todosync.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findAllByPriorityAndUserId(Priority priority, UUID userId);
    List<Todo> findAllByCompleteTrueAndUserId(UUID userId);
    List<Todo> findAllByCompleteFalseAndUserId(UUID userId);
    List<Todo> findAllByDueDateAfterAndUserId(Instant timestamp, UUID userId);
    List<Todo> findAllByDueDateBeforeAndUserId(Instant timestamp, UUID userId);
    List<Todo> findAllByTodoListId(UUID todoListId);
}