package com.project.cadmus_challenge.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class ControllerIntegrationTests {
    protected static final String ROOT_URI = "/api/v1";

    protected static final Long PRE_EXISTING_ID = 0L;
    protected static final Long NON_EXISTING_ID = -1L;
    protected static final String INVALID_ID = "ID";

    @Autowired
    protected WebTestClient webTestClient;
}
