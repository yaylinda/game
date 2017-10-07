package yay.linda.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yay.linda.dto.Player;
import yay.linda.service.PlayerService;

import javax.inject.Inject;

@RestController
@RequestMapping("/player")
@CrossOrigin("*")
public class PlayerController {

    @Inject
    private PlayerService playerService;

    @RequestMapping(value = "/join/{name}", method = RequestMethod.POST)
    public ResponseEntity<Player> join(@PathVariable String name) {
        Player player = playerService.join(name);
        return ResponseEntity.ok(player);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Player> findPlayerById(@PathVariable String id) {
        Player player = playerService.findPlayerById(id);
        return ResponseEntity.ok(player);
    }


}
