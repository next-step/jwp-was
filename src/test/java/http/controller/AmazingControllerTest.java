package http.controller;

import http.requests.RequestContext;
import http.responses.ResponseContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("으메이징 컨트롤러를 테스트한다.")
class AmazingControllerTest {

    @DisplayName("컨트롤러를 테스트한다.")
    @Test
    void test_controller() {
        final String rawRequestLine = "GET /index.html?amu=mal&dae=janchi HTTP/1.1";
        final List<String> rawRequestHeaders = new ArrayList<>();
        rawRequestHeaders.add("Host: localhost:8080");
        rawRequestHeaders.add("Connection: keep-alive");
        rawRequestHeaders.add("Cache-Control: no-cache");

        final RequestContext requestContext = new RequestContext(rawRequestLine, rawRequestHeaders, "");

        final ResponseContext responseContext = AmazingController.dispatch(requestContext);
        assertThat(responseContext).isNotNull();
    }
}