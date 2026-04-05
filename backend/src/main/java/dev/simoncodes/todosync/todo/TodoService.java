package dev.simoncodes.todosync.todo;

import dev.simoncodes.todosync.entity.Todo;
import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.repository.TodoListRepository;
import dev.simoncodes.todosync.repository.TodoRepository;
import dev.simoncodes.todosync.todo.dto.CreateTodoRequest;
import dev.simoncodes.todosync.todo.dto.TodoResponse;
import dev.simoncodes.todosync.todo.dto.UpdateTodoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepo;
    private final TodoListRepository todoListRepo;
    private final String NOT_FOUND_ERROR = "Todo not found with provided id.";

    public List<TodoResponse> getTodosForUser(UUID userId, UUID listId) {
        if (listId == null) {
            return todoRepo.findAllByUserId(userId)
                    .stream()
                    .map(TodoResponse::from)
                    .toList();
        }
        return todoRepo.findAllByUserIdAndTodoListId(userId, listId)
                .stream()
                .map(TodoResponse::from)
                .toList();
    }

    public TodoResponse getSingleTodo(UUID userId, UUID todoId) {
        Todo todo = getTodoAndVerifyOwnership(userId, todoId);
        return TodoResponse.from(todo);
    }

    public TodoResponse createTodo(User user, CreateTodoRequest request, UUID listId) {

        Todo todo = new Todo()
                .withUser(user)
                .withTitle(request.title())
                .withDescription(request.description())
                .withPriority(request.priority())
                .withDueDate(request.dueDate())
                .withTodoList(listId == null ? null : todoListRepo.getReferenceById(listId));
        return TodoResponse.from(todoRepo.save(todo));
    }

    @SuppressWarnings("OptionalAssignedToNull")
    public TodoResponse updateTodo(UUID userId, UUID todoId, UpdateTodoRequest request) {
        Todo todo = getTodoAndVerifyOwnership(userId, todoId);
        if (request.title() != null) {
            todo.setTitle(request.title());
        }

        if (request.description() != null) {
            todo.setDescription(request.description().orElse(null));
        }
        if (request.dueDate() != null) {
            todo.setDueDate(request.dueDate().orElse(null));
        }
        if (request.isComplete() != null) {
            todo.setComplete(request.isComplete());
        }
        if (request.priority() != null) {
            todo.setPriority(request.priority());
        }
        return TodoResponse.from(todoRepo.save(todo));
    }

    public void deleteTodo(UUID userId, UUID todoId) {
        Todo todo = getTodoAndVerifyOwnership(userId, todoId);
        todoRepo.delete(todo);
    }

    private Todo getTodoAndVerifyOwnership(UUID userId, UUID todoId) {
        Todo todo = todoRepo.findById(todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR));
        if (!todo.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR);
        }
        return todo;
    }
}