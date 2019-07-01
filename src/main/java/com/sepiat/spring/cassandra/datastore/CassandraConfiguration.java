package com.sepiat.spring.cassandra.datastore;

import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

/**
 * @author alexandru.ionita@sepiat.com
 * @since 1.0.0, created on 2019-07-01
 */
@Configuration
@EnableCassandraRepositories(basePackages = {"com.sepiat.spring.cassandra.datastore.repos"})
public class CassandraConfiguration extends AbstractCassandraConfiguration
{
    final String nodes = "localhost";
    final String keyspace = "demo";
    final int replicationFactor = 1;


    @Override
    public String[] getEntityBasePackages()
    {
        return new String[] {"com.sepiat.spring.cassandra.model"};
    }

    @Override
    protected String getKeyspaceName()
    {
        return keyspace;
    }

    @Override
    protected String getContactPoints()
    {
        return nodes;
    }

    @Override
    protected boolean getMetricsEnabled()
    {
        return false;
    }

    @Bean
    public CassandraOperations caterpillarCassandraTemplate(Session session) {
        return new CassandraTemplate( session);
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(keyspace).ifNotExists()
                .with( KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication( replicationFactor );

        return Arrays.asList( specification);
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
}
