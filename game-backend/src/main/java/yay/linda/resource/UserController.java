package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.UserDTO;
import yay.linda.dto.Player;
import yay.linda.service.UserService;

import javax.inject.Inject;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Inject
    private UserService userService;

    /**
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Player> createUser(@RequestBody UserDTO userDTO) {
        Player player = userService.createUser(userDTO);
        return ResponseEntity.ok(player);
    }

    /**
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Player> loginUser(@RequestBody UserDTO userDTO) {
        Player player = userService.loginUser(userDTO);
        return ResponseEntity.ok(player);
    }
}
