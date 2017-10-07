package yay.linda.repo;

import yay.linda.dto.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRepo {
    private Map<UUID, Player> playerMap;

    public PlayerRepo() {
        this.playerMap = new HashMap<>();
    }

    public void addPlayer(Player player) {
        this.playerMap.put(player.getId(), player);
    }

    public Player getPlayerById(UUID id) {
        return this.playerMap.get(id);
    }
}
