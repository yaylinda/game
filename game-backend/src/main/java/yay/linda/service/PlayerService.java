package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.dto.Player;
import yay.linda.dto.enums.PlayerTeam;
import yay.linda.repo.PlayerRepo;

import java.util.UUID;

@Component
public class PlayerService {

    private Player player1;
    private PlayerRepo playerRepo = new PlayerRepo();

    public Player join(String name) {
        Player player = new Player(name);
        if (player1 == null) {
            player.setTeam(PlayerTeam.TEAM1.name());
            player.setPower(1);
            player1 = player;
        } else {
            player.setTeam(PlayerTeam.TEAM2.name());
            player.setPower(2);
            player.setOpponentId(player1.getId());
            player1 = null;
        }
        playerRepo.addPlayer(player);
        return player;
    }

    public Player findPlayerById(String id) {
        return playerRepo.getPlayerById(UUID.fromString(id));
    }
}
