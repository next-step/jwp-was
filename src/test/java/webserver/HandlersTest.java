package webserver;

import http.request.Request;
import http.request.body.Body;
import http.request.headers.Headers;
import http.request.headers.Headers2;
import http.request.requestline.requestLine2.RequestLine2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.customhandler.TemplateHandler;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlersTest {
    @DisplayName("요청의 url이 .css로 끝나면, TemplateHandler 맵핑")
    @Test
    void findHandlerWhenTemplate() {
        //given
        RequestLine2 requestLine = new RequestLine2("GET /style.css HTTP/1.1");
        Body body = new Body("body");
        Headers2 headers = new Headers2(new HashMap<>());
        Request request = new Request(requestLine, headers, body);

        //when
        Handler handler = Handlers.findHandler(request);

        //then
        assertThat(handler).isInstanceOf(TemplateHandler.class);
    }
}
