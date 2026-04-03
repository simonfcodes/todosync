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
@Table(name = "refresh_tokens")
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Instant createdAt;
    private Instant expiresOn;
    private Boolean revoked = false;

    @PrePersist
    private void setDefaultCreatedAt() {
        this.setCreatedAt(Instant.now());
    }
}
