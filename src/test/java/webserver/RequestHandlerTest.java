package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileIoUtilsTest;
import webserver.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandlerTest.class);

    private static BufferedReader bufferedReader;

    @BeforeAll
    static void mockingBufferedReader() throws IOException {
        bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine())
                .thenReturn(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive",
                        "Accept: */*",
                        ""
                );
    }

    @Test
    void responseIndexHtmlTest() throws IOException, URISyntaxException {
        String filePath ="./templates/index.html";
        HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
        RequestHandler requestHandler = new RequestHandler(null);

        byte[] responseBody = requestHandler.doGet(httpRequest).getBody();
        log.debug("response body : {}", new String(responseBody));
        assertThat(responseBody).isEqualTo(FileIoUtils.loadFileFromClasspath(filePath));
    }
}
