package yay.linda.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import yay.linda.dto.PlayerDTO;
import yay.linda.dto.SessionToken;

@Component
public class UserService {


    public ResponseEntity<SessionToken> login(String username, String password) {
        return null;
    }

    public ResponseEntity<PlayerDTO> getPlayer(String sessionToken) {
        return null;
    }
}
