package org.test.neo4j.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionTestApplication.class)
@WebAppConfiguration
@ActiveProfiles(value = {"test"})
public class TransactionTestApplicationTests {

	@Test
	public void contextLoads() {
	}

}
