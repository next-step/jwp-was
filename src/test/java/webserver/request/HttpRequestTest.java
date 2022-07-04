package webserver.request;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class HttpRequestTest {

    @Test
    void from_GET() throws IOException {
        //when
        String reqeustStr = "GET /users HTTP/1.1";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(reqeustStr.getBytes()), "UTF-8"));
        HttpRequest httpRequest = HttpRequest.from(br);

        assertAll(
                () -> assertEquals(httpRequest.getHttpMethod(), HttpMethod.from("GET")),
                () -> assertEquals(httpRequest.getUri(), Uri.from("/users")),
                () -> assertEquals(httpRequest.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }

    @Test
    void from_POST() throws IOException {
        //when
        String reqeustStr = "POST /users HTTP/1.1";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(reqeustStr.getBytes()), "UTF-8"));
        HttpRequest httpRequest = HttpRequest.from(br);

        assertAll(
                () -> assertEquals(httpRequest.getHttpMethod(), HttpMethod.from("POST")),
                () -> assertEquals(httpRequest.getUri(), Uri.from("/users")),
                () -> assertEquals(httpRequest.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }

    @Test
    void from() throws IOException {
        //when
        String reqeustStr = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(reqeustStr.getBytes()), "UTF-8"));
        HttpRequest httpRequest = HttpRequest.from(br);

        assertAll(
                () -> assertEquals(httpRequest.getHttpMethod(), HttpMethod.from("GET")),
                () -> assertEquals(httpRequest.getUri(), Uri.from("/users?userId=javajigi&password=password&name=JaeSung")),
                () -> assertEquals(httpRequest.getProtocol(), Protocol.from("HTTP/1.1"))
        );
    }
}