package org.test.neo4j.transaction.test;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.test.neo4j.transaction.TestService;
import org.test.neo4j.transaction.TransactionTestApplicationTests;
import org.test.neo4j.transaction.service.BoxService;
import org.test.neo4j.transaction.service.CargoService;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.test.neo4j.transaction.TestService.BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE;
import static org.test.neo4j.transaction.TestService.BOX_NAME_OUTSIDE_TRANSACTION_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE;

/**
 * Created by kp on 5/11/17.
 */
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
    }

    @Autowired
    BoxService boxService;

    @Autowired
    CargoService cargoService;

    @Before
    @Transactional
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        jdbcTemplate.update("delete from box");
        jdbcTemplate.update("delete from cargo");
        boxService.deleteAll();
        cargoService.deleteAll();
    }


    @Before
    public void clearNeo4jAndInit() {

        session.query(" MATCH (n:TEST_NODE) DETACH DELETE n", new HashMap<>());
        session.query("create (t:TEST_NODE{name:\"INIT_NODE\",uniqueId:\"papageorgopoulos\"}) return t", new HashMap<>());
        session.query("CREATE CONSTRAINT ON (t:TEST_NODE) ASSERT t.uniqueId IS UNIQUE", new HashMap<>());
        session.clear();


    }



    @Test
    public void runTestUsingSimulatingApiCalls() throws Exception {
        //tries to write in parallel nodes with the same uniqueId in Neo4j cathes unique contraint and then it tries to write in separate transaction
        // 2 entities in postgres
        try {
            mockMvc.perform(get("/api/test/run"))
                    .andExpect(status().is(200));

        } finally {
            //this one exists,
            mockMvc.perform(get("/api/box/get/" + BOX_NAME_OUTSIDE_TRANSACTION_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE))
                    .andExpect(status().is(200)).andReturn();
            //this one not, but IT SHOULD
            mockMvc.perform(get("/api/box/get/" + BOX_NAME_INSIDE_TRANSACTION_NOT_WRITTEN_SUCCSFULLY_AFTER_NEO4J_FAILURE))
                    .andExpect(status().is(200)).andReturn();
        }

    }


}
