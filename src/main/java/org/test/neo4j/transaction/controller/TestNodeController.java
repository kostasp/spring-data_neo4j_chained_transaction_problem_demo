package org.test.neo4j.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.neo4j.transaction.TestService;

/**
 * Created by kp on 5/11/17.
 */
@RestController
public class TestNodeController {


    @Autowired
    private TestService testService;

    @GetMapping("/api/test/run")
    public ResponseEntity createNodeAndSubEntities() throws
            InterruptedException {
        testService.runTest();
        return ResponseEntity.ok("Ok");
    }
}
