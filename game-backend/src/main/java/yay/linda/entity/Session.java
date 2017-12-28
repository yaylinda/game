package yay.linda.entity;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table
public class Session implements Serializable {
    @PrimaryKey
    private UUID sessionId;
    private UUID playerId;

    public Session (UUID playerId) {
        this.sessionId = UUID.randomUUID();
        this.playerId = playerId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
