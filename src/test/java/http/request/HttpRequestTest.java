package http.request;

import http.common.Cookies;
import http.response.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.session.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpRequest = new HttpRequest(null, null, null, null, null, null);
        httpResponse = new HttpResponse();
        httpRequest.setHttpResponse(httpResponse);
    }

    @Test
    @DisplayName("세션이 존재하지 않는 httpReqeust에서 true 옵션으로 세션을 가져올 경우 새로운 세션을 생셩하여 반환한다")
    void notExistSessionWithTrue() {
        final Optional<HttpSession> result = httpRequest.getSession(true);

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("세션이 존재하지 않는 httpReqeust에서 false 옵션으로 세션을 가져올 경우 새로운 세션을 생성하지 않는다")
    void notExistSessionWithFalse() {
        final Optional<HttpSession> result = httpRequest.getSession(false);

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("httpReqeust에서 세션을 가져올 때 세션이 존재할 경우 옵션에 제약 없이 기존의 세션을 반환한다")
    void existSession(boolean create) {
        httpRequest.getSession(true);

        final Optional<HttpSession> result = httpRequest.getSession(create);

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("httpReqeust에서 세션을 가져올 때 세션이 존재하지 않을 경우 새로운 세션을 만들고 sessionId는 HttpResponse의 쿠키에 JSESSIONID로 저장된다")
    void jsessionId() {
        httpRequest.getSession();
        Cookies cookies = httpResponse.getCookie();

        String result = cookies.getValue("JSESSIONID");

        assertThat(result).isNotBlank();
    }


}