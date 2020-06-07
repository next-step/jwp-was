package handler;


import static org.assertj.core.api.Assertions.assertThat;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.RedirectView;
import http.view.StaticResourceView;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.RequestHandlerMapping;

public class RequestHandlerMappingTest {

    private RequestHandlerMapping requestHandlerMapping;

    @BeforeEach
    void init(){
        this.requestHandlerMapping = new RequestHandlerMapping();
    }

    @DisplayName("static resource 요청 처리")
    @ParameterizedTest
    @CsvSource({
        "GET /index.html HTTP/1.1, /index.html",
        "GET /index2.html HTTP/1.1, /index2.html"
    })
    void handleStaticResourceRequest(String line, String path) {
        HttpRequest httpRequest = HttpRequest.of(
            line,
            Arrays.asList(),
            null
        );
        HttpResponse response = requestHandlerMapping.handle(httpRequest);

        assertThat(response).isEqualTo(new HttpResponse(new StaticResourceView(path)));
    }

    @Test
    void handleUserCreate(){
        HttpRequest httpRequest = HttpRequest.of(
            "POST /user/create HTTP/1.1",
            Arrays.asList(),
            null
        );
        HttpResponse response = requestHandlerMapping.handle(httpRequest);

        assertThat(response).isEqualTo(new HttpResponse(new RedirectView("/index.html")));
    }
}
