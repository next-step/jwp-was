package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.session.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    @DisplayName("세션이 존재하지 않는 httpReqeust에서 true 옵션으로 세션을 가져올 경우 새로운 세션을 생셩하여 반환한다")
    void notExistSessionWithTrue() {
        final HttpRequest httpRequest = new HttpRequest(null, null, null, null, null, null);

        final HttpSession session = httpRequest.getSession(true);

        assertThat(session).isNotNull();
    }

    @Test
    @DisplayName("세션이 존재하지 않는 httpReqeust에서 false 옵션으로 세션을 가져올 경우 null을 반환한다")
    void notExistSessionWithFalse() {
        final HttpRequest httpRequest = new HttpRequest(null, null, null,null, null, null);

        final HttpSession session = httpRequest.getSession(false);

        assertThat(session).isNull();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("세션이 존재하는 httpReqeust에서 세션을 가져올 경우 옵션에 제약 없이 기존의 세션을 반환한다")
    void existSession(boolean create) {
        final HttpRequest httpRequest = new HttpRequest(null, null, null,null, null, null);
        httpRequest.getSession(true);

        final HttpSession session = httpRequest.getSession(create);

        assertThat(session).isNotNull();
    }

}