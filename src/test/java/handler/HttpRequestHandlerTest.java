package handler;


import static org.assertj.core.api.Assertions.assertThat;

import http.response.HttpResponse;
import http.request.HttpRequest;
import http.response.RedirectHttpResponse;
import http.response.StaticResourceHttpResponse;
import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpRequestHandlerTest {

    private HttpRequestHandler httpRequestHandler;

    @BeforeEach
    void init(){
        this.httpRequestHandler = new HttpRequestHandler();
    }

    @DisplayName("static resource 요청 처리")
    @ParameterizedTest
    @CsvSource({
        "GET /index.html HTTP/1.1, ./templates/index.html",
        "GET /index2.html HTTP/1.1, ./templates/index2.html"
    })
    void handleStaticResourceRequest(String line, String path) {
        HttpRequest httpRequest = HttpRequest.of(
            line,
            Arrays.asList(),
            null
        );
        HttpResponse response = httpRequestHandler.handle(httpRequest);

        File file = new File(path);
        assertThat(response).isEqualTo(new StaticResourceHttpResponse(file));
    }

    @Test
    void handleUserCreate(){
        HttpRequest httpRequest = HttpRequest.of(
            "POST /user/create HTTP/1.1",
            Arrays.asList(),
            null
        );
        HttpResponse response = httpRequestHandler.handle(httpRequest);

        assertThat(response).isInstanceOf(RedirectHttpResponse.class);
    }
}
