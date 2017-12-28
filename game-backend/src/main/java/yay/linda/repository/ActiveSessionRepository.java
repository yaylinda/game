package yay.linda.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import yay.linda.entity.Session;

import java.util.UUID;

@Repository
public interface ActiveSessionRepository extends CassandraRepository<Session, UUID> {

    @Override
    Iterable<Session> findAll(Iterable<UUID> iterable);
}
