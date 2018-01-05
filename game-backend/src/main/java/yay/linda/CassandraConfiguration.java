package yay.linda;

import com.datastax.driver.core.Session;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraTemplate;
import yay.linda.entity.Player;

@Configuration
@EnableAutoConfiguration
class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Override
    public String getKeyspaceName() {
        return "example";
    }

    @Bean
    public CassandraTemplate cassandraTemplate(Session session) {
        return new CassandraTemplate(session);
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] { Player.class.getPackage().getName() };
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE;
    }
}