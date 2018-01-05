package yay.linda.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yay.linda.entity.Player;

import java.util.UUID;

@Repository
public interface PlayerRepository extends CassandraRepository<Player, UUID> {

}
