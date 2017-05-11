package org.test.neo4j.transaction.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.test.neo4j.transaction.domain.Box;
import org.test.neo4j.transaction.repository.BoxRepository;
import org.test.neo4j.transaction.service.TestNodeService;

import java.util.Optional;

/**
 * Created by kp on 5/11/17.
 */
@RestController
public class BoxController {
    private final Logger LOGGER = LoggerFactory.getLogger(TestNodeService.class);
    @Autowired
    BoxRepository boxRepository;

    @GetMapping("/api/box/get/{name}")
    public ResponseEntity<Box> getBox(@PathVariable("name") String name) throws
            InterruptedException {

        LOGGER.info(String.format("===getBox with name:%s",name));
        Box box = boxRepository.findByName(name);
        return Optional.ofNullable(box)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
