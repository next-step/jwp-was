package webserver.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RequestLineTest {

    @Test
    void from_GET() {
        //when
        RequestLine requestLine = RequestLine.from("GET /users HTTP/1.1");

        assertAll(
                () -> assertEquals(requestLine.getHttpMethod(), HttpMethod.from("GET")),
                () -> assertEquals(requestLine.getUri(), Uri.from("/users")),
                () -> assertEquals(requestLine.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }

    @Test
    void from_POST() {
        //when
        RequestLine requestLine = RequestLine.from("POST /users HTTP/1.1");

        assertAll(
                () -> assertEquals(requestLine.getHttpMethod(), HttpMethod.from("POST")),
                () -> assertEquals(requestLine.getUri(), Uri.from("/users")),
                () -> assertEquals(requestLine.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }

    @Test
    void from() {
        //when
        RequestLine requestLine = RequestLine.from("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertAll(
                () -> assertEquals(requestLine.getHttpMethod(), HttpMethod.from("GET")),
                () -> assertEquals(requestLine.getUri(), Uri.from("/users?userId=javajigi&password=password&name=JaeSung")),
                () -> assertEquals(requestLine.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }
}