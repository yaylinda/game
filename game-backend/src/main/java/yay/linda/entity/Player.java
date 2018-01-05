package yay.linda.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table(value = "player")
public class Player implements Serializable {

    @PrimaryKey("player_id") private UUID playerId;
    @Column("username") private String username;
    @Column("password") private String password;
    @Column("email") private String email;

    public Player(String username, String password, String email) {
        this.playerId = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
