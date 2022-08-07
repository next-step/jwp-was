package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import constant.HttpCookie;
import constant.HttpMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import session.HttpSession;
import session.SessionManager;

public class HttpRequestTest {

    private static final String testDirectory = "./src/test/resources/http/";

    @Test
    @DisplayName("GET 피일을 읽어 값을 확인합니다.")
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequest.parse(in);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    @DisplayName("POST 파일을 읽어 값을 확인합니다.")
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.parse(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        HttpRequest request = HttpRequest.parse(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    @DisplayName("파라미터 값을 반환합니다.")
    void getParamterTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");

        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getParameter("name")).isEqualTo("1234");
    }

    @Test
    @DisplayName("header의 값을 반환합니다.")
    void getHeaderTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");

        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Cookie")).isEqualTo("logined=true");
    }

    @Test
    @DisplayName("파라미터 값을 반환합니다.")
    void getParameterTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getParameter("name")).isEqualTo("1234");
    }

    @Test
    @DisplayName("path 값을 반환합니다.")
    void getPath() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("Cookie 값만 반환합니다.")
    void getCookieTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: logined=true");
        HttpRequest httpRequest = HttpRequest.from(lines);

        RequestCookie cookie = httpRequest.getCoookie();

        assertThat(cookie.getParameter(HttpCookie.LOGIN.getValue())).isEqualTo("true");
    }

    @Test
    @DisplayName("로그인된 JSESSIONID이 있을 떄 true로 반환하기")
    void isLoginedTest() {
        HttpSession session = SessionManager.createSession();
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: JSESSIONID="+session.getId());
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.isLogined()).isTrue();
    }

    @Test
    @DisplayName("SessionManager에 없는 SessionID가 들어올 경우 false반환합니다.")
    void isLoginedFalseTest() {
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: JSESSIONID=JSESSIONID");
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.isLogined()).isFalse();
    }

    @Test
    @DisplayName("요청받은 SessionId로 알맞은 Session을 받는지 확인합니다.")
    void getHttpSesionTest() {
        HttpSession session = SessionManager.createSession();
        List<String> lines = List.of("GET /index.html?name=1234 HTTP/1.1", "Host: localhost:8080", "Cookie: JSESSIONID="+session.getId());
        HttpRequest httpRequest = HttpRequest.from(lines);

        assertThat(httpRequest.getHttpSesion()).isEqualTo(session);
    }

}
