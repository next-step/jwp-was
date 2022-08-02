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

@DisplayName("StaticController 테스트")
class StaticControllerTest {

    private static StaticController staticController;
    private static HttpRequest request;

    @BeforeAll
    static void setUp() {
        staticController = new StaticController();
        request = HttpRequest.of(
                RequestLine.of(HttpMethod.GET, Path.of("/css/styles.css"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(Collections.emptyList()),
                HttpRequestBody.empty()
        );
    }

    @DisplayName("method,path 가 일치하면 true 리턴한다.")
    @Test
    void matchTrue() {
        boolean result = staticController.matchHttpMethodAndPath(request);
        assertThat(result).isTrue();
    }

    @Test
    void response() throws IOException, URISyntaxException {
        HttpResponse response = staticController.execute(request);
        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.CONTENT_TYPE, "text/css;charset=utf-8")),
                () -> assertThat(response.getBody()).isNotNull()
        );
    }

}
