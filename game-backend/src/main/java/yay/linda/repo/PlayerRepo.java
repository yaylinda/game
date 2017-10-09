package yay.linda.repo;

import yay.linda.dto.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerRepo {
    private Map<String, Player> playerMap;

    public PlayerRepo() {
        this.playerMap = new HashMap<>();
    }

    public void addPlayer(Player player) {
        this.playerMap.put(player.getId(), player);
    }

    public Player getPlayerById(String id) {
        return this.playerMap.get(id);
    }

    public Set<String> getPlayerIds() {
        return playerMap.keySet();
    }
}
