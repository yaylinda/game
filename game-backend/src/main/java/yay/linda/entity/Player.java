package yay.linda.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table
public class Player implements Serializable {

    @PrimaryKey
    private UUID playerId;
    private String username;
    private String password;
    private String email;

}
