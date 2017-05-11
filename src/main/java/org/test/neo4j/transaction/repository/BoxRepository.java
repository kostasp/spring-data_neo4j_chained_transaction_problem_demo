package org.test.neo4j.transaction.repository;

import org.springframework.data.repository.CrudRepository;
import org.test.neo4j.transaction.domain.Box;

/**
 * Created by kp on 5/11/17.
 */
public interface BoxRepository extends CrudRepository<Box, Long> {

    Box findByName(String name);
}
