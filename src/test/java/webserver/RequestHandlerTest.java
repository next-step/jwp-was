package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import route.Router;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.RequestStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void responseIndexHtmlTest() throws Exception {
        String filePath ="./templates/index.html";
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        WebServerRouter webServerRouter = new Router();

        RequestHandler requestHandler = new RequestHandler(null, webServerRouter);

        byte[] responseBody = requestHandler.writeResponse(httpRequest).getBody();
        log.debug("response body : {}", new String(responseBody));
        assertThat(responseBody).isEqualTo(FileIoUtils.loadFileFromClasspath(filePath));
    }
}
