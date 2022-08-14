package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.CreateMemberHandler;
import webserver.handler.LoginMemberHandler;
import webserver.handler.StaticFileHandler;
import webserver.http.HttpRequest;
import webserver.http.RequestLine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static webserver.http.HttpMethod.POST;

class HandlerMappingTest {

    @DisplayName("요청 매핑에 따른 핸들러를 리턴할 수 있다.")
    @Test
    void getHandlerTest() {
        // given
        HandlerMapping handlerMapping = new HandlerMapping(
                new RequestMappingRegistration("/user/create", POST, new CreateMemberHandler()),
                new RequestMappingRegistration("/user/login", POST, new LoginMemberHandler(new JwpSessionHandler()))
        );
        RequestLine requestLine = RequestLine.parseOf("POST /user/create HTTP/1.1");
        HttpRequest httpRequest = new HttpRequest(requestLine);

        Handler handler = handlerMapping.getHandler(httpRequest);

        assertThat(handler).isInstanceOf(CreateMemberHandler.class);
    }

    @DisplayName("같은 요청 매핑에 다른 핸들러를 매핑할 수 없다.")
    @Test
    void duplicateHandlerTest() {
        assertThatThrownBy(() -> new HandlerMapping(
                        new RequestMappingRegistration("/user/create", POST, new CreateMemberHandler()),
                        new RequestMappingRegistration("/user/create", POST, new LoginMemberHandler(new JwpSessionHandler()))
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 등록된 매핑정보 입니다.");
    }

    @DisplayName("요청에 매핑된 핸들러가 없다면, 설정한 폴백 핸들러가 리턴되어야 한다.")
    @Test
    void fallbackHandlerTest() {
        // given
        Handler fallbackHandler = new StaticFileHandler();
        HandlerMapping handlerMapping = new HandlerMapping(
                List.of(new RequestMappingRegistration("/user/create", POST, new CreateMemberHandler())),
                fallbackHandler);

        // when
        Handler handler = handlerMapping.getHandler(new HttpRequest("POST /user/login HTTP/1.1"));

        // then
        assertThat(handler).isEqualTo(fallbackHandler);
    }

}
