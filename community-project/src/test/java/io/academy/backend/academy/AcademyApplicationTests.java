package io.academy.backend.academy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "NoDB")
class AcademyApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {

	}

}
