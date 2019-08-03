package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileIoUtilsTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

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

        HttpResponse httpResponse = httpRequest.doGet();
        log.debug("response body : {}", new String(httpResponse.getBody()));
        assertThat(httpResponse.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath(filePath));
    }
}
