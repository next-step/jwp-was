package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.IllegalRequestPathException;

class RequestPathTest {

    @DisplayName("클라이언트로부터 받은 요청 경로를 파싱한다.")
    @Test
    void parse() {
        RequestPath requestPath = RequestPath.from("/users?userId=javajigi&password=password&name=JaeSung");
        assertAll(() -> {
            assertThat(requestPath.getPath()).isEqualTo("/users");
            assertThat(requestPath.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
            assertThat(requestPath.getParameter("userId")).isEqualTo("javajigi");
            assertThat(requestPath.getParameter("password")).isEqualTo("password");
            assertThat(requestPath.getParameter("name")).isEqualTo("JaeSung");
        });
    }

    @DisplayName("HTTP 요청 path가 '/'로 시작하지 않으면 IllegalRequestPathException 예외가 발생한다.")
    @Test
    void invalid() {
        assertThatThrownBy(() -> RequestPath.from("users?userId=javajigi&password=1234&name=pjs"))
            .isInstanceOf(IllegalRequestPathException.class);
    }
}
