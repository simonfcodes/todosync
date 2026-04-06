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
        System.out.println("SYNC DEBUG: Sending to user: " + userId.toString());
        System.out.println("SYNC DEBUG: Payload: " + payload);
        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/sync", payload);
    }
}
