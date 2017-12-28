package yay.linda.repo;

import yay.linda.dto.PlayerDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerRepo {
    private Map<String, PlayerDTO> playerMap;

    public PlayerRepo() {
        this.playerMap = new HashMap<>();
    }

    public void addPlayer(PlayerDTO player) {
        this.playerMap.put(player.getId(), player);
    }

    public PlayerDTO getPlayerById(String id) {
        return this.playerMap.get(id);
    }

    public Set<String> getPlayerIds() {
        return playerMap.keySet();
    }
}
