package org.test.neo4j.transaction.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.neo4j.transaction.domain.Box;
import org.test.neo4j.transaction.repository.BoxRepository;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class BoxService {
    private static final Logger log = Logger.getLogger(BoxService.class);
    @Autowired
    BoxRepository boxRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll() {
        boxRepository.deleteAll();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Box box) {
        log.info("============SAVING BOX:" + box);
        boxRepository.save(box);
    }
}
