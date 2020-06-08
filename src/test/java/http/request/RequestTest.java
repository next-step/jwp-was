package http.request;

import http.response.ContentType;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.session.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {
    @DisplayName("Request에서 Session 추출 - JSESSIONID가 있을 때")
    @Test
    void getSession() {
        //given
        RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.1");
        Headers headers = new Headers(createHeaders());
        RequestBody body = new RequestBody(Strings.EMPTY);
        Request request = new Request(requestLine, headers, body);

        //when
        HttpSession session = request.getSession();

        //then
        assertThat(session.getId())
                .isEqualTo(UUID.fromString(headers.getHeader("JSESSION")));
    }

    private Map<String, String> createHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html");
        headers.put("JSESSIONID", UUID.randomUUID().toString());

        return headers;
    }
}
