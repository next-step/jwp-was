package http.request;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.session.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {
    @DisplayName("Request에서 Session 추출 - JSESSIONID가 있을 때")
    @Test
    void getSessionWhenSessionExist() {
        //given
        RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.1");
        Headers headers = new Headers(createHeadersWithSession(new HttpSession()));
        RequestBody body = new RequestBody(Strings.EMPTY);
        Request request = new Request(requestLine, headers, body);

        //when
        HttpSession session = request.getSession();

        //then
        assertThat(session.getId())
                .isEqualTo(headers.getHeader("JSESSIONID"));
    }

    @DisplayName("Request에서 Session 추출 - JSESSIONID가 없을 때")
    @Test
    void getSessionWhenSessionNotExist() {
        //given
        RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.1");
        Headers headers = new Headers(new HashMap<>());
        RequestBody body = new RequestBody(Strings.EMPTY);
        Request request = new Request(requestLine, headers, body);
        assertThat(headers.getHeader("JSESSIONID")).isNull();

        //when
        HttpSession session = request.getSession();

        //then
        assertThat(session.getId()).isNotNull();
    }

    private Map<String, String> createHeadersWithSession(HttpSession session) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html");
        headers.put("JSESSIONID", session.getId());

        return headers;
    }
}
