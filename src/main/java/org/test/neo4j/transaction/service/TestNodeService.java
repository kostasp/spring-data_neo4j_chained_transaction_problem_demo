package org.test.neo4j.transaction.service;

import org.neo4j.driver.v1.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.neo4j.transaction.domain.TestNode;
import org.test.neo4j.transaction.repository.TestNodeRepository;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class TestNodeService {
    private final Logger LOGGER = LoggerFactory.getLogger(TestNodeService.class);

    @Autowired
    private TestNodeRepository testNodeRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void persistTestNode( String uniqueId,String nameOfNode) {
        TestNode sampleNode = new TestNode(uniqueId, nameOfNode);
        try {
            testNodeRepository.save(sampleNode);
        } catch (ClientException exception) {
            if (exception.getMessage() != null && exception.code().equals("Neo.ClientError.Schema.ConstraintValidationFailed")) {
                String msg = "=============CATCH EXCEPTION AND CONTINUE EXECUTION , A node with the same id already exists in neo4j ,uniqueId is:" +
                        sampleNode.getUniqueId();
                LOGGER.warn(msg);

            } else {
                throw exception;
            }
        }
    }
}
