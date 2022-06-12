package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.HttpMethod;
import webserver.request.Path;
import webserver.request.Protocol;
import webserver.request.RequestLine;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RequestLineTest {

    @Test
    void from() {
        //when
        RequestLine requestLine = RequestLine.from("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertAll(
                () -> assertEquals(requestLine.getHttpMethod(), HttpMethod.from("GET")),
                () -> assertEquals(requestLine.getPath(), Path.from("/users?userId=javajigi&password=password&name=JaeSung")),
                () -> assertEquals(requestLine.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }
}