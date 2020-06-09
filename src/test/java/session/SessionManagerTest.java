package session;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.FileLoader;
import testutils.HttpRequestGenerator;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("모든 request 에 대해서 세션을 셍상히고 로드하는 작업을 함")
class SessionManagerTest {
    private SessionManager sessionManager;
    private HttpResponse httpResponse;
    private final String sessionId = "default";

    @BeforeEach
    void setEnv() {
        InMemorySessionHolder inMemorySessionHolder = new InMemorySessionHolder();
        sessionManager = new SessionManager(inMemorySessionHolder);

        HttpSession httpSession = DefaultHttpSession.of(sessionId);
        httpSession.setAttribute("key", "value");

        httpResponse = HttpResponse.init();
        inMemorySessionHolder.save(httpSession);
    }

    @Test
    @DisplayName("세션 메니저는 http request 와 http response 를 파라미터로 받아 세션 키를 생성 및 응답값에 저장한다.")
    void loadSession() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /test HTTP/1.1");

        Map<String, String> cookies = httpResponse.getCookies();

        assertThat(cookies).hasSize(0);
        sessionManager.loadSession(httpRequest, httpResponse);
        assertThat(cookies).hasSize(1);
        assertThat(cookies.get("suid")).isNotNull();
    }

    @Test
    @DisplayName("키값을 가지고 있는 요청은 키에 해당하는 세션을 session holder 로 부터 가져와 http request 객체에 저장한다.")
    void loadSessionWithCookieExistRequest() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("suid-contained-request.txt"));
        String sessionId = httpRequest.getCookie("suid");

        assertThat(sessionId).isNotNull();
        assertThat(sessionId).isEqualTo(this.sessionId);
        sessionManager.loadSession(httpRequest, httpResponse);
        assertThat(httpRequest.getSession()).isNotNull();
    }

    @Test
    @DisplayName("서로 다른 요청에 대해서도 세션으로 부터 같은 값을 받아 올 수 있다.")
    void loadSameAttribute() throws IOException {
        HttpRequest httpRequest = HttpRequest.readRawRequest(FileLoader.load("suid-contained-request.txt"));
        sessionManager.loadSession(httpRequest, httpResponse);

        HttpSession session = httpRequest.getSession();

        assertThat(session.getAttribute("key")).isEqualTo("value");
    }
}
