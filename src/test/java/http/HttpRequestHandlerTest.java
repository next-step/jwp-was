package http;


import static org.assertj.core.api.Assertions.assertThat;

import http.response.StaticResourceHttpResponse;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpRequestHandlerTest {

    @DisplayName("static resource 요청 처리")
    @ParameterizedTest
    @CsvSource({
        "GET /index.html HTTP/1.1, ./templates/index.html",
        "GET /index2.html HTTP/1.1, ./templates/index2.html"
    })
    void handleRequest(String line, String path) {
        HttpRequest httpRequest = HttpRequest.of(line);
        HttpResponse response = HttpRequestHandler.handle(httpRequest);

        File file = new File(path);
        assertThat(response).isEqualTo(new StaticResourceHttpResponse(file));
    }
}
