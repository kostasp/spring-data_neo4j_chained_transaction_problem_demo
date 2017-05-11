package org.test.neo4j.transaction.repository;

import org.springframework.data.repository.CrudRepository;
import org.test.neo4j.transaction.domain.Cargo;

/**
 * Created by kp on 5/11/17.
 */
public interface CargoRepository extends CrudRepository<Cargo,Long> {

    Cargo findByName(String name);

}
