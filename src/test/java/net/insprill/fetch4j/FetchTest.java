package net.insprill.fetch4j;

import net.insprill.fetch4j.exception.HostNotFoundException;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.UUID;

import static net.insprill.fetch4j.Fetch.fetch;
import static net.insprill.fetch4j.Params.params;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FetchTest {

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
                .contentType("application/json")
                .body("{\n" +
                        "\"email\": \"eve.holt@reqres.in\",\n" +
                        "\"password\": \"pistol\"\n" +
                        "}"));

        assertEquals(200, response.getStatus());
        assertEquals("utf-8", response.getContentEncoding());
        assertEquals("application/json", response.getContentType());
    }

    @Test
    void postBadRequest() {
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
    void unknownHostTest() {
        String URL = "https://www." + UUID.randomUUID() + ".gov/";
        try {
            fetch(URL);
        } catch (HostNotFoundException ignored) {
            return;
        }
        throw new AssertionFailedError("Failed to throw HostNotFoundException for URL '" + URL + "'");
    }

}
