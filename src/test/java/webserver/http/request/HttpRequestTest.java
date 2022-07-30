package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;
import webserver.http.Session;
import webserver.http.SessionConfig;
import webserver.http.SessionStorage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("http 요청")
class HttpRequestTest {

    private static final SessionStorage SESSION_STORAGE = SessionStorage.empty();

    @Test
    @DisplayName("문자열 리스트로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() -> HttpRequest.of(
                SESSION_STORAGE,
                Arrays.asList(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive"
                )
        ));
    }

    @Test
    @DisplayName("리스트와 세션 저장소는 필수")
    void instance_empty_thrownIllegalArgumentException() {
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() -> HttpRequest.of(SESSION_STORAGE, Collections.emptyList())),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> HttpRequest.of(null, Arrays.asList(
                        "GET /index.html HTTP/1.1",
                        "Host: localhost:8080",
                        "Connection: keep-alive"
                )))
        );
    }

    @Test
    @DisplayName("바디 추가")
    void withBody() {
        //given
        HttpRequest request = HttpRequest.of(SESSION_STORAGE, List.of("GET /index.html HTTP/1.1"));
        RequestBody body = RequestBody.empty();
        //when, then
        assertThat(request.withBody(body)).isEqualTo(HttpRequest.of(request, body));
    }


    @Test
    @DisplayName("요청 세션 조회")
    void session() {
        //given
        Session emptySession = HttpSession.empty();
        HttpRequest request = HttpRequest.of(SessionStorage.from(List.of(emptySession)),
                List.of(
                        "GET /index.html HTTP/1.1",
                        String.format("Cookie: %s=%s", SessionConfig.sessionCookieName(), emptySession.getId())
                )
        );
        //when, then
        assertThat(request.session()).isEqualTo(Optional.of(emptySession));
    }
}
