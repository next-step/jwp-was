package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.RequestStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RequestHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandlerTest.class);

    private static RequestStream requestStream;

    @BeforeAll
    static void mockingBufferedReader() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";

        requestStream = new RequestStream(new ByteArrayInputStream(request.getBytes()));
    }
}
