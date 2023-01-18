package net.insprill.fetch4j;

import net.insprill.fetch4j.exception.HostNotFoundException;
import net.insprill.fetch4j.exception.InvalidURLException;
import net.insprill.fetch4j.exception.TimeoutException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static net.insprill.fetch4j.Fetch.fetch;
import static net.insprill.fetch4j.Params.params;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FetchTest {

    @BeforeAll
    static void setUp() {
        Params.setDefaultHeaders(Collections.singletonMap("User-Agent", "fetch4j"));
    }

    @Test
    void get() {
        Response response = fetch("https://reqres.in/api/users/2");

        assertEquals(200, response.getStatus());
        assertEquals(280, response.getContentLength());
        assertEquals("utf-8", response.getContentEncoding());
        assertEquals("application/json", response.getContentType());
    }

    @Test
    void post() {
        Response response = fetch("https://reqres.in/api/register", params()
                .method(Params.Method.POST)
                .contentType(Params.ContentType.JSON)
                .body("{\n" +
                        "\"email\": \"eve.holt@reqres.in\",\n" +
                        "\"password\": \"pistol\"\n" +
                        "}"));

        assertEquals(200, response.getStatus());
        assertEquals("utf-8", response.getContentEncoding());
        assertEquals("application/json", response.getContentType());
    }

    @Test
    void post_BadRequest() {
        Response response = fetch("https://reqres.in/api/register", params().method(Params.Method.POST));

        assertEquals(400, response.getStatus());
        assertEquals("utf-8", response.getContentEncoding());
        assertEquals("application/json", response.getContentType());
        assertEquals("{\"error\":\"Missing email or username\"}", response.getBody());
    }

    @Test
    void delete() {
        Response response = fetch("https://reqres.in/api/users/2", params()
                .method(Params.Method.DELETE));

        assertEquals(204, response.getStatus());
    }

    @Test
    void unknownHost() {
        String URL = "https://www." + UUID.randomUUID() + ".gov";
        assertThrowsExactly(HostNotFoundException.class, () -> fetch(URL));
    }

    @Test
    void invalidUrl() {
        String URL = "www.google.com";
        assertThrowsExactly(InvalidURLException.class, () -> fetch(URL));
    }

    @Test
    void connectionTimeout() {
        String URL = "https://www.google.com";
        assertThrowsExactly(TimeoutException.class, () -> fetch(URL, params().connectionTimeout(1)));
    }

    @Test
    void queryParameters_Single_ProperlySet() {
        Response response = fetch("https://reqres.in/api/users", params()
                .query("page", 2));
        assertTrue(response.getBody().startsWith("{\"page\":2"));
    }

    @Test
    void queryParameters_Multiple_ProperlySet() {
        Response response = fetch("https://reqres.in/api/users", params()
                .query("page", 2)
                .query("per_page", 4));
        assertTrue(response.getBody().startsWith("{\"page\":2,\"per_page\":4"));
    }

}
