package dev.simoncodes.todosync.list;

import dev.simoncodes.todosync.entity.Todo;
import dev.simoncodes.todosync.entity.TodoList;
import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.list.dto.*;
import dev.simoncodes.todosync.repository.TodoListRepository;
import dev.simoncodes.todosync.repository.TodoRepository;
import dev.simoncodes.todosync.sync.SyncService;
import dev.simoncodes.todosync.sync.dtos.SyncPayload;
import dev.simoncodes.todosync.sync.enums.ActionType;
import dev.simoncodes.todosync.sync.enums.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TodoListService {

    private final TodoListRepository todoListRepo;
    private final TodoRepository todoRepo;
    private final SyncService syncService;

    public List<TodoListResponse> getTodoListsForUser(UUID userId) {
        return todoListRepo.findAllByUserId(userId)
                .stream()
                .map(TodoListResponse::from)
                .toList();
    }

    public TodoListDetailResponse getSingleTodoList(UUID userId, UUID listId) {
        TodoList list = todoListRepo.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + listId));
        verifyListOwnership(userId, list);
        List<Todo> todos = todoRepo.findAllByTodoListId(listId);
        return TodoListDetailResponse.from(list, todos);
    }

    public TodoListResponse createTodoList(User user, CreateTodoListRequest request) {
        TodoList todoList = new TodoList()
                .withName(request.name())
                .withUser(user);
        todoList = todoListRepo.save(todoList);

        TodoListResponse todoListResponse = TodoListResponse.from(todoList);
        SyncPayload payload = new SyncPayload(
                EntityType.TODOLIST,
                ActionType.CREATED,
                todoListResponse
        );
        syncService.sendSyncMessage(user.getId(), payload);
        return todoListResponse;
    }

    public TodoListResponse updateTodoList(UUID userId, UUID listId, UpdateTodoListRequest request) {
        TodoList list = todoListRepo.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + listId));
        verifyListOwnership(userId, list);
        list.setName(request.name());
        list = todoListRepo.save(list);
        TodoListResponse todoListResponse = TodoListResponse.from(list);
        SyncPayload payload = new SyncPayload(
                EntityType.TODOLIST,
                ActionType.UPDATED,
                todoListResponse
        );
        syncService.sendSyncMessage(userId, payload);
        return todoListResponse;
    }

    public void deleteTodoList(UUID userId, UUID listId) {
        TodoList list = todoListRepo.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + listId));
        verifyListOwnership(userId, list);
        todoListRepo.delete(list);
        TodoListDeleteResponse response = TodoListDeleteResponse.of(listId);
        SyncPayload payload = new SyncPayload(
                EntityType.TODOLIST,
                ActionType.DELETED,
                response
        );
        syncService.sendSyncMessage(userId, payload);
    }

    private void verifyListOwnership(UUID userId, TodoList list) {
        if (!list.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + list.getId());
        }
    }

}
