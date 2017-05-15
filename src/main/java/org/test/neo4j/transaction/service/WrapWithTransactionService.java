package org.test.neo4j.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.neo4j.transaction.TestService;
import org.test.neo4j.transaction.domain.Box;
import org.test.neo4j.transaction.domain.Cargo;
import org.test.neo4j.transaction.repository.BoxRepository;
import org.test.neo4j.transaction.repository.CargoRepository;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class WrapWithTransactionService {
    @Autowired
    private BoxService boxService;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private TestNodeService testNodeService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void persistAll(String uniqueId, String name) {
        //1st step will fail because of parallel threads trying to write same node using a unique constraint on uniqueId field.Exception is caught
        //and code wants to continue.
        testNodeService.persistTestNode(uniqueId, name);
        //this one should be written BUT DOES NOT
        boxService.save(new Box(TestService.BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE, "cargo_description_" +
                uniqueId));
        //this one should be written BUT DOES NOT
        cargoService.save(new Cargo(TestService.CARGO_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE,
                "cargo_description_" + uniqueId));


    }


}
