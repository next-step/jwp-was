package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandlerTest.class);

    private static RequestStream requestStream;

    @BeforeAll
    static void mockingBufferedReader() {
        String request = "GET /index.html HTTP/1.1\n" +
        "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";

        requestStream = new RequestStream(new ByteArrayInputStream(request.getBytes()));
    }

    @Test
    void responseIndexHtmlTest() throws IOException, URISyntaxException {
        String filePath ="./templates/index.html";
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        HttpResponse httpResponse = new HttpResponse();

        RequestHandler requestHandler = new RequestHandler(null);

        byte[] responseBody = requestHandler.getResponse(httpRequest).getBody();
        log.debug("response body : {}", new String(responseBody));
        assertThat(responseBody).isEqualTo(FileIoUtils.loadFileFromClasspath(filePath));
    }
}
