package org.test.neo4j.transaction.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.test.neo4j.transaction.domain.TestNode;

/**
 * Created by kp on 5/11/17.
 */
public interface TestNodeRepository extends Neo4jRepository<TestNode,String> {
}
