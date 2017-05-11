package org.test.neo4j.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("org.test.neo4j.transaction")
@EnableNeo4jRepositories("org.test.neo4j.transaction.repository")
@EntityScan(
        basePackageClasses = {TransactionTestApplication.class, Jsr310JpaConverters.class}
)
public class TransactionTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionTestApplication.class, args);
    }
}
