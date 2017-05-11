package org.test.neo4j.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.test.neo4j.transaction.TestService;
import org.test.neo4j.transaction.service.WrapperService;

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
