package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class CreateMemberHandlerTest {

    private CreateMemberHandler createMemberHandler;

    @BeforeEach
    void setup() {
        createMemberHandler = new CreateMemberHandler();
    }


    @DisplayName("request path 가 /user/create 이고 request method 가 post 이면 해당 Handler 를 사용할 수 있다.")
    @Test
    void supportTest() {
        // given
        Request request = new Request(
                RequestLine.parseOf("POST /user/create HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                "");

        // when
        boolean support = createMemberHandler.isSupport(request);

        // then
        assertThat(support).isTrue();
    }

    @DisplayName("회원가입 후 index.html 로 리다이렉트 될 수 있도록 응답해야 한다.")
    @Test
    void handleTest() {
        // given
        Request request = new Request(
                RequestLine.parseOf("POST /user/create HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                "userId=javajigi&password=password&name=재성&email=javajigi@slipp.net");

        // when
        Response response = createMemberHandler.handle(request);

        // then
        assertThat(response.getStatusLine()).isEqualTo(new StatusLine(ProtocolVersion.HTTP11, Status.FOUND));
        assertThat(response.getHeaders()).isEqualTo(Headers.of(Map.of("Location", "/index.html")));
    }

}