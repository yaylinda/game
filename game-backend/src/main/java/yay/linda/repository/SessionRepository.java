package yay.linda.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import yay.linda.entity.Player;

public interface SessionRepository extends CassandraRepository<Session, UUID> {
}
