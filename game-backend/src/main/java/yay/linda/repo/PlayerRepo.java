package yay.linda.repo;

import yay.linda.dto.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<String> getPlayerIds() {
        return playerMap.keySet().stream().map(UUID::toString).collect(Collectors.toList());
    }
}
