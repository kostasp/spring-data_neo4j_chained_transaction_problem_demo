package org.test.neo4j.transaction.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.neo4j.transaction.domain.Cargo;
import org.test.neo4j.transaction.repository.CargoRepository;

/**
 * Created by kp on 5/11/17.
 */
@Service
public class CargoService {
    private static final Logger log = Logger.getLogger(CargoService.class);
    @Autowired
    CargoRepository cargoRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll() {
        cargoRepository.deleteAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Cargo cargo) {
        log.info("============SAVING CARGO:"+cargo);
        cargoRepository.save(cargo);
    }

}
