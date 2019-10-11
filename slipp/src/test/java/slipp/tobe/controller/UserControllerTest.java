package slipp.tobe.controller;

import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import slipp.domain.User;
import slipp.support.db.DataBase;
import support.test.NsWebTestClient;
import support.test.NsWebTestServer;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        NsWebTestServer nsWebTestServer = new NsWebTestServer(8080);
        nsWebTestServer.start();
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    void getUserProfile() {
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users?userId=admin");
        String response = result.toString();
        assertThat(response).contains("자바지기");
    }

    @Test
    void postUser() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("name", "무민");
        params.put("password", "password");
        params.put("email", "moomin@woowahan.com");
        assertThat(nsWebTestClient.postForm("/users", params)).isEqualTo(URI.create("/"));
    }
}