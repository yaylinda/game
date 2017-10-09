package yay.linda.repo;

import yay.linda.dto.Player;

import java.util.*;
import java.util.stream.Collectors;

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
