package session;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.HttpRequestGenerator;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("모든 request 에 대해서 세션을 셍상히고 로드하는 작업을 함")
class SessionManagerTest {
    private SessionManager sessionManager;
    private final String sessionId = "default";

    @BeforeEach
    void setEnv() {
        InMemorySessionHolder inMemorySessionHolder = new InMemorySessionHolder();
        sessionManager = new SessionManager(inMemorySessionHolder);
        inMemorySessionHolder.save(DefaultHttpSession.of(sessionId));
    }

    @Test
    @DisplayName("세션 메니저는 http request 와 http response 를 파라미터로 받아 세션 키를 생성 및 응답값에 저장한다.")
    void loadSession() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /test HTTP/1.1");
        HttpResponse httpResponse = HttpResponse.init();

        Map<String, String> cookies = httpResponse.getCookies();

        assertThat(cookies).hasSize(0);
        sessionManager.loadSession(httpRequest, httpResponse);
        assertThat(cookies).hasSize(1);
        assertThat(cookies.get("suid")).isNotNull();
    }
}