package yay.linda.service;

import org.springframework.stereotype.Component;
import yay.linda.dto.Player;
import yay.linda.dto.UserDTO;

@Component
public class UserService {

    public Player createUser(UserDTO userDTO) {
        Player newPlayer = new Player();
        newPlayer.setName(userDTO.getUsername());
        // TODO save user dto info to repo
        return newPlayer;
    }

    public Player loginUser(UserDTO userDTO) {
        // TODO look up user dto
        return null;
    }
}
