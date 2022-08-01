package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResourceController 테스트")
class ResourceControllerTest {

    private ResourceController resourceController;
    private HttpRequest request;

    @BeforeEach
    void setUp() {
        resourceController = new ResourceController();
        request = HttpRequest.of(
                RequestLine.of(HttpMethod.GET, Path.of("/index.html"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(Collections.emptyList()),
                HttpRequestBody.empty()
        );
    }

    @DisplayName("method,path 가 일치하면 true 리턴한다.")
    @Test
    void matchTrue() {
        boolean result = resourceController.matchHttpMethodAndPath(request);
        assertThat(result).isTrue();
    }

    @Test
    void response() throws IOException, URISyntaxException {
        HttpResponse response = resourceController.execute(request);
        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                () -> assertThat(response.getBody()).isNotNull()
        );
    }

}
