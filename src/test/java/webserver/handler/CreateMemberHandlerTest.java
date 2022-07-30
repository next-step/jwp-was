package webserver.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestMappingInfo;
import webserver.http.*;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


class CreateMemberHandlerTest {

    private CreateMemberHandler createMemberHandler;

    @BeforeEach
    void setup() {
        createMemberHandler = new CreateMemberHandler();
    }


    @DisplayName("request path 가 /user/create 이고 request method 가 post 인 요청과 매핑된다.")
    @Test
    void supportTest() {
        // given
        RequestMappingInfo expected = new RequestMappingInfo("/user/create", HttpMethod.POST);

        // when
        RequestMappingInfo actual = createMemberHandler.getMappingInfo();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("회원가입 후 index.html 로 리다이렉트 될 수 있도록 응답해야 한다.")
    @Test
    void handleTest() {
        // given
        Request request = new Request(
                RequestLine.parseOf("POST /user/create HTTP/1.1"),
                Headers.parseOf(new ArrayList<>()),
                "userId=javajigi&password=password&name=재성&email=javajigi@slipp.net");

        Response response = new Response();

        // when
        createMemberHandler.handle(request, response);

        // then
        assertThat(response.getStatusLine()).isEqualTo(new StatusLine(ProtocolVersion.HTTP11, Status.FOUND));
        assertThat(response.getHeaders().getValue("Location")).isEqualTo( "/index.html");
    }

}