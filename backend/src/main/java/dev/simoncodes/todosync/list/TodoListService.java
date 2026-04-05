package dev.simoncodes.todosync.list;

import dev.simoncodes.todosync.entity.Todo;
import dev.simoncodes.todosync.entity.TodoList;
import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.list.dto.CreateTodoListRequest;
import dev.simoncodes.todosync.list.dto.TodoListDetailResponse;
import dev.simoncodes.todosync.list.dto.TodoListResponse;
import dev.simoncodes.todosync.list.dto.UpdateTodoListRequest;
import dev.simoncodes.todosync.repository.TodoListRepository;
import dev.simoncodes.todosync.repository.TodoRepository;
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
        return TodoListResponse.from(todoList);

    }

    public TodoListResponse updateTodoList(UUID userId, UUID listId, UpdateTodoListRequest request) {
        TodoList list = todoListRepo.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + listId));
        verifyListOwnership(userId, list);
        list.setName(request.name());
        list = todoListRepo.save(list);
        return TodoListResponse.from(list);
    }

    public void deleteTodoList(UUID userId, UUID listId) {
        TodoList list = todoListRepo.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + listId));
        verifyListOwnership(userId, list);
        todoListRepo.delete(list);
    }

    private void verifyListOwnership(UUID userId, TodoList list) {
        if (!list.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found for provided id: " + list.getId());
        }
    }

}
