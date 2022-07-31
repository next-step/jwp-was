package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.CreateMemberHandler;
import webserver.handler.LoginMemberHandler;
import webserver.http.HttpMethod;
import webserver.http.Request;
import webserver.http.RequestLine;

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
        Request request = new Request(requestLine);

        Handler handler = handlerMapping.getHandler(request);

        Assertions.assertThat(handler).isInstanceOf(CreateMemberHandler.class);
    }

}