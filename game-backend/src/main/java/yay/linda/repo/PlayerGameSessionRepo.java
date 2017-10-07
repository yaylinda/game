package yay.linda.repo;

import yay.linda.service.GameSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerGameSessionRepo {
    private Map<UUID, GameSession> gameSessionMap;

    public PlayerGameSessionRepo() {
        this.gameSessionMap = new HashMap<>();
    }

    public void assignGameSession(UUID playerId, GameSession gameSession) {
        this.gameSessionMap.put(playerId, gameSession);
    }

    public GameSession getGameSessionById(UUID id) {
        return this.gameSessionMap.get(id);
    }
}
