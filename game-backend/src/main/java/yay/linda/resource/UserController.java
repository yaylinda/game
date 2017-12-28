package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.SessionToken;
import yay.linda.dto.PlayerDTO;
import yay.linda.service.UserService;

import javax.inject.Inject;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class UserController {

    @Inject
    private UserService userService;

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    public ResponseEntity<SessionToken> login(@PathVariable("username") String username, @PathVariable("password") String password) {
        SessionToken sessionToken = userService.login(username, password);
        return ResponseEntity.ok(sessionToken);
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register/{username}/{password}/{email}", method = RequestMethod.GET)
    public ResponseEntity<SessionToken> register(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("email") String email) {
        SessionToken sessionToken = userService.register(username, password, email);
        return ResponseEntity.ok(sessionToken);
    }


    /**
     *
     * @param sessionToken
     * @return
     */
    @RequestMapping(value = "/players/{sessionToken}", method = RequestMethod.GET)
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable("sessionToken") String sessionToken) {
        return userService.getPlayer(sessionToken);
    }
}
