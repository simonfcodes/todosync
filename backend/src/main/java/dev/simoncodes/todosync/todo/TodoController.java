package dev.simoncodes.todosync.todo;

import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.id.IdTranslator;
import dev.simoncodes.todosync.todo.dto.CreateTodoRequest;
import dev.simoncodes.todosync.todo.dto.TodoResponse;
import dev.simoncodes.todosync.todo.dto.UpdateTodoRequest;
import dev.simoncodes.todosync.user.AuthenticatedUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAll(@RequestParam(required = false) String listId) {
        User user = authenticatedUserService.getCurrentUser();
        UUID todoListId = IdTranslator.decodeBase62ToUuid(listId);
        return ResponseEntity.ok(todoService.getTodosForUser(user.getId(), todoListId));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getOne(@PathVariable String todoId) {
        User user = authenticatedUserService.getCurrentUser();
        UUID id = IdTranslator.decodeBase62ToUuid(todoId);
        return ResponseEntity.ok(todoService.getSingleTodo(user.getId(), id));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        User user = authenticatedUserService.getCurrentUser();
        UUID todoListId = IdTranslator.decodeBase62ToUuid(request.listId());
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(user, request, todoListId));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable String todoId, @Valid @RequestBody UpdateTodoRequest request) {
        User user = authenticatedUserService.getCurrentUser();
        UUID id = IdTranslator.decodeBase62ToUuid(todoId);
        return ResponseEntity.ok(todoService.updateTodo(user.getId(), id, request));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String todoId) {
        User user = authenticatedUserService.getCurrentUser();
        UUID id = IdTranslator.decodeBase62ToUuid(todoId);
        todoService.deleteTodo(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
