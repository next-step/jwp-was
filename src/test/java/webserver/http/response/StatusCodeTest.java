package webserver.http.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusCodeTest {

    @Test
    void statusCode200() {
        assertEquals(200, StatusCode.OK.getStatus());
    }

    @Test
    void statusCode302() {
        assertEquals(302, StatusCode.FOUND.getStatus());
    }

    @Test
    void statusCode400() {
        assertEquals(400, StatusCode.BAD_REQUEST.getStatus());
    }
}
