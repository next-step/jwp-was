package controller;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ResourceController 테스트")
class TemplateControllerTest {

    private static TemplateController templateController;
    private static HttpRequest request;

    @BeforeAll
    static void setUp() {
        templateController = new TemplateController();
        request = HttpRequest.of(
                RequestLine.of(HttpMethod.GET, Path.of("/index.html"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(Collections.emptyList()),
                HttpRequestBody.empty()
        );
    }

    @DisplayName("method,path 가 일치하면 true 리턴한다.")
    @Test
    void matchTrue() {
        boolean result = templateController.match(request);
        assertThat(result).isTrue();
    }

    @Test
    void response() throws IOException, URISyntaxException {
        HttpResponse response = templateController.execute(request);
        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                () -> assertThat(response.getBody()).isNotNull()
        );
    }

}
