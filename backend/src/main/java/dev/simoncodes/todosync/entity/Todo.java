package dev.simoncodes.todosync.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "todos")
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private TodoList todoList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String description;
    @Column(name = "is_complete")
    private Boolean complete = false;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "priority_level")
    private Priority priority;
    private Instant dueDate;
    private int sortOrder;
    private Instant createdAt;
    private Instant updatedAt;

    public Todo withTodoList(TodoList todoList) {
        this.todoList = todoList;
        return this;
    }

    public Todo withUser(User user) {
        this.user = user;
        return this;
    }

    public Todo withTitle(String title) {
        this.title = title;
        return this;
    }

    public Todo withDescription(String description) {
        this.description = description;
        return this;
    }

    public Todo withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Todo withDueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Todo withSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    @PrePersist
    private void setDefaultCreatedAndUpdatedAt() {
        this.setCreatedAt(Instant.now());
        this.setUpdatedAt(Instant.now());
    }

    @PreUpdate
    private void setDefaultUpdatedAt() {
        this.setUpdatedAt(Instant.now());
    }
}