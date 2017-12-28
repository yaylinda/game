package yay.linda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import yay.linda.dto.PlayerDTO;
import yay.linda.dto.SessionToken;
import yay.linda.entity.Player;
import yay.linda.entity.Session;
import yay.linda.repository.PlayerRepository;
import yay.linda.repository.ActiveSessionRepository;

import java.util.List;
import java.util.UUID;

@Component
public class UserService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    public SessionToken login(String username, String password) {
        UUID sessionId = UUID.randomUUID();
        List<Player> allPlayers = playerRepository.findAll();
        boolean playerExists = false;
        UUID playerId = UUID.randomUUID();
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username) & player.getPassword().equals(password)) {
                playerExists = true;
                playerId = player.getPlayerId();
                break;
            }
        }
        if (playerExists) {
            List<Session> allActiveSessions = activeSessionRepository.findAll();
            boolean playerHasActiveSession = false;
            for (Session activeSession: allActiveSessions) {
                if (activeSession.getPlayerId().equals(playerId)) {
                    sessionId = activeSession.getSessionId();
                    playerHasActiveSession = true;
                    break;
                }
            }
            if (!playerHasActiveSession) {
                Session newSession = new Session(playerId);
                sessionId = newSession.getSessionId();
                activeSessionRepository.save(newSession);
            }
        }


        return new SessionToken(sessionId.toString());
    }

    public SessionToken register(String username, String password, String email) {
        Player newPlayer = new Player(username, password, email);
        UUID playerId = newPlayer.getPlayerId();
        playerRepository.save(newPlayer);
        return login(username, password);
    }

    public ResponseEntity<PlayerDTO> getPlayer(String sessionToken) {
        return null;
    }
}
