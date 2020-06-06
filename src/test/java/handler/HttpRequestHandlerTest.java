package handler;


import static org.assertj.core.api.Assertions.assertThat;

import handler.HttpRequestHandler;
import http.HttpResponse;
import http.request.HttpRequest;
import http.response.EmptyHttpResponse;
import http.response.StaticResourceHttpResponse;
import java.io.File;
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
        HttpRequest httpRequest = HttpRequest.of(line);
        HttpResponse response = httpRequestHandler.handle(httpRequest);

        File file = new File(path);
        assertThat(response).isEqualTo(new StaticResourceHttpResponse(file));
    }

    @Test
    void handleUserCreate(){
        HttpRequest httpRequest = HttpRequest.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        HttpResponse response = httpRequestHandler.handle(httpRequest);

        assertThat(response).isInstanceOf(EmptyHttpResponse.class);
    }
}
