package webserver;

import http.request.Request;
import http.request.body.Body;
import http.request.headers.Headers2;
import http.request.requestline.requestLine2.RequestLine2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.customhandler.StaticResourceHandler;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlersTest {
    @DisplayName("요청의 url이 static 자원을 가리키면, StaticHandler 맵핑")
    @ParameterizedTest
    @ValueSource(strings = {".css", ".js", ".ttf", ".woff"})
    void findHandlerWhenTemplate(String suffix) {
        //given
        RequestLine2 requestLine = new RequestLine2("GET /style" + suffix + " HTTP/1.1");
        Body body = new Body("body");
        Headers2 headers = new Headers2(new HashMap<>());
        Request request = new Request(requestLine, headers, body);

        //when
        Handler handler = Handlers.findHandler(request);

        //then
        assertThat(handler).isInstanceOf(StaticResourceHandler.class);
    }
}
