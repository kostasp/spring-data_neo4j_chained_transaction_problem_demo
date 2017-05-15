package org.test.neo4j.transaction;

import org.test.neo4j.transaction.service.WrapWithTransactionService;

/**
 * Created by kp on 5/15/17.
 */
public class TestNodeWrapperObject implements Runnable {

    private int index;
    private String name;
    private String uniqueId;

    private WrapWithTransactionService wrapWithTransactionService;

    public TestNodeWrapperObject(int index, String name, String uniqueId, WrapWithTransactionService wrapWithTransactionService
    ) {
        this.index = index;
        this.name = name;
        this.uniqueId = uniqueId;
        this.wrapWithTransactionService = wrapWithTransactionService;
    }

    @Override
    public void run() {
        wrapWithTransactionService.persistAll(uniqueId, name + "_" + index);
    }
}
