package webserver.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import static org.junit.jupiter.api.Assertions.*;

class ListMemberHandlerTest {

    private ListMemberHandler listMemberHandler;

    @BeforeEach
    void setup() {
        listMemberHandler = new ListMemberHandler();
    }

    @DisplayName("[GET] /user/list 요청을 처리할 수 있다.")
    @Test
    void supportTest() {
        // given
        Request request = new Request(RequestLine.parseOf("GET /user/list HTTP/1.1"));

        // when
        boolean support = listMemberHandler.isSupport(request);

        // then
        Assertions.assertThat(support).isTrue();
    }

    @DisplayName("로그인 하지 않은 사용자가 접근하면 login.html 로 리다이렉트 시켜야 한다.")
    @Test
    void redirectLoginPageTest() {
        // given
        Request request = new Request(RequestLine.parseOf("GET /user/list HTTP/1.1"));
        Response response = new Response();

        // when
        listMemberHandler.handle(request, response);

        // then
        Assertions.assertThat(response.getHeaders().getValue("Location")).isEqualTo("/user/login.html");
        Assertions.assertThat(response.getStatusLine().getStatus()).isEqualTo(Status.FOUND);
    }

    @DisplayName("로그인 사용자가 접근하면 사용자 리스트 화면이 보여야 한다.")
    @Test
    void loginListPageTest() {
        // given
        Header cookieHeader = new Header("cookie", "logined=true");
        Request request = new Request(RequestLine.parseOf("GET /user/list HTTP/1.1"), Headers.of(cookieHeader));
        Response response = new Response();

        // when
        listMemberHandler.handle(request, response);

        // then
        Assertions.assertThat(response.hasBody()).isEqualTo(true);
        Assertions.assertThat(response.getStatusLine().getStatus()).isEqualTo(Status.OK);
    }


}