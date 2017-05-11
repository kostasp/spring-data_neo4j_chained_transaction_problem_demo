package org.test.neo4j.transaction;


import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by kp on 5/11/17.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String postgresDataSourceUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager chainedTransactionManager(SessionFactory sessionFactory) throws Exception {
        Neo4jTransactionManager neoTransactionManager = new Neo4jTransactionManager(sessionFactory);
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
        return new ChainedTransactionManager(dataSourceTransactionManager, neoTransactionManager);
    }



    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws Exception {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgresDataSourceUrl);
        dataSource.setUsername(postgresUsername);
        dataSource.setPassword(postgresPassword);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setMaxActive(500);
        dataSource.setMaxIdle(150);
        dataSource.setDefaultTransactionIsolation(java.sql.Connection.TRANSACTION_READ_COMMITTED);
        return dataSource;
    }

}
