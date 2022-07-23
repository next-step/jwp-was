package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Request;

import java.util.List;


class CreateMemberHandlerTest {

    @DisplayName("request path 가 /user/create 이고 request method 가 post 이면 해당 Handler 를 사용할 수 있다.")
    @Test
    void supportTest() {
        // given
        CreateMemberHandler createMemberHandler = new CreateMemberHandler();
        Request request = Request.parseOf(List.of("POST /user/create HTTP/1.1", "Host: localhost:8080"));

        // when
        boolean support = createMemberHandler.isSupport(request);

        // then
        Assertions.assertThat(support).isTrue();
    }

}