package org.test.neo4j.transaction.test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.test.neo4j.transaction.TestNodeWrapperObject;
import org.test.neo4j.transaction.TestService;
import org.test.neo4j.transaction.TransactionTestApplicationTests;
import org.test.neo4j.transaction.domain.Box;
import org.test.neo4j.transaction.domain.Cargo;
import org.test.neo4j.transaction.repository.BoxRepository;
import org.test.neo4j.transaction.repository.CargoRepository;
import org.test.neo4j.transaction.service.BoxService;
import org.test.neo4j.transaction.service.CargoService;
import org.test.neo4j.transaction.service.WrapWithTransactionService;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.test.neo4j.transaction.TestService.BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE;
import static org.test.neo4j.transaction.TestService.CARGO_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE;

/**
 * Created by kp on 5/11/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChainedTransactionFailureTest extends TransactionTestApplicationTests {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    Session session;

    @Autowired
    TestService testService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    BoxService boxService;

    @Autowired
    CargoService cargoService;

    @Autowired
    BoxRepository boxRepository;

    @Autowired
    CargoRepository cargoRepository;

    @Autowired
    WrapWithTransactionService wrapWithTransactionService;

    @Before
    @Transactional
    public void clearStorage() {
        jdbcTemplate.update("delete from box");
        jdbcTemplate.update("delete from cargo");

        session.query(" MATCH (n:TEST_NODE) DETACH DELETE n", new HashMap<>());
        session.query("create (t:TEST_NODE{name:\"INIT_NODE\",uniqueId:\"papageorgopoulos\"}) return t", new HashMap<>());
        session.query("CREATE CONSTRAINT ON (t:TEST_NODE) ASSERT t.uniqueId IS UNIQUE", new HashMap<>());
        session.clear();
    }


    @Test
    public void runTest1Simple() throws InterruptedException {
        String testNodeName = "testName";
        String testNodeUniqueId = "testUniqueId";

        //simulate parallel transactions coming from different 3 treads, The first step of this action will cause a unique constraint exception
        // from neo4j caught and swallowed, the next steps will try to store objects in postgres unsuccessfully
        for (int i = 0; i < 3; i++) {
            new Thread(new TestNodeWrapperObject(i, testNodeName, testNodeUniqueId, wrapWithTransactionService)).start();
        }

        Thread.sleep(3000L);

        //this one althought it should be written, it DOES NOT,
        Box boxInsideTransactionNOTWritten = boxRepository.findByName
                (BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE);
        assert (boxInsideTransactionNOTWritten != null);

        //this one althought it should be written, it DOES NOT, WILL NEVER REACH THIS LINE BECAUSE PREVIOUS ONE FAILS
        Cargo cargonsideTransactionNOTWritten = cargoRepository.findByName
                (CARGO_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE);
        assert (cargonsideTransactionNOTWritten != null);
    }





}
