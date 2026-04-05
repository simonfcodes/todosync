package dev.simoncodes.todosync.sync;

import dev.simoncodes.todosync.sync.dtos.SyncPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SyncService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendSyncMessage(UUID userId, SyncPayload payload) {
        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/sync", payload);
    }
}
