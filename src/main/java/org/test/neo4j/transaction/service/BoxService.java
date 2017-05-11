package org.test.neo4j.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.neo4j.transaction.repository.BoxRepository;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class BoxService {

    @Autowired
    BoxRepository boxRepository;
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll(){
        boxRepository.deleteAll();
    }
}
