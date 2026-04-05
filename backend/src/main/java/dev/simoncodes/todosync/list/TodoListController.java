package dev.simoncodes.todosync.list;

import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.id.IdTranslator;
import dev.simoncodes.todosync.list.dto.CreateTodoListRequest;
import dev.simoncodes.todosync.list.dto.TodoListDetailResponse;
import dev.simoncodes.todosync.list.dto.TodoListResponse;
import dev.simoncodes.todosync.list.dto.UpdateTodoListRequest;
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
@RequestMapping("/api/lists")
public class TodoListController {
    private final TodoListService todoListService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    public ResponseEntity<List<TodoListResponse>> getAll() {
        User user = authenticatedUserService.getCurrentUser();
        return ResponseEntity.ok(todoListService.getTodoListsForUser(user.getId()));
    }

    @GetMapping("/{listId}")
    public ResponseEntity<TodoListDetailResponse> getOne(@PathVariable String listId) {
        User user = authenticatedUserService.getCurrentUser();
        UUID todoListId = IdTranslator.decodeBase62ToUuid(listId);
        return ResponseEntity.ok(todoListService.getSingleTodoList(user.getId(), todoListId));
    }

    @PostMapping
    public ResponseEntity<TodoListResponse> createList(@Valid @RequestBody CreateTodoListRequest request) {
        User user = authenticatedUserService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(todoListService.createTodoList(user, request));
    }

    @PatchMapping("/{listId}")
    public ResponseEntity<TodoListResponse> updateList(@PathVariable String listId, @Valid @RequestBody UpdateTodoListRequest request) {
        User user = authenticatedUserService.getCurrentUser();
        UUID todoListId = IdTranslator.decodeBase62ToUuid(listId);
        return ResponseEntity.ok(todoListService.updateTodoList(user.getId(), todoListId, request));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable String listId) {
        User user = authenticatedUserService.getCurrentUser();
        UUID todoListId = IdTranslator.decodeBase62ToUuid(listId);
        todoListService.deleteTodoList(user.getId(), todoListId);
        return ResponseEntity.noContent().build();
    }
}
