package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionManagerTest {

    private static final String HTTP_PLAIN_GET = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Cookie: test=testValue\n" +
            "Accept: */*\n";

    @DisplayName("HttpSessionManager 테스트 : session create")
    @Test
    public void httpSessionCreate() {
        try {
            HttpRequest request = HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_PLAIN_GET.getBytes()));
            HttpSession httpSession = request.getSession(true);
            assertThat(HttpSessionManager.getInstance().getHttpSession(httpSession.getId())).isNotNull();
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
        }
    }

    @DisplayName("HttpSessionManager 테스트 : session create no flag")
    @Test
    public void httpSessionCreateNoFlag() {
        try {
            HttpRequest request = HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_PLAIN_GET.getBytes()));
            HttpSession httpSession = request.getSession();
            assertThat(HttpSessionManager.getInstance().getHttpSession(httpSession.getId())).isNotNull();
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
        }
    }

    @DisplayName("HttpSessionManager 테스트 : session no")
    @Test
    public void httpSessionNoCreate() {
        try {
            HttpRequest request = HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_PLAIN_GET.getBytes()));
            HttpSession httpSession = request.getSession(false);
            assertThat(httpSession).isNull();
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
        }
    }
}
