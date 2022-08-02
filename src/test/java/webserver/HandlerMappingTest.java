package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.CreateMemberHandler;
import webserver.handler.LoginMemberHandler;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.RequestLine;

import java.util.List;
import java.util.Map;

class HandlerMappingTest {

    @DisplayName("요청 매핑에 따른 핸들러를 리턴할 수 있다.")
    @Test
    void getHandlerTest() {
        // given
        HandlerMapping handlerMapping = new HandlerMapping(Map.of(
                new RequestMappingInfo("/user/create", HttpMethod.POST), new CreateMemberHandler(),
                new RequestMappingInfo("/user/login", HttpMethod.POST), new LoginMemberHandler())
        );
        RequestLine requestLine = RequestLine.parseOf("POST /user/create HTTP/1.1");
        HttpRequest httpRequest = new HttpRequest(requestLine);

        Handler handler = handlerMapping.getHandler(httpRequest);

        Assertions.assertThat(handler).isInstanceOf(CreateMemberHandler.class);
    }

    @DisplayName("같은 요청 매핑에 다른 핸들러를 매핑할 수 없다.")
    @Test
    void duplicateHandlerTest() {
        Assertions.assertThatThrownBy(() -> new HandlerMapping(List.of(
                        new RequestMappingRegistration("/user/create", HttpMethod.POST, new CreateMemberHandler()),
                        new RequestMappingRegistration("/user/create", HttpMethod.POST, new LoginMemberHandler())
                )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 등록된 매핑정보 입니다.");
    }


}
