package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yay.linda.dto.Player;
import yay.linda.service.PlayerService;

import javax.inject.Inject;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Inject
    private PlayerService playerService;

    @RequestMapping(value = "/join/{name}", method = RequestMethod.POST)
    public ResponseEntity<Player> join(@PathVariable String name) {
        Player player = playerService.doMatchmaking(name);
        System.out.println(player);
        return ResponseEntity.ok(player);
    }
}
