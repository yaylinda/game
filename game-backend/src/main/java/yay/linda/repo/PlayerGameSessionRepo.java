package yay.linda.repo;

import yay.linda.service.GameSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<String> getPlayerIds() {
        return gameSessionMap.keySet().stream().map(UUID::toString).collect(Collectors.toList());
    }
}
