package org.test.neo4j.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test.neo4j.transaction.repository.BoxRepository;
import org.test.neo4j.transaction.repository.CargoRepository;
import org.test.neo4j.transaction.service.TestNodeService;
import org.test.neo4j.transaction.service.WrapWithTransactionService;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class TestService {


    public static final String BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE = "INSIDE_TRANSACTION_BOX_NAME";

    public static final String CARGO_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE = "INSIDE_TRANSACTION_CARGO_NAME";

    @Autowired
    private WrapWithTransactionService wrapWithTransactionService;


    public void runTest() throws InterruptedException {
        String testNodeName = "testName";
        String testNodeUniqueId = "testUniqueId";

        //simulate parallel transactions coming from different 3 treads, The first step of this action will cause a unique constraint exception
        // from neo4j caught and swallowed, the next steps will try to store objects in postgres unsuccessfully
        for (int i = 0; i < 3; i++) {
            new Thread(new TestNodeWrapperObject(i, testNodeName, testNodeUniqueId, wrapWithTransactionService)).start();
        }


    }
}
