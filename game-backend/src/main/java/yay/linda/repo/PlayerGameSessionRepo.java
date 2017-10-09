package yay.linda.repo;

import yay.linda.game.GameSession;

import java.util.*;

public class PlayerGameSessionRepo {
    private Map<String, GameSession> gameSessionMap;

    public PlayerGameSessionRepo() {
        this.gameSessionMap = new HashMap<>();
    }

    public void assignGameSession(String playerId, GameSession gameSession) {
        this.gameSessionMap.put(playerId, gameSession);
    }

    public GameSession getGameSessionById(String id) {
        return this.gameSessionMap.get(id);
    }

    public Set<String> getPlayerIds() {
        return gameSessionMap.keySet();
    }
}
