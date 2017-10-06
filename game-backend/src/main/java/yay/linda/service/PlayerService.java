package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.dto.Player;
import yay.linda.dto.enums.PlayerTeam;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class PlayerService {

    private boolean waitingPlayer = false;

    public Player doMatchmaking(String name) {
        Player player = new Player(name);
        if (!waitingPlayer) {
            player.setTeam(PlayerTeam.TEAM1.name());
            waitingPlayer = true;
        } else {
            player.setTeam(PlayerTeam.TEAM2.name());
            waitingPlayer = false;
        }
        return player;
    }
}
