package org.test.neo4j.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test.neo4j.transaction.domain.Box;
import org.test.neo4j.transaction.repository.BoxRepository;
import org.test.neo4j.transaction.repository.CargoRepository;
import org.test.neo4j.transaction.service.WrapperService;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class TestService {

    public static final String BOX_NAME_OUTSIDE_TRANSACTION_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE ="OUTSIDE_TRANSACTION_BOX_NAME";

    public static final String BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE ="INSIDE_TRANSACTION_BOX_NAME";

    public static final String CARGO_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE ="INSIDE_TRANSACTION_CARGO_NAME";

    @Autowired
    WrapperService wrapperService;

    @Autowired
    BoxRepository boxRepository;

    @Autowired
    CargoRepository cargoRepository;
    public class TestNodeWrapperObject implements Runnable {

        private int index;
        private String name;
        private String uniqueId;

        private WrapperService wrapperService;

        public TestNodeWrapperObject(int index, String name, String uniqueId, WrapperService wrapperService) {
            this.index = index;
            this.name = name;
            this.uniqueId = uniqueId;
            this.wrapperService = wrapperService;
        }

        @Override
        public void run() {
            wrapperService.persistAll(uniqueId, name+"_" + index);
        }
    }

    public void runTest() throws InterruptedException {
        String testNodeName = "testName";
        String testNodeUniqueId = "testUniqueId";

        //simulate parallel transactions coming from different 3 treads
        for (int i = 0; i < 3; i++) {
            new Thread(new TestNodeWrapperObject(i, testNodeName, testNodeUniqueId, wrapperService)).start();
        }
        Thread.sleep(3000l);
        boxRepository.save(new Box(BOX_NAME_OUTSIDE_TRANSACTION_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE,
                BOX_NAME_OUTSIDE_TRANSACTION_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE +"_description"));

        Box boxFinal = boxRepository.findByName(BOX_NAME_OUTSIDE_TRANSACTION_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE);

        assert (boxFinal != null);
    }
}
