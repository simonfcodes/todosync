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
@Table(name = "lists")
@Entity
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private Instant createdAt;

    @PrePersist
    private void setDefaultCreatedAt() {
        this.setCreatedAt(Instant.now());
    }
}
